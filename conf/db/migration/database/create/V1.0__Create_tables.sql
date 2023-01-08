create table Companies
(
    Inn  INTEGER      not null
        primary key,
    Name VARCHAR(254) not null
);

create table Employees
(
    Id           INTEGER      not null
        primary key autoincrement,
    CompanyInnFK INTEGER      not null
        constraint Company_inn_fk
            references Companies,
    Name         VARCHAR(254) not null,
    Surname      VARCHAR(254) not null,
    Salary       INTEGER      not null
);