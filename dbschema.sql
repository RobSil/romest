drop table if exists public.users cascade;
drop table if exists public.roles cascade;
drop table if exists public.users_roles cascade;
drop table if exists public.properties cascade;
drop table if exists public.photos cascade;
drop table if exists public.posts cascade;
drop table if exists public.avatars cascade;

create table if not exists public.users (
                                            id bigint not null generated always as identity,
                                            created_date timestamp without time zone default CURRENT_TIMESTAMP not null,
                                            last_modified_date timestamp without time zone,

                                            email character varying(64) not null,
    password_hash character varying(512) not null,
    is_blocked bool not null,

    constraint users_pkey primary key (id)
    );

create table if not exists public.roles (
                                            id bigint not null generated always as identity,
                                            created_date timestamp without time zone default current_timestamp,
                                            last_modified_date timestamp without time zone,

                                            title character varying (32) not null,

    constraint roles_pkey primary key (id)
    );

create table if not exists public.users_roles (
                                                  id bigint not null generated always as identity,
                                                  created_date timestamp without time zone default current_timestamp,
                                                  last_modified_date timestamp without time zone,

                                                  user_id bigint not null,
                                                  role_id bigint not null,

                                                  constraint user_role_pkey primary key (id),
    constraint fk_users foreign key (user_id) references public.users(id),
    constraint fk_roles foreign key (role_id) references public.roles(id)
    );

create table if not exists public.properties (
                                                 id bigint not null generated always as identity,
                                                 created_date timestamp without time zone default current_timestamp,
                                                 last_modified_date timestamp without time zone,

                                                 name character varying (64) not null,
    value character varying (64) not null,

    constraint properties_pkey primary key (id)
    );

create table if not exists public.posts (
                                            id bigint not null generated always as identity,
                                            created_date timestamp without time zone default current_timestamp,
                                            last_modified_date timestamp without time zone,

                                            title character varying (256) null,
    text character varying (8192) not null,

    constraint posts_pkey primary key (id)
    );

create table if not exists public.photos (
                                             id bigint not null generated always as identity,
                                             created_date timestamp without time zone default current_timestamp,
                                             last_modified_date timestamp without time zone,

                                             path character varying (1024) not null,
    post_id bigint not null,

    constraint photos_pkey primary key (id),
    constraint fk_posts foreign key (post_id) references public.posts(id)
    );

create table if not exists public.avatars (
                                              id bigint not null generated always as identity,
                                              created_date timestamp without time zone default current_timestamp,
                                              last_modified_date timestamp without time zone,

                                              path character varying (1024) not null,
    user_id bigint not null,

    constraint avatars_pkey primary key (id),
    constraint fk_users foreign key (user_id) references public.users(id)
    );
