CREATE TABLE contact(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      firstName VARCHAR(255),
      lastName VARCHAR(255),
      telephoneNumber VARCHAR (15),
      birth timestamp
);

create table phonBookUser(
      id INT IDENTITY NOT NULL PRIMARY KEY ,
      username varchar_ignorecase(50) not null,
      password varchar_ignorecase(500) not null,

      firstName varchar_ignorecase(500) not null,
      lastName varchar_ignorecase(500) not null
);