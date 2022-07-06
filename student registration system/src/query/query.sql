use studentregistration

create table coursecategory (categoryID int not null AUTO_INCREMENT primary key,categoryName varchar(45))

create table classroom(classroomID int not null AUTO_INCREMENT primary key,classroomName varchar(25))

create table lecturer(lecturerID int not null AUTO_INCREMENT primary key,lecturerName varchar(35))
create table course
(courseID int not null AUTO_INCREMENT primary key,
courseName varchar(40),
fee int,
categoryID int,
FOREIGN KEY (categoryID) REFERENCES coursecategory(categoryID))

create table student
(studentID int not null AUTO_INCREMENT primary key,
studentName varchar(40),
address varchar(40),
phoneNo varchar(15),
dateofbirdth Date,
NRC varchar(20),
email varchar(25))

create table schedule
(scheduleID int not null AUTO_INCREMENT primary key,
starttime Time,
endtime Time,
startDate Date,
endDate Date,
totalUser int,
courseID int,
FOREIGN KEY (courseID) REFERENCES course(courseID),
classroomID int,
FOREIGN KEY (classroomID) REFERENCES classroom(classroomID),
lecturerID int,
FOREIGN KEY (lecturerID) REFERENCES lecturer(lecturerID))


create table registration
(registrationID int not null AUTO_INCREMENT primary key,
registrationDate Date,
studentID int,
FOREIGN KEY (studentID) REFERENCES student(studentID),
scheduleID int,
FOREIGN KEY (scheduleID) REFERENCES schedule(scheduleID),
amount double)



