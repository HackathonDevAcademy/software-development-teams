create table developer
(
    id                      bigserial not null,
    activation_token        varchar(255),
    email                   varchar(255),
    full_name               varchar(255),
    password                varchar(255),
    position                varchar(255),
    reset_token             varchar(255),
    reset_token_expire_time timestamp,
    role                    varchar(255),
    status                  varchar(255),
    team_id                 int8,
    primary key (id)
);

create table task
(
    id           bigserial not null,
    description  varchar(255),
    end_date     timestamp,
    start_date   timestamp,
    status       varchar(255),
    title        varchar(255),
    developer_id int8,
    primary key (id)
);

create table team
(
    id          bigserial not null,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);

alter table developer
    add constraint email_constraint unique (email);

alter table developer
    add constraint team_id_constraint foreign key (team_id) references team;

alter table task
    add constraint developer_id_constraint foreign key (developer_id) references developer;