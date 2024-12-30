package com.example.seulibapp.config;
import com.example.seulibapp.dao.BookDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
@EnableScheduling
public class ElasticsearchShutdownConfig {

    @Autowired
    @Qualifier("bookRepository")
    private BookRepository bookRepository; // Elasticsearch Repository

    @Autowired
    @Qualifier("bookDao")
    private BookDao bookDao; // Database DAO

    /**
     * 服务退出时同步数据到数据库
     */
    @EventListener(ContextClosedEvent.class)
    public void syncDataToDatabaseOnShutdown() {
        // 清空数据库中的所有数据
        bookDao.deleteAll();

        // 分页查询 ES 中的数据
        int pageSize = 1000;
        int page = 0;
        boolean hasNext = true;

        while (hasNext) {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Book> booksPage = bookRepository.findAll(pageable);

            List<Book> books = booksPage.getContent();
            if (!books.isEmpty()) {
                // 遍历每本书，检查数据库中是否存在相应的bid
                for (Book book : books) {
                    // 如果数据库中已经有相应的bid，保留它；如果没有，将bid设为null
                    if (!bookDao.existsById(book.getBid())) {
                        // 说明数据库中不存在该bid或是新书
                        book.setBid(null);  // 让数据库自动生成
                    }
                }
                // 保存到数据库
                bookDao.saveAll(books);
                page++;
            } else {
                hasNext = false;
            }
        }

        System.out.println("同步完成：所有数据已从ES同步到数据库");
    }
}

