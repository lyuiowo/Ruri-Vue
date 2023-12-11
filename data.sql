create database ruri;

-- 用户表
create table user (
    userID UUID primary key ,
    userName varchar(50) not null unique ,
    email varchar(100) not null unique ,
    password varchar(100) not null ,
    create_at timestamp ,
    last_join_at timestamp ,
    is_deleted boolean not null default false
);

-- 小说表
create table novel (
    novelID int auto_increment primary key ,
    name varchar(50) not null ,
    description varchar(255) not null ,
    authorID UUID not null,
    create_at timestamp ,
    update_at timestamp ,
    is_hidden boolean not null default false,
    total_chapter_num int not null  default 0,
    foreign key (authorID) references user (userID)
);

-- 获取章节数量触发器
DELIMITER //
CREATE TRIGGER update_total_chapter_num
AFTER INSERT ON chapter
FOR EACH ROW
BEGIN
    UPDATE novel n
    SET n.total_chapter_num = (
        SELECT COUNT(*)
        FROM chapter c
        WHERE c.novelID = n.novelID
    )
    WHERE n.novelID = NEW.novelID;
END;
//
DELIMITER ;

-- 章节表
create table chapter (
    chapterID int auto_increment,
    novelID int,
    title varchar(100),
    content text,
    create_at timestamp,
    status int not null default 0,
    is_deleted boolean not null default false,
    primary key (chapterID, novelID),
    foreign key (novelID) references novel (novelID)
);

-- 章节 ID 自增触发器
DELIMITER //
CREATE TRIGGER increment_chapterID
BEFORE INSERT ON chapter
FOR EACH ROW
BEGIN
    DECLARE maxChapterID INT;
    SET maxChapterID = (SELECT COALESCE(MAX(chapterID), 0) FROM chapter WHERE novelID = NEW.novelID);
    SET NEW.chapterID = maxChapterID + 1;
END;
//
DELIMITER ;