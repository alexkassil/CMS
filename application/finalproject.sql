/* Reset Final Project Database */

drop database finalproject;
create database finalproject;

use finalproject;

drop table if exists Assignments;
drop table if exists Courses;
drop table if exists StudentRecords;
drop table if exists Grades;

create table Assignments (
       id int not null primary key auto_increment,
       courseID varchar(50) not null,
       dueDate date not null,
       points integer not null,
       name varchar(50) not null,
       assignmentType char(4) not null);

create table Courses (
       courseID varchar(50) not null primary key,
       hwWeight double(10, 5) not null,
       quizWeight double(10, 5) not null,
       testWeight double(10, 5) not null);

create table StudentRecords (
       studentRecordID int not null primary key auto_increment,
       courseID varchar(50) not null,
       studentName varchar(50) not null);

create table Grades (
       courseID varchar(50) not null,
       studentRecordID int not null,
       id int not null,
       grade double(10, 5) not null);

insert into Courses values('Math 54', .2, .3, .5);

insert into StudentRecords values(1, 'Math 54', 'Alex');
insert into StudentRecords values(2, 'Math 54', 'Charlie');
insert into StudentRecords values(3, 'Math 54', 'Sarah');

insert into Assignments values(1, 'Math 54', now(), 20, 'Homework 1', 'HWRK');
insert into Assignments values(2, 'Math 54', now(), 20, 'Homework 2', 'HWRK');
insert into Assignments values(3, 'Math 54', now(), 20, 'Quiz 1', 'QUIZ');
insert into Assignments values(4, 'Math 54', now(), 20, 'Quiz 2', 'QUIZ');
insert into Assignments values(5, 'Math 54', now(), 20, 'Test 1', 'TEST');
insert into Assignments values(6, 'Math 54', now(), 20, 'Test 2', 'TEST');

insert into Grades values('Math 54', 1, 1, 1.5);
insert into Grades values('Math 54', 1, 2, 1);
insert into Grades values('Math 54', 1, 3, 0);
insert into Grades values('Math 54', 1, 4, .9);
insert into Grades values('Math 54', 1, 5, 1.0001);
insert into Grades values('Math 54', 1, 6, .949542);

insert into Grades values('Math 54', 2, 1, .95);
insert into Grades values('Math 54', 2, 2, .5432);
insert into Grades values('Math 54', 2, 3, 1.01);
insert into Grades values('Math 54', 2, 4, 1.1);
insert into Grades values('Math 54', 2, 5, .43);
insert into Grades values('Math 54', 2, 6, 1.5);

insert into Grades values('Math 54', 3, 1, 1.5);
insert into Grades values('Math 54', 3, 2, 1.0);
insert into Grades values('Math 54', 3, 3, .94);
insert into Grades values('Math 54', 3, 4, .67);
insert into Grades values('Math 54', 3, 5, .8787878);
insert into Grades values('Math 54', 3, 6, .444444444);

commit;
