drop table contact if exists;
drop table phoneBookUser if exists;

CREATE TABLE contact(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      firstName VARCHAR(255),
      lastName VARCHAR(255),
      telephoneNumber VARCHAR (15),
      birth timestamp,
      phoneBookUser INT
);

create table phoneBookUser(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      firstName varchar(500) not null,
      lastName varchar(500) not null,

      mail varchar(500) not null,

      username varchar(50) not null,
      password varchar(500) not null,
      securityRole varchar(50) not null
);

create table nonce(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      nonce varchar(512) not null unique ,
      userName varchar(500) not null,

      start timestamp  not null,

      stop timestamp  not null,
      used INTEGER (1) not null,
);
