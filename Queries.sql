CREATE DATABASE ScoresDB;

use ScoresDB;

DROP TABLE Teacher;
CREATE TABLE Teacher (
	  id INT PRIMARY KEY,
	  first_name VARCHAR(50) NOT NULL,
	  last_name VARCHAR(50) NOT NULL,
	  email VARCHAR(50) NOT NULL,
	  CONSTRAINT AK_Teacher_email UNIQUE (email)
);

DROP TABLE Student;
CREATE TABLE Student (
	  id INT PRIMARY KEY,
	  first_name VARCHAR(50) NOT NULL,
	  last_name VARCHAR(50) NOT NULL,
	  email VARCHAR(50) NOT NULL,
	  CONSTRAINT AK_Student_email UNIQUE (email)
);

DROP TABLE Course;
CREATE TABLE Course (
	id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    teacher_id INT NOT NULL,
    CONSTRAINT FK_Course_teacher_id FOREIGN KEY (teacher_id) REFERENCES Teacher(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE Student_Course;
CREATE TABLE Student_Course (
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student(id),
  FOREIGN KEY (course_id) REFERENCES Course(id)
);

DROP TABLE admin_credentials;
CREATE TABLE admin_credentials (
	login_name VARCHAR(50) NOT NULL,
    pwd varbinary(200) NOT NULL
);

CREATE TABLE EVALUATION (
eval_name varchar(50) not null,
eval_type enum ('Homework', 'Project', 'Assignment', 'Mid Term', 'Final Term') not null,
teacher_id int not null,
course_id int not null,
primary key (teacher_id, course_id, eval_name, eval_type),
foreign key (teacher_id) references Teacher(id) on update cascade on delete cascade,
FOREIGN KEY (course_id) REFERENCES Course(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table results(
score int not null check (score between 0 and 100),
eval_name varchar(50) not null,
eval_type varchar(20) not null,
course_id int not null,
student_id int not null,
primary key (eval_name, eval_type, course_id, student_id),
foreign key (student_id) references Student(id) on update cascade on delete cascade,
FOREIGN KEY (course_id) REFERENCES Course(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- insert into admin_credentials(login_name, pwd) 
-- values ("admin" , aes_encrypt('admin','admin'));

DELIMITER //
CREATE PROCEDURE insertTeacherData(
    id_p INT,
    email_p VARCHAR(50),
    first_name_p VARCHAR(50),
    last_name_p VARCHAR(50)
)
	BEGIN
		INSERT INTO Teacher (id, email, first_name, last_name)
		VALUES (id_p, email_p, first_name_p, last_name_p);
	END //
    
    
DELIMITER //
CREATE PROCEDURE insertStudentData(
    id_p INT,
    email_p VARCHAR(50),
    first_name_p VARCHAR(50),
    last_name_p VARCHAR(50)
)
	BEGIN
		INSERT INTO Student (id, email, first_name, last_name)
		VALUES (id_p, email_p, first_name_p, last_name_p);
	END //
    
DELIMITER //
CREATE PROCEDURE insertTeacherCourseData(
    id_p INT,
    name_p VARCHAR(50),
    tecaher_id_p INT
)
	BEGIN
		INSERT INTO Course (id, name, teacher_id)
		VALUES (id_p, name_p, tecaher_id_p);
END //

DELIMITER //
CREATE PROCEDURE insertStudentCourseData(
    student_id_p INT,
    course_id_p INT
)
	BEGIN
		INSERT INTO Student_Course (student_id, course_id)
		VALUES (student_id_p, course_id_p);
END //

DELIMITER //
CREATE PROCEDURE viewTeacherCourseData()
	BEGIN
		SELECT t.id, t.first_name, t.last_name, t.email, c.id AS course_id , c.name AS course_name 
        FROM Teacher t
        LEFT JOIN Course c ON c.teacher_id = t.id
        ORDER BY t.id;
END //

DELIMITER //
CREATE PROCEDURE viewStudentCourseData()
	BEGIN
		SELECT s.id, s.first_name, s.last_name, s.email, sc.course_id AS course_id , c.name AS course_name 
        FROM Student s
        JOIN Student_Course sc ON sc.student_id = s.id 
        JOIN Course c ON sc.course_id = c.id
        ORDER BY s.id;
END //

DELIMITER //
CREATE PROCEDURE getAllTeachersData(id INT)
	BEGIN
		SELECT t.id, t.first_name, t.last_name, t.email, c.id AS course_id , c.name AS course_name 
        FROM Teacher t
        LEFT JOIN Course c ON c.teacher_id = t.id WHERE t.id = id;
END //

DELIMITER //
CREATE PROCEDURE getAllStudentsData(id INT)
	BEGIN
		SELECT s.id, s.first_name, s.last_name, s.email, sc.course_id AS course_id , c.name AS course_name 
        FROM Student s
        LEFT JOIN Student_Course sc ON sc.student_id = s.id 
        LEFT JOIN Course c ON sc.course_id = c.id
        WHERE s.id = id;
END //

DELIMITER //
CREATE PROCEDURE updateTeacher(
	id_p INT,
    first_name_p VARCHAR(50),
    last_name_p VARCHAR(50),
    name_p VARCHAR(50),
    course_id_p INT
)
	BEGIN
		UPDATE Teacher t
        JOIN Course c ON t.id = c.teacher_id
        SET t.first_name = first_name_p,
			t.last_name = last_name_p,
			c.name = name_p WHERE t.id = id_p AND c.id = course_id_p;
END //

DELIMITER //
CREATE PROCEDURE updateStudent(
	id_p INT,
    first_name_p VARCHAR(50),
    last_name_p VARCHAR(50)
)
	BEGIN
		UPDATE Student s
        SET s.first_name = first_name_p,
			s.last_name = last_name_p
            WHERE s.id = id_p;
END //

DELIMITER //
CREATE PROCEDURE deleteTeacher(
	id_p INT
)
	BEGIN
		DELETE FROM Course WHERE teacher_id = id_p;
		DELETE FROM teacher WHERE id = id_p;
END //

DELIMITER //
CREATE PROCEDURE deleteStudent(
	id_p INT
)
	BEGIN
		DELETE FROM Student_Course WHERE student_id = id_p;
		DELETE FROM student WHERE id = id_p;
END //

DELIMITER //
CREATE FUNCTION readAdminCredentials(userId VARCHAR(50), passCode VARCHAR(200))
RETURNS BOOLEAN
READS SQL DATA
BEGIN
	DECLARE res_pwd VARCHAR(200);
    
    SELECT CONVERT(AES_DECRYPT(pwd, userId), CHAR(200)) INTO res_pwd FROM admin_credentials
    WHERE login_name = userId;
    
    IF res_pwd = passCode THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
        
	END IF;
END //

create table student_credentials(
id int unique not null,
pwd varbinary(200) not null,
foreign key (id) references Student(id) on update cascade on delete cascade
);

create table teacher_credentials(
id int unique not null,
pwd varbinary(200) not null,
foreign key (id) references Teacher(id) on update cascade on delete cascade
);

delimiter //
create procedure insertStudentLogin(
newId int, 
pwd varbinary(200)
)
begin 
insert into student_credentials values(newId, pwd);
select * from student_credentials where id = newId;
end //

delimiter //
create procedure insertTeacherLogin(
newId int, 
pwd varbinary(200)
)
begin 
insert into teacher_credentials values(newId, pwd);
select * from teacher_credentials where id = newId;
end //

delimiter //
create function validateTeacherLogin(userId VARCHAR(50), passCode varchar(200))
returns boolean
reads sql data
begin
	declare res_pwd varchar(200);
    
    select cast(pwd as char character set utf8) into res_pwd from teacher_credentials where id = userId;
    if res_pwd = passCode then
		return true;
	else
		return false;
        
	end if;
end //

delimiter //
create procedure viewEvaluations(
teacherId int, 
courseId int)
begin
select * from evaluation where teacher_id = teacherId and course_id = courseId;
end //

delimiter //
create procedure addEvaluationForACourse(
teacher_id int, 
course_id int, 
eval_name varchar(200),
eval_type varchar(200)
)
begin
insert into evaluation values (eval_name, eval_type,teacher_id, course_id);
select * from evaluation where teacher_id = teacher_id;
end //


delimiter //
create procedure updateEvaluationForACourse(
teacherId int,
courseId int, 
old_eval_name varchar(200),
evalType varchar(200),
new_eval_name varchar(200)
)
begin
update evaluation set eval_name = new_eval_name where teacher_id = teacherId and course_id = courseId and eval_name = old_eval_name and
eval_type = evalType;
update results set eval_name = new_eval_name where course_id = courseId and eval_name = old_eval_name and
eval_type = evalType;
end //

delimiter //
create procedure studentsInACourse(
courseId int
)
begin 
select * from Student 
where Student.id in (select student_id from Student_Course where course_id = courseId);
end //

delimiter //
create procedure viewResults(
studentId int, 
courseId int)
begin
select * from results where student_id = studentId and course_id = courseId;
end //

delimiter //
create procedure addResult(
Score int,
evalName varchar(200),
evalType varchar(200),
courseId int,
studentId int
)
begin
insert into results values(Score, evalName, evalType, courseId, studentId);
end //

delimiter //
create procedure updateResult(
newScore int,
evalName varchar(200),
evalType varchar(200),
courseId int,
studentId int
)
begin
update results set score = newScore where eval_name = evalName and eval_type = evalType and course_id = courseId and student_id = studentId;
end //

delimiter //
create procedure deleteEvaluationForACourse(
teacherId int,
courseId int, 
evalName varchar(200),
evalType varchar(200)
)
begin 
delete from evaluation where teacher_id = teacherId and course_id = courseId and eval_name = evalName and
eval_type = evalType;
delete from results where course_id = courseId and eval_name = evalName and
eval_type = evalType;
end //

delimiter //
create trigger validateResultInsertion 
before insert on results 
for each row
begin
declare exists_flag int;
select count(*) into exists_flag from evaluation where course_id = new.course_id and eval_type = new.eval_type 
and eval_name = new.eval_name;
if(exists_flag = 0) then 
signal sqlstate '45000'
    set message_text = 'The entered Evaluation Name or/and Evaluation Type is/are not set for this course!'; 
end if;
end //

delimiter //
create procedure deleteResult(
evalType varchar(200),
evalName varchar(200),
courseId int,
studentId int
)
begin
delete from results where eval_type = evalType and eval_name = evalName and
course_id = courseId and student_id = studentId;
end //
