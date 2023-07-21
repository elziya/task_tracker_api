create table account(
    id bigserial primary key,
    first_name varchar,
    last_name varchar,
    email varchar(150) not null,
    password varchar,
    role varchar(50) not null,
    state varchar(50) not null,
    confirm_code uuid default uuid_generate_v4()
);

create table project(
    id serial primary key,
    name varchar(100) not null,
    start_date timestamp not null,
    end_date timestamp not null,
    account_id integer not null,
    foreign key (account_id) references account(id)
);

create table task(
    id serial primary key,
    name varchar(50),
    duration integer default 0,
    project_id integer not null,
    foreign key (project_id) references project(id)
);

create table file_info(
      id serial primary key,
      size bigint not null,
      content_type varchar(200) not null,
      orig_name varchar(200) not null,
      storage_name varchar(200) not null,
      task_id integer not null,
      foreign key (task_id) references task(id)
);
