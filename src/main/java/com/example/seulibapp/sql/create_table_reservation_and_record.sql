CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bid BIGINT,
    user_id BIGINT,
    reservation_date DATETIME
);

CREATE TABLE record (
                        id int AUTO_INCREMENT PRIMARY KEY,        -- 自增主键
                        bid varchar(255) NOT NULL,                          -- 书籍ID，外键
                        user_id int NOT NULL,                     -- 用户ID，外键
                        action_date VARCHAR(255) NOT NULL,               -- 动作日期
                        action_type ENUM('BORROW', 'RETURN','REBORROW') NOT NULL, -- 动作类型
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);