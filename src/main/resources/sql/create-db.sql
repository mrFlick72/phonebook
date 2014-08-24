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
      firstName varchar_ignorecase(500) not null,
      lastName varchar_ignorecase(500) not null,

      username varchar_ignorecase(50) not null,
      password varchar_ignorecase(500) not null,
      securityRole varchar_ignorecase(50) not null
);