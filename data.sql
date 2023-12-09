create database ruri;

-- 用户表
create table user (
    userID UUID primary key ,
    userName varchar(50) not null unique ,
    email varchar(100) not null unique ,
    password varchar(100) not null ,
    create_at timestamp ,
    last_join_at timestamp ,
    is_deleted boolean default false
);

-- 小说表
create table novel (
    novelID int auto_increment primary key ,
    name varchar(50) not null ,
    description varchar(255) not null ,
    authorID UUID not null,
    create_at timestamp ,
    update_at timestamp ,
    is_hidden boolean default false,
    foreign key (authorID) references user (userID)
);

-- 章节表
create table chapter (
    chapterID int auto_increment,
    novelID int,
    title varchar(100),
    content text,
    create_at timestamp,
    status int,
    is_deleted boolean default false,
    primary key (chapterID, novelID),
    foreign key (novelID) references novel (novelID)
);

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

-- 阅读表
create table progress (
    userID UUID,
    novelID int,
    chapterID varchar(50),
    primary key (userID, novelID, chapterID),
    foreign key (userID) references user (userID),
    foreign key (novelID) references novel (novelID),
    foreign key (novelID, chapterID) references chapter (novelID, chapterID)
);
