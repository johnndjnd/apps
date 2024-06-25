create table user_favor_class
(
    id       int auto_increment comment 'Primary Key'
        primary key,
    user_id  int null,
    class_id int null,
    constraint user_favor_class_ibfk_1
        foreign key (user_id) references user (id)
            on update cascade on delete cascade,
    constraint user_favor_class_ibfk_2
        foreign key (class_id) references class (id)
);

create index class_id
    on user_favor_class (class_id);

create index user_id
    on user_favor_class (user_id);

INSERT INTO jiaoji.user_favor_class (id, user_id, class_id) VALUES (1, 2, 5);
