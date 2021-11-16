DROP TABLE USER IF EXISTS;
CREATE TABLE USER
(
    `ID`   int(11) NOT NULL,
    `NAME` varchar(255) DEFAULT NULL,
    `MAIL` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`ID`)
);

INSERT INTO USER(ID, NAME, MAIL)
VALUES (1, 'John', 'john@mail.com');

INSERT INTO USER(ID, NAME, MAIL)
VALUES (2, 'Jack', 'jack@mail.com');