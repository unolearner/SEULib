package com.example.seulibapp.config;
import com.example.seulibapp.dao.BookDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
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
        Iterable<Book> allBooks = bookRepository.findAll(); // 获取所有ES中的数据

        bookDao.saveAll(StreamSupport.stream(allBooks.spliterator(), false)
                .collect(Collectors.toList())); // 保存到数据库
        System.out.println("同步完成：所有数据已从ES同步到数据库");
    }
}

