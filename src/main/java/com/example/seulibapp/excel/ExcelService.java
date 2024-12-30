package com.example.seulibapp.excel;

import com.example.seulibapp.entity.Book;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {

    public List<Book> importBooksFromExcel(MultipartFile file) throws IOException {

        DataFormatter dataFormatter = new DataFormatter();


        // 创建一个 Workbook 对象，读取 Excel 文件
        Workbook workbook =new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
        List<Book> books = new ArrayList<>();

        // 遍历所有的行，跳过标题行（第一行），从第二行开始
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) continue;  // 如果行为空，跳过

            // 获取列数据并创建 Book 对象
            Book book = new Book();
            book.setBname(dataFormatter.formatCellValue(row.getCell(0)));  // 获取书名
            book.setDetail(dataFormatter.formatCellValue(row.getCell(1)));  // 获取详细信息
            book.setPrice(dataFormatter.formatCellValue(row.getCell(2)));   // 获取价格
            book.setAuthor(dataFormatter.formatCellValue(row.getCell(3)));  // 获取作者
            book.setPrinter(dataFormatter.formatCellValue(row.getCell(4))); // 获取出版社
            book.setDate(dataFormatter.formatCellValue(row.getCell(5)));    //获取日期
            book.setType(dataFormatter.formatCellValue(row.getCell(6)));    // 获取书籍类型
            book.setImage(dataFormatter.formatCellValue(row.getCell(7)));   // 获取封面路径
            book.setStore((int)row.getCell(8).getNumericCellValue()); // 获取入库数量
            // 将其加入到列表中
            books.add(book);
        }

        // 关闭 Workbook，释放资源
        workbook.close();

        return books;
    }
}