/* Reset Final Project Database */

drop database finalproject;
create database finalproject;

use finalproject;

drop table if exists Assignments;
drop table if exists Courses;
drop table if exists StudentRecords;

create table Assignments (
       id int not null primary key auto_increment,
       courseID varchar(50) not null,
       studentRecordID integer,
       dueDate date not null,
       points integer not null,
       score integer not null,
       name varchar(50) not null,
       assignmentType char(4) not null);

insert into Assignments values(null, 'Math 54', null, now(), 20, -1, 'Homework 1', 'HWRK');
insert into Assignments values(null, 'Math 54', null, now(), 20, -1, 'Homework 2', 'HWRK');
insert into Assignments values(null, 'Math 54', null, now(), 20, -1, 'Quiz 1', 'QUIZ');
insert into Assignments values(null, 'Math 54', null, now(), 20, -1, 'Quiz 2', 'QUIZ');
insert into Assignments values(null, 'Math 54', null, now(), 20, -1, 'Test 1', 'TEST');
insert into Assignments values(null, 'Math 54', null, now(), 20, -1, 'Test 2', 'TEST');


commit;
