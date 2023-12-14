create database ruri;
use ruri;

drop table user;
drop table novel;
drop table chapter;
drop table idea;

-- 用户表
create table user (
    userID UUID primary key ,
    userName varchar(50) not null unique ,
    avatar varchar(255) null ,
    email varchar(100) not null unique ,
    password varchar(100) not null ,
    create_at timestamp ,
    last_join_at timestamp ,
    is_admin boolean not null default false,
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
    total_chapter_num int not null  default 0, -- 获取章节表中相同 novelID 的数量
    foreign key (authorID) references user (userID)
);

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

-- 灵感库
create table idea (
    ideaID int auto_increment ,
    authorID UUID not null ,
    title varchar(255) not null ,
    content text ,
    primary key (ideaID, authorID),
    foreign key (authorID) references user(userID)
);

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

-- 插入用户 默认密码：LiuLi1314
insert into
    user
        (userID, userName, avatar, email, password, create_at, last_join_at)
    value
        ('472041BE-C952-57A2-DB69-E22C207A5EF2', 'Admin', '',  'admin@ru.ri', '$2a$10$k0IIZIzAHP.gGFygjZ5ZgO3g4oNvPdFdy6juml5.IpXbQi2iGXfVa', '2023-11-13 12:00:00', '2023-11-13 12:00:00');

-- 插入小说
insert into
    novel
        (novelID, name, description, authorID, create_at, update_at)
    values
        (1, '测试书', '测试描述', '5029b867-5a90-44ef-a3d5-58fa157ca646', '2023-11-13 12:00:00', '2023-11-13 12:00:00'),
        (2, 'ruri', '测试描述', '5029b867-5a90-44ef-a3d5-58fa157ca646', '2023-11-13 12:00:00', '2023-11-13 12:00:00');

-- 插入章节
insert into
    chapter
        (chapterID, novelID, title, content, create_at, status)
    values
        (1, 1, '第一章测试', '测试内容', '2023-11-13 12:00:00', 1),
        (2, 1, '第一章测试', '测试内容', '2023-11-13 12:00:00', 0);
