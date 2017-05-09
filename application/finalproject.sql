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
       if int not null,
       grade double(10, 5) not null);

insert into Courses values('Math 54', .2, .3, .5);

insert into StudentRecords(1, 'Math 54', 'Alex');
insert into StudentRecords(2, 'Math 54', 'Charlie');
insert into StudentRecords(3, 'Math 54', 'Sarah');

insert into Assignments values(1, 'Math 54', now(), 20, 'Homework 1', 'HWRK');
insert into Assignments values(2, 'Math 54', now(), 20, 'Homework 2', 'HWRK');
insert into Assignments values(3, 'Math 54', now(), 20, 'Quiz 1', 'QUIZ');
insert into Assignments values(4, 'Math 54', now(), 20, 'Quiz 2', 'QUIZ');
insert into Assignments values(5, 'Math 54', now(), 20, 'Test 1', 'TEST');
insert into Assignments values(6, 'Math 54', now(), 20, 'Test 2', 'TEST');


commit;
