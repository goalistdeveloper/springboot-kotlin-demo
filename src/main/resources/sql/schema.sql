CREATE EXTENSION IF NOT EXISTS "pgcrypto";

create table if not exists profiles
(
    id                varchar(36) default gen_random_uuid(),
    profile_photo_url varchar(255) default null,
    address           varchar(255) not null,
    primary key (id)
);

create table if not exists users
(
    id         varchar(36)  default gen_random_uuid(),
    name       varchar(255) not null,
    password   varchar(255) not null,
    profile_id varchar(36),
    primary key (id),
    constraint fk_users_profile_id foreign key (profile_id) references profiles(id)
);
