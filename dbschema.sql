drop table if exists public.users cascade;
drop table if exists public.roles cascade;
drop table if exists public.users_roles cascade;
drop table if exists public.properties cascade;

create table if not exists public.users (
    id bigint not null generated always as identity,
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    version bigint,

    email character varying(64) not null,
    passwordHash character varying(512) not null,
    is_blocked bool not null,

    constraint users_pkey primary key (id)
);

create table if not exists public.roles (
    id bigint not null generated always as identity,
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    version bigint,

    title character varying (32) not null,

    constraint roles_pkey primary key (id)
);

create table if not exists public.users_roles (
    id bigint not null generated always as identity,
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    version bigint,

    user_id bigint not null,
    role_id bigint not null,

    constraint user_role_pkey primary key (id),
    constraint fk_users foreign key (user_id) references public.users(id),
    constraint fk_roles foreign key (role_id) references public.roles(id)
);

create table if not exists public.properties (
    id bigint not null generated always as identity,
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    version bigint,

    name character varying (64) not null,
    value character varying (64) not null,

    constraint properties_pkey primary key (id)
);