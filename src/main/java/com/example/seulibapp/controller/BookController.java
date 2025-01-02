package com.example.seulibapp.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.example.seulibapp.dao.RecordDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.entity.BookRecord;
import com.example.seulibapp.entity.Reservation;
import com.example.seulibapp.entity.User;
import com.example.seulibapp.dao.ReservationDao;
import com.example.seulibapp.excel.ExcelService;
import com.example.seulibapp.myEnum.ActionType;
import com.example.seulibapp.request.CommonRequest;
import com.example.seulibapp.response.MyErrorResponse;
import com.example.seulibapp.service.ElasticsearchService;
import com.example.seulibapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "*")
@RestController
@Scope("prototype")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private ElasticsearchService elasticsearchService;  // 注入接口
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("reservationRepository")
    private ReservationDao reservationDao;
    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

    @Autowired
    private ExcelService excelService;

    // 添加书籍
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = elasticsearchService.saveBook(book);  // 保存书籍‘
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{bid}")  // 使用 bid 作为资源的标识符
                .buildAndExpand(savedBook.getBid())  // 假设 book 对象中有 bid
                .toUri();

        return ResponseEntity.created(location)  // 返回 201 Created 状态码
                .body(savedBook);  // 返回保存后的书籍对象
    }

    // 搜索书籍
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        List<Book> books = elasticsearchService.searchBooksByName(keyword);  // 使用 Elasticsearch 进行搜索

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();  // 如果没有找到书籍，返回 204 No Content
        }

        return ResponseEntity.ok(books);  // 返回 200 OK 和搜索结果
    }

    // 获取所有书籍
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        Iterable<Book> booksIterable = elasticsearchService.getAllBooks();  // 获取所有书籍

        List<Book> books = StreamSupport.stream(booksIterable.spliterator(), false)
                .collect(Collectors.toList());  // 将 Iterable 转换为 List


        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();  // 如果没有书籍，返回 204 No Content
        }

        return ResponseEntity.ok(books);  // 返回 200 OK 和书籍列表
    }



    // 删除书籍
    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bid) {
        boolean isDeleted = elasticsearchService.deleteBook(bid);  // 删除书籍
        if (isDeleted) {
            return ResponseEntity.noContent().build();  // 删除成功，返回 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 删除失败（书籍不存在），返回 404 Not Found
        }
    }

    /**
     * 查询销量前十的书籍
     * @return 前十书籍的列表
     */
    @GetMapping("/top-books")
    public ResponseEntity<List<Book>> getTop10Books() {
        List<Book> topBooks = elasticsearchService.getTop10BooksBySales();  // 查询前十书籍

        if (topBooks.isEmpty()) {
            return ResponseEntity.noContent().build();  // 如果没有书籍，返回 204 No Content
        }

        return ResponseEntity.ok(topBooks);  // 返回 200 OK 和书籍列表
    }

    /**
     * 借阅书籍：sales++，store--
     * @return null
     */
    @PostMapping("/borrow")
    ResponseEntity<?>borrowBook(@RequestBody CommonRequest request) {
        Book book=elasticsearchService.searchBookById(request.getBookId());
        User user=userService.getUserById(request.getUserId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //检查User信誉，信誉低于30不允许借
        if(user.getCredit()<24){
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(new MyErrorResponse("Out of credit","您的信誉不足，无法借阅"));
        }

        //库存不足，进行预约，返回信息
        if(book.getStore()==0){
            Reservation reservation=new Reservation();
            reservation.setBid(request.getBookId());
            reservation.setReservationDate(LocalDateTime.now().format(formatter));
            reservation.setUserId(request.getUserId());
            reservationDao.save(reservation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MyErrorResponse("Out of stock","库存不足，无法借阅，已为您预约"));
        }
        //库存足够，正常借阅，书籍库存减一，销量加一
        book.setStore(book.getStore()-1);
        book.setSales(book.getSales()+1);
        elasticsearchService.saveBook(book);
        //留下借阅记录
        BookRecord record=new BookRecord();
        record.setUserId(request.getUserId());
        record.setBid(request.getBookId());
        record.setActionType(ActionType.BORROW);
        record.setActionDate(LocalDateTime.now().format(formatter));
        recordDao.save(record);
        return ResponseEntity.ok("借阅成功,借阅人："+user.getUserName());
    }


    /**
     *
     * 从excel表中导入书籍
     * @param file excel 文件
     * @return 相关信息？成功，或者失败
     */
    @PostMapping("/import")
    public ResponseEntity<String> importBooks(@RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("上传的文件为空");
            }

            // 解析 Excel 文件
            List<Book> books = excelService.importBooksFromExcel(file);
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("没有解析到书籍数据");
            }

            // 将解析的书籍传递给 ElasticsearchService
            elasticsearchService.processImportedBooks(books);

            // 返回 200 OK 和成功信息
            return ResponseEntity.ok("书籍导入成功，数量：" + books.size());

        } catch (IOException e) {
            // 返回 500 Internal Server Error 和错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("文件解析失败：" + e.getMessage());
        } catch (IllegalArgumentException e) {
            // 返回 400 Bad Request 和错误信息
            return ResponseEntity.badRequest().body("文件类型错误：" + e.getMessage());
        } catch (Exception e) {
            // 处理其他任何未捕获的异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("系统错误：" + e.getMessage());
        }
    }

    /**
     * 下载导入模板的 方法
     * @return Http响应头，提升下载文件
     * @throws IOException IO异常
     */
    @GetMapping("/download-book-template")
    public ResponseEntity<Resource> downloadBookTemplate() throws IOException {
        // 资源路径是 /Template/book-template.xlsx
        Resource resource = new ClassPathResource("templates/Book_Entry_Template.xlsx");

        // 获取文件名
        String filename = resource.getFilename();

        // 设置 HTTP 响应头
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    /**
     * 更新书籍的方法
     * @param updatedBook 传入的书籍实体
     * @return 返回保存后的书籍实体
     */
    @PutMapping("/update")
    public ResponseEntity<Book> updateBookInventory(@RequestBody Book updatedBook) {
        // 调用 Elasticsearch 服务来保存更新后的书籍
        elasticsearchService.saveBook(updatedBook);

        // 使用保存后的book ID查询最新的实体
        Book savedBook = elasticsearchService.searchBookById(updatedBook.getBid());

        return ResponseEntity.ok(savedBook); // 返回保存后的书籍实体
    }

    /**
     * 还书，store++，留下记录
     * @param request
     * @return
     */
    @PostMapping("/return")
    public ResponseEntity<?>returnBook(@RequestBody CommonRequest request) {
        Book book=elasticsearchService.searchBookById(request.getBookId());
        book.setStore(book.getStore()+1);//还书，库存加一
        elasticsearchService.saveBook(book);
        //留下记录
        BookRecord record=new BookRecord();
        record.setBid(request.getBookId());
        record.setUserId(request.getUserId());
        record.setActionType(ActionType.RETURN);
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        record.setActionDate(LocalDateTime.now().format(formatter));
        recordDao.save(record);
        //删除此前的借和续借
        List<BookRecord>borrowAndReborrows=recordDao.findBeforeBorrowAndReborrow(request.getUserId(),
                request.getBookId());
        recordDao.deleteAll(borrowAndReborrows);
        return ResponseEntity.ok("还书成功，书籍《"+book.getBname()+"》现存量为"+book.getStore());
    }

    /**
     * 续借，允许一个月续借两次，续借前先查record检查有无一个月内两次的情况
     * @return
     */
    @PostMapping("/reborrow")
    public ResponseEntity<?>reBorrowBook(@RequestBody CommonRequest request){
        User user=userService.getUserById(request.getUserId());
        Book book=elasticsearchService.searchBookById(request.getBookId());
        //检查User信誉，信誉低于30不允许续借
        if(user.getCredit()<24){
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(new MyErrorResponse("Out of credit","您的信誉不足，无法续借"));
        }
        //检查一个月内有无续借
        LocalDate oneMonthAgo=LocalDate.now().minusMonths(1);
        List<BookRecord> records=recordDao.findRecentOneMonthReBorrow(request.getUserId(),
                String.valueOf(oneMonthAgo),request.getBookId());
        if(!records.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(new MyErrorResponse("Out of date","您本月已续借过该书，无法继续续借"));
        }
        //一切正常，进行续借，留下记录
        BookRecord record=new BookRecord();
        record.setBid(request.getBookId());
        record.setUserId(request.getUserId());
        record.setActionType(ActionType.REBORROW);
        record.setActionDate(String.valueOf(LocalDate.now()));
        recordDao.save(record);
        return ResponseEntity.ok("续借成功，新的还书截止期限为："+LocalDate.now().plusMonths(1));
    }
}
