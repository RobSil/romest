drop table if exists public.users cascade;
drop table if exists public.roles cascade;
drop table if exists public.users_roles cascade;
drop table if exists public.properties cascade;
drop table if exists public.photos cascade;
drop table if exists public.posts cascade;
drop table if exists public.tags cascade;
drop table if exists public.posts_tags;
drop table if exists public.likes;
drop table if exists public.avatars cascade;
drop table if exists public.boards cascade;

create table if not exists public.users
(
    id                 bigint                                                not null generated always as identity,
    created_date       timestamp without time zone default CURRENT_TIMESTAMP not null,
    last_modified_date timestamp without time zone,

    username           character varying(64)                                 not null unique,
    email              character varying(64)                                 not null unique,
    password_hash      character varying(512)                                not null,
    is_blocked         bool                                                  not null,

    constraint users_pkey primary key (id)
);

create table if not exists public.roles
(
    id                 bigint                not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    title              character varying(32) not null,

    constraint roles_pkey primary key (id)
);

create table if not exists public.users_roles
(
    id                 bigint not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    user_id            bigint not null,
    role_id            bigint not null,

    constraint user_role_pkey primary key (id),
    constraint fk_users foreign key (user_id) references public.users (id),
    constraint fk_roles foreign key (role_id) references public.roles (id)
);

create table if not exists public.properties
(
    id                 bigint                not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    name               character varying(64) not null,
    value              character varying(64) not null,

    constraint properties_pkey primary key (id)
);

create table if not exists public.boards
(
    id                 bigint                not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    name               character varying(64) not null,
    minimized_name     character varying(64) not null,
    is_private         bool                  not null,

    user_id            bigint                not null,

    unique (minimized_name, user_id),

    constraint boards_pkey primary key (id),
    constraint fk_user foreign key (user_id) references public.users (id)
);

-- create table if not exists public.posts_boards
-- (
--     id                 bigint not null generated always as identity,
--     created_date       timestamp without time zone default current_timestamp,
--     last_modified_date timestamp without time zone,
--
--     post_id            bigint not null,
--     board_id           bigint not null,
--
--     constraint post_board_pkey primary key (id),
--     constraint fk_posts foreign key (post_id) references public.posts (id),
--     constraint fk_boards foreign key (board_id) references public.boards (id)
-- );

create table if not exists public.photos
(
    id                 bigint                  not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    path               character varying(1024) not null,
    storing_source     character varying(128)  not null,
    image_kit_id       character varying(128)  not null,
    image_kit_url      character varying(512)  not null,
--     post_id            bigint                  not null,

    constraint photos_pkey primary key (id)
--     constraint fk_posts foreign key (post_id) references public.posts (id)
);

create table if not exists public.tags
(
    id                 bigint                not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    title              character varying(64) not null unique,

    constraint tags_pkey primary key (id)
);

create table if not exists public.posts
(
    id                 bigint                  not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    title              character varying(256)  null,
    text               character varying(8192) null,

    board_id           bigint                  not null,
    photo_id           bigint                  not null,

    constraint posts_pkey primary key (id),
    constraint fk_board foreign key (board_id) references public.boards (id),
    constraint fk_photo foreign key (photo_id) references public.photos (id)
);

create table if not exists public.posts_tags
(
    id                 bigint not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    post_id            bigint not null,
    tag_id             bigint not null,

    constraint posts_tags_pkey primary key (id),
    constraint fk_post foreign key (post_id) references public.posts (id),
    constraint fk_tag foreign key (tag_id) references public.tags (id)
);

create table if not exists public.likes
(
    id                 bigint not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    post_id            bigint not null,
    user_id            bigint not null,

    constraint likes_pkey primary key (id),
    constraint fk_post foreign key (post_id) references public.posts (id),
    constraint fk_user foreign key (user_id) references public.users (id)
);

create table if not exists public.avatars
(
    id                 bigint                  not null generated always as identity,
    created_date       timestamp without time zone default current_timestamp,
    last_modified_date timestamp without time zone,

    path               character varying(1024) not null,
    user_id            bigint                  not null,

    constraint avatars_pkey primary key (id),
    constraint fk_users foreign key (user_id) references public.users (id)
);
