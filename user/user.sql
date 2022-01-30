CREATE TABLE utente (
    id bigint NOT NULL,
    is_admin bit NOT NULL,
    mail varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id),
     UNIQUE (mail)
);