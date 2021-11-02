create table bands (
    band_id integer primary key generated always as identity,
    band_name varchar (60) not null unique
);

create table albums (
    album_id integer primary key generated always as identity,
    album_name varchar(60) not null,
    release_year varchar(4) not null,
    band_id integer references bands(band_id) not null,
    current_rating integer default 0,
    total_rating integer default 0,
    rating_update_counter integer default 0,
    album_picture_name varchar not null unique
);


create table tags (
    tag_id integer primary key generated always as identity,
    tag_name varchar (60) not null unique
);

create table albums_tags(
    album_id integer references albums(album_id),
    tag_id integer references tags(tag_id),
    primary key (album_id, tag_id)
);

create table users (
    user_id integer primary key generated always as identity,
    user_name varchar(60) not null unique,
    user_password varchar(255) not null,
    user_role varchar(60)
);


insert into bands (band_name) values ('Metallica');
insert into bands (band_name) values ('AC/DC');
insert into bands (band_name) values ('Cinderella');
insert into bands (band_name) values ('Pink Floyd');
insert into bands (band_name) values ('Rammstein');
insert into bands (band_name) values ('Motley Crue');

insert into albums (album_name, release_year, band_id, album_picture_name) values ('Master Of Puppets', '1986', 1, '1.jpg');
insert into albums (album_name, release_year, band_id, album_picture_name) values ('...And Justice For All', '1988', 1, '2.jpg');
insert into albums (album_name, release_year, band_id, album_picture_name) values ('Back In Black', '1980', 2, '3.jpg');
insert into albums (album_name, release_year, band_id, album_picture_name) values ('Long Cold Winter', '1988', 3, '4.jpg');
insert into albums (album_name, release_year, band_id, album_picture_name) values ('The Wall', '1979', 4, '5.jpg');
insert into albums (album_name, release_year, band_id, album_picture_name) values ('Rammstein', '2019', 5, '6.jpg');
insert into albums (album_name, release_year, band_id, album_picture_name) values ('Dr. Feelgood', '1989', 6, '7.jpg');

insert into tags (tag_name) values ('thrash-metal');
insert into tags (tag_name) values ('heavy-metal');
insert into tags (tag_name) values ('hard-rock');
insert into tags (tag_name) values ('glam-rock');
insert into tags (tag_name) values ('electronic-rock');
insert into tags (tag_name) values ('industrial');

insert into albums_tags(album_id, tag_id) values (1, 1);
insert into albums_tags(album_id, tag_id) values (1, 2);
insert into albums_tags(album_id, tag_id) values (2, 1);
insert into albums_tags(album_id, tag_id) values (2, 2);
insert into albums_tags(album_id, tag_id) values (3, 3);
insert into albums_tags(album_id, tag_id) values (4, 4);
insert into albums_tags(album_id, tag_id) values (5, 3);
insert into albums_tags(album_id, tag_id) values (5, 5);
insert into albums_tags(album_id, tag_id) values (6, 6);

insert into users (user_name, user_password, user_role) values ('admin', '$2a$12$sz7UTS45pjZ79DxbAa3h8u4JsX9uyXpq2uqjJgI5uBXW.IPHGkW9C', 'ADMIN');
insert into users (user_name, user_password, user_role) values ('user', '$2a$12$ME4Vo1NjyMuZ/2TWmvLvruP1LeDdb2jUVLTiIHSmxV5ZfalmtyQjm', 'USER');


