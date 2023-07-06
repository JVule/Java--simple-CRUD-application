
create table if not exists roditelj
(
    id          integer not null primary key,
    ime         CHARACTER VARYING(30) not null,
    prezime     CHARACTER VARYING(30) not null,
    ime_djeteta CHARACTER VARYING(60) not null
);

create table if not exists grupa (
                                     id      integer not null primary key,
                                     naziv   character varying(30) not null
);

create table if not exists dijete (
                                      id              integer not null primary key,
                                      ime             character varying(30) not null,
                                      prezime         character varying(30) not null,
                                      dob             int not null,
                                      roditelj_id     integer references RODITELJ(id) on delete set null,
                                      grupa_id        integer references grupa(id) on delete set null
);

create table if not exists odgajatelj
(
    id          integer not null primary key,
    ime         CHARACTER VARYING(30) not null,
    prezime     CHARACTER VARYING(30) not null,
    id_grupe    integer references grupa(id) on delete set null,
    placa       int not null
);

create table if not exists ocjena_rada_odgajatelj (
                                                      odgajatelj_id   integer references odgajatelj(id) on delete cascade,
                                                      ocjena          int not null
);

create table if not exists dolazak (
                                       id              integer not null primary key,
                                       roditelj_id     integer references roditelj(id) on delete set null,
                                       dijete_id       integer references dijete(id) on delete set null,
                                       odgajatelj_id   integer references odgajatelj(id) on delete set null,
                                       vrijeme_dolaska timestamp not null default current_timestamp(),
                                       naziv_grupe     character varying(30) not null
);

create table if not exists jelo (
                                    id              integer not null primary key,
                                    naziv           character varying(30) not null,
                                    masti           int,
                                    proteini        int,
                                    ugljikohidrati  int
);

create table if not exists kuharica
(
    id          integer not null primary key,
    ime         CHARACTER VARYING(30) not null,
    prezime     CHARACTER VARYING(30) not null,
    placa       int not null,
    smjena      character varying(10) not null,
    jelo_id     integer references jelo(id) on delete set null
);

create table if not exists ocjena_rada_kuharica (
                                                    kuharica_id     integer references kuharica(id) on delete cascade,
                                                    ocjena          int not null
);



insert into RODITELJ(id, ime, prezime, ime_djeteta)
values
    (1, 'Ana', 'Matic', 'Maja Matic'),
    (2, 'Petar', 'Radic', 'Elena Radic'),
    (3, 'Antun', 'Majer', 'Tin Majer')
;

insert into GRUPA (id, NAZIV)
values
    ( 1, 'grupa Jagodice' ),
    (2, 'grupa Kesteni')
;

insert into DIJETE(id, ime, prezime, dob, roditelj_id, grupa_id)
values
    (1, 'Maja', 'Matic', 8, 1, 2),
    (2, 'Elena', 'Radic', 6, 2, 1),
    (3, 'Tin', 'Majer', 7, 3, 1)
;

insert into ODGAJATELJ (id, IME, PREZIME, ID_GRUPE, PLACA)
values
    (1, 'Renata', 'Jakic', 1, 9000),
    (2, 'Anita', 'Sever', 2, 9000)
;

insert into OCJENA_RADA_ODGAJATELJ
values
    (1, 5),
    (1, 4),
    (2, 5),
    (2, 5)
;

insert into DOLAZAK(id, roditelj_id, dijete_id, odgajatelj_id, vrijeme_dolaska, naziv_grupe)
values
    (1, 1, 1, 1, parsedatetime('2023-01-11 11:47:52', 'yyyy-MM-dd hh:mm:ss'), 'grupa Jagodice'),
    (2, 2, 2, 2, parsedatetime('2023-01-12 10:35:12', 'yyyy-MM-dd hh:mm:ss'), 'grupa Kesteni'),
    (3, 3, 3, 1, parsedatetime('2023-01-13 08:24:13', 'yyyy-MM-dd hh:mm:ss'), 'grupa Jagodice'),
    (4, 3, 3, 2, parsedatetime('2023-01-14 09:45:14', 'yyyy-MM-dd hh:mm:ss'), 'grupa Jagodice'),
    (5, 2, 2, 1, parsedatetime('2023-01-15 10:23:24', 'yyyy-MM-dd hh:mm:ss'), 'grupa Kesteni'),
    (6, 3, 3, 2, parsedatetime('2023-01-16 09:41:52', 'yyyy-MM-dd hh:mm:ss'), 'grupa Kesteni'),
    (7, 1, 1, 1, parsedatetime('2023-01-17 08:23:32', 'yyyy-MM-dd hh:mm:ss'), 'grupa Kesteni'),
    (8, 1, 1, 2, parsedatetime('2023-01-18 09:42:41', 'yyyy-MM-dd hh:mm:ss'), 'grupa Jagodice')
;

insert into JELO(id, naziv, masti, proteini, ugljikohidrati)
values
    (1, 'tjestenina Bolognese', 5, 30, 40),
    (2, 'cufte s krumpirom', 11, 28, 41)
;

insert into KUHARICA(id, ime, prezime, placa, smjena, jelo_id)
values
    (1, 'Ivana', 'Jakic', 7000, 'ujutro', 2),
    (2, 'Jadranka', 'Susic', 7000, 'popodne', 1)
;

insert into OCJENA_RADA_KUHARICA(kuharica_id, ocjena)
values
    (1, 5),
    (1, 5),
    (1, 4),
    (2, 5),
    (2, 5)
;