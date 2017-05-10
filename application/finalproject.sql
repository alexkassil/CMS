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

insert into StudentRecords values(null, 'Math 54', 'Alex');
insert into StudentRecords values(null, 'Math 54', 'Charlie');
insert into StudentRecords values(null, 'Math 54', 'Sarah');

insert into Assignments values(null, 'Math 54', now(), 20, 'Homework 1', 'HWRK');
insert into Assignments values(null, 'Math 54', now(), 20, 'Homework 2', 'HWRK');
insert into Assignments values(null, 'Math 54', now(), 20, 'Quiz 1', 'QUIZ');
insert into Assignments values(null, 'Math 54', now(), 20, 'Quiz 2', 'QUIZ');
insert into Assignments values(null, 'Math 54', now(), 20, 'Test 1', 'TEST');
insert into Assignments values(null, 'Math 54', now(), 20, 'Test 2', 'TEST');

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

insert into Courses values('Comp 232', .5, .2, .3);

insert into StudentRecords values(4, 'Comp 232', 'Matt');
insert into StudentRecords values(5, 'Comp 232', 'Claire');
insert into StudentRecords values(6, 'Comp 232', 'Jackie');

insert into Assignments values(7, 'Comp 232', now(), 50, 'Homework 1', 'HWRK');
insert into Assignments values(8, 'Comp 232', now(), 100, 'Homework 2', 'HWRK');
insert into Assignments values(9, 'Comp 232', now(), 100, 'Homework 3', 'HWRK');
insert into Assignments values(10, 'Comp 232', now(), 100, 'Homework 4', 'HWRK');
insert into Assignments values(11, 'Comp 232', now(), 20, 'Quiz 1', 'QUIZ');
insert into Assignments values(12, 'Comp 232', now(), 200, 'Final', 'TEST');

insert into Grades values('Comp 232', 4, 7, 1);
insert into Grades values('Comp 232', 4, 8, 1);
insert into Grades values('Comp 232', 4, 9, .9);
insert into Grades values('Comp 232', 4, 10, .94);
insert into Grades values('Comp 232', 4, 11, .80);
insert into Grades values('Comp 232', 4, 12, 1.03);

insert into Grades values('Comp 232', 5, 7, .95);
insert into Grades values('Comp 232', 5, 8, 1.5);
insert into Grades values('Comp 232', 5, 9, .89);
insert into Grades values('Comp 232', 5, 10, .98);
insert into Grades values('Comp 232', 5, 11, .89);
insert into Grades values('Comp 232', 5, 12, .99);

insert into Grades values('Comp 232', 6, 7, .69);
insert into Grades values('Comp 232', 6, 8, .72);
insert into Grades values('Comp 232', 6, 9, .54);
insert into Grades values('Comp 232', 6, 10, .87);
insert into Grades values('Comp 232', 6, 11, .9);
insert into Grades values('Comp 232', 6, 12, 1);



commit;
