-- noinspection SqlNoDataSourceInspectionForFile
-- noinspection SqlDialectInspectionForFile

CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE mykeyspace;

CREATE TABLE user (
   userid BIGINT,
   username VARCHAR,
   firstname VARCHAR,
   lastname VARCHAR,
   age INT,
   PRIMARY KEY (userid)
);

CREATE TABLE call (
   callid BIGINT,
   calloutnumber VARCHAR,
   callinnuber VARCHAR,
   PRIMARY KEY (callid)
);

INSERT INTO user (userid, username, firstname, lastname, age)
VALUES (99050,'teddn93','Ted', 'Hovards', 43);

INSERT INTO user (userid, username, firstname, lastname, age)
VALUES (934091,'johnnee123','John', 'Ostins', 32);

INSERT INTO user (userid, username, firstname, lastname, age)
VALUES (9924093,'colinF213','Colin', 'Farel', 37);

INSERT INTO call (callid, calloutnumber, callinnuber)
VALUES (115301,'+847368273948','+847284736482');

INSERT INTO call (callid, calloutnumber, callinnuber)
VALUES (677402,'+883749583912','+857371623748');

INSERT INTO call (callid, calloutnumber, callinnuber)
VALUES (3646483,'+876472847361','+859374893819');


