create table rmk_likes
(
    id     int auto_increment
        primary key,
    uid    int                  not null,
    rmk_id int                  null,
    liked  tinyint(1) default 1 null,
    constraint rmk_likes_remarks_id_fk
        foreign key (rmk_id) references remarks (id)
            on update cascade on delete cascade,
    constraint rmk_likes_user_id_fk
        foreign key (uid) references user (id)
);

INSERT INTO jiaoji.rmk_likes (id, uid, rmk_id, liked) VALUES (1, 10, 3, false);
INSERT INTO jiaoji.rmk_likes (id, uid, rmk_id, liked) VALUES (2, 10, 4, false);
INSERT INTO jiaoji.rmk_likes (id, uid, rmk_id, liked) VALUES (3, 10, 49, false);
