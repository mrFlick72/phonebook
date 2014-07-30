CREATE TABLE contact(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      nome VARCHAR(255),
      cognome VARCHAR(255),
      telefono VARCHAR (15),
      nascita timestamp
);

create table phonBookUser(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      username varchar_ignorecase(50) not null,
      password varchar_ignorecase(500) not null,

      firstName varchar_ignorecase(500) not null,
      lastName varchar_ignorecase(500) not null,
      autority varchar_ignorecase(50) not null,

      enabled boolean not null
);