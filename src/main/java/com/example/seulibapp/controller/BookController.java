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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public void addBook(@RequestBody Book book) {
        elasticsearchService.saveBook(book);  // 保存书籍
    }

    // 搜索书籍
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return elasticsearchService.searchBooksByName(keyword);  // Elasticsearch 搜索
    }

    // 获取所有书籍
    @GetMapping("/all")
    public Iterable<Book> getAllBooks() {
        return elasticsearchService.getAllBooks();  // 获取所有书籍
    }



    // 删除书籍
    @DeleteMapping("/{bid}")
    public void deleteBook(@PathVariable Long bid) {
        elasticsearchService.deleteBook(bid);  // 删除书籍
    }

    /**
     * 查询销量前十的书籍
     * @return 前十书籍的列表
     */
    @GetMapping("/top-books")
    public List<Book> getTop10Books() {
        return elasticsearchService.getTop10BooksBySales();
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
        if(book.getStore()==0){//库存不足，进行预约，返回信息
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
    public String importBooks(@RequestParam("file") MultipartFile file) {
        try {
            // 解析 Excel 文件
            List<Book> books = excelService.importBooksFromExcel(file);
            System.out.println(books);

            // 将解析的书籍传递给 ElasticsearchService
            elasticsearchService.processImportedBooks(books);

            System.out.println(books);
            return "书籍导入成功，数量：" + books.size();
        } catch (IOException e) {
            return "文件解析失败：" + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "文件类型错误：" + e.getMessage();
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
        return ResponseEntity.ok("还书成功，书籍"+book.getBname()+"现存量为"+book.getStore());
    }
}
