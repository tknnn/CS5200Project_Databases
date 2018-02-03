/**
* DBMS: MySQL
* Students Involved:
* Syed Aman Alam
* Mitresh Pandya
* Yue Cheng
* Bing Qi Liu
*
* Total tables: 27
*/

/*Basic Operations for getting started*/
CREATE DATABASE trainly;
USE trainly;


/**Main table for students as by default user is student*/
CREATE TABLE `trainly`.`Student`(
    `U_id` INT(10) NOT NULL AUTO_INCREMENT ,
    `Firstname` VARCHAR(255),
    `Lastname` VARCHAR(255),
    `Email` VARCHAR(255) NOT NULL,
    `Saltnum` INT(16) NOT NULL,
    `Hash` VARCHAR(40) NOT NULL,
    `Street` VARCHAR(255),
    `City` VARCHAR(255),
    `Postalcode` VARCHAR(10),
    `Country` VARCHAR(255) NOT NULL DEFAULT "USA",
    `Profilepic` LONGBLOB,
    PRIMARY KEY (`U_id`)
);


/*Table for faculty*/
CREATE TABLE `trainly`.`faculty` (
    `U_id` INT(10) NOT NULL,
    PRIMARY KEY (`U_id`),
    CONSTRAINT `fk_faculty` FOREIGN KEY (U_id) REFERENCES student (U_id)
);



/*Table for admin*/
/*CONSTRAINT `fk_grantor` FOREIGN KEY (Grantor_id) REFERENCES admin (U_id)*/
CREATE TABLE `trainly`.`admin` (
    `U_id` INT(10) NOT NULL,
    `Grantor_id` INT(10) NOT NULL,
    `Approval_time` TIMESTAMP NOT NULL,
    PRIMARY KEY (`U_id`),
    CONSTRAINT `fk_admin` FOREIGN KEY (U_id) REFERENCES student (U_id)
);



/*Multivalued attribute contact number*/
CREATE TABLE `trainly`.`contact_number` (
    `U_id` INT(10) NOT NULL,
    `Phone_type` VARCHAR(10),
    `Phone_number` INT(10),
    PRIMARY KEY (`U_id`),
    CONSTRAINT `fk_contact` FOREIGN KEY (U_id) REFERENCES student (U_id)
);


/*Admin validates faculty*/
CREATE TABLE `trainly`.`validates` (
    `faculty_id` INT(10) NOT NULL,
    `admin_id` INT(10) NOT NULL,
    `title` VARCHAR(100),
    `affiliation` VARCHAR(100),
    `work_website` VARCHAR(100),
    `date_time_verify` TIMESTAMP,
    PRIMARY KEY (`faculty_id`,`admin_id`),
    CONSTRAINT `fk_validates_faculty` FOREIGN KEY (faculty_id) REFERENCES faculty (U_id),
    CONSTRAINT `fk_validates_admin` FOREIGN KEY (admin_id) REFERENCES admin (U_id)
);



/*Strong entity course*/
CREATE TABLE `trainly`.`course` (
    `C_id` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `icon` LONGBLOB,
    `name` VARCHAR(100),
    `cost` FLOAT,
    `description` VARCHAR(255),
    `primary_topic_id` INT(10)
);


/*Every course has a topic*/
CREATE TABLE `trainly`.`topic` (
    `T_id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name_of_topic` VARCHAR(255)
);


/*Multivalued attribute secodnary topic*/
CREATE TABLE `trainly`.`secondary_topic` (
    `C_id` INT(10) NOT NULL,
    `T_id` INT(10) NOT NULL,
    `name_topic` VARCHAR(255),
    PRIMARY KEY(T_id,C_id),
    CONSTRAINT `fk_secondary_topic_topic` FOREIGN KEY (T_id) REFERENCES topic (T_id),
    CONSTRAINT `fk_secondary_topic_course` FOREIGN KEY (C_id) REFERENCES  course (C_id)
);


/*Student enrolls in courses
     Default rating is 0 when course is not completed.
    Even if it is completed, default is 0 means the student has not given rating.
    When a student gives rating, it has to be between [1,5]
    */
CREATE TABLE `trainly`.`enrolls` (
    `S_id` INT(10) NOT NULL,
    `C_id` INT(10) NOT NULL,
    `Is_Interested` tinyint(1) NOT NULL DEFAULT '1',
    `Enroll_date` DATE NOT NULL,
    `Completion_date` DATE,
    `Payment_code` INT(10) NOT NULL,
    `Ratings` decimal(2,1) DEFAULT '0',
    `Comment` VARCHAR(100),
    PRIMARY KEY(`S_id`,`C_id`),
    CONSTRAINT `fk_enrolls_sid` FOREIGN KEY (S_id) REFERENCES student (U_id),
    CONSTRAINT `fk_enrolls_cid` FOREIGN KEY (C_id) REFERENCES course (C_id),
    CONSTRAINT `rating` CHECK (Ratings>=1 AND Ratings <=5)
);


/*Faculty creates courses relation MN*/
CREATE TABLE `trainly`.`f_creates_c` (
    `F_id` INT(10) NOT NULL,
    `C_id` INT(10) NOT NULL,
    `date_of_creation` DATE NOT NULL,
    PRIMARY KEY(`C_id`,`F_id`),
    CONSTRAINT `fk_faculties_id_fid` FOREIGN KEY (F_id) REFERENCES faculty (U_id),
    CONSTRAINT `fk_faculties_id_cid` FOREIGN KEY (C_id) REFERENCES course (C_id)
);


/*Course has course material*/
CREATE TABLE `trainly`.`course_material`(
  `course_id` INT(10) NOT NULL,
  `course_material_id` INT(10) NOT NULL,
  `name` VARCHAR(100),
  `created_by_faculty` INT(10) NOT NULL,
  `typeid` INT(4),
  PRIMARY KEY(`course_id`, `course_material_id`),
  CONSTRAINT `material_references_course` FOREIGN KEY (course_id) REFERENCES course (C_id),
  CONSTRAINT `material_created_by_faculty` FOREIGN KEY (created_by_faculty) REFERENCES f_creates_c (F_id)
);

/*File of Course Material*/
CREATE TABLE `trainly`.`file`(
    `file_course_id` INT(10) NOT NULL,
    `file_course_material_id` INT(10) NOT NULL,
    `type` VARCHAR(20),
    `size` VARCHAR(20),
    `path` VARCHAR(255),
    `typeid` INT(1),
    CONSTRAINT `file_course_material` FOREIGN KEY (`file_course_id`,`file_course_material_id`)
    REFERENCES course_material (course_id,course_material_id)
);

/*Link of Course Material*/
CREATE TABLE `trainly`.`link`(
    `link_course_id` INT(10) NOT NULL,
    `link_course_material_id` INT(10) NOT NULL,
    `url` VARCHAR(255),
    `video` TINYINT(1),
    `typeid` INT(2),
     PRIMARY KEY (link_course_id,link_course_material_id),
    CONSTRAINT `link_course_material` FOREIGN KEY (link_course_id,link_course_material_id)
     REFERENCES course_material (course_id,course_material_id)
);

/*Post of Course Material*/
CREATE TABLE `trainly`.`post`(
    `post_course_id` INT(10) NOT NULL,
    `post_course_material_id` INT(10) NOT NULL,
    `text` VARCHAR(255),
    `typeid` INT(3),
    PRIMARY KEY (post_course_id,post_course_material_id),
    CONSTRAINT `post_course_material` FOREIGN KEY (post_course_id,post_course_material_id)
     REFERENCES course_material (course_id,course_material_id)
);

/*Quiz of Course Material*/
CREATE TABLE `trainly`.`quiz`(
    `quiz_course_id` INT(10) NOT NULL,
    `quiz_course_material_id` INT(10) NOT NULL,
    `passing_score` INT(5),
    `sequence` INT(5),
    `typeid` INT(4),
    PRIMARY KEY (quiz_course_id,quiz_course_material_id),
    CONSTRAINT `quiz_course_material` FOREIGN KEY (quiz_course_id,quiz_course_material_id) REFERENCES course_material (course_id,course_material_id)
);


/*Student asks question on course material*/
CREATE TABLE `trainly`.`question`(
    `visibility` TINYINT(1) DEFAULT 0,
    `rating` decimal(2,1) DEFAULT 1,
    `answer_to_question` VARCHAR(255),
    `question_id` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CONSTRAINT `rating` CHECK (rating>=1 AND rating <=5)
);

/*
This table is the relation between student and course material.
It keeps track of which course material has been complete.
When a student enrolls in a course, by default s/he enrolls in all course materials.
So all course material by default for that student has completion status 0.
When student starts completing each course material for that course, s/he eventually
starts changing 0s to 1s and when all course material are completed, the course is complete.
This table has PK of `c_id`,`course_material_id`,`student_id`
The `c_id`,`course_material_id` comes from the PK of course material
The `student_id` comes from the student table as this is a relation between student table and course material table
*/
CREATE TABLE `trainly`.`student_completes_course_material`(
    `student_id` INT(10) NOT NULL,
    `c_id` INT(10) NOT NULL,
    `course_material_id` INT(10) NOT NULL,
    `completed` tinyint(1) NOT NULL DEFAULT '0',
    `Completion_date_time_each_material` DATE,
    PRIMARY KEY(`c_id`,`course_material_id`,`student_id`),
    CONSTRAINT `student_completes_material` FOREIGN KEY (student_id) REFERENCES enrolls (S_id),
    CONSTRAINT `material_completed_by_student` FOREIGN KEY (c_id,course_material_id) REFERENCES course_material (course_id,course_material_id)
);

/*Every Quiz has questions*/
CREATE TABLE `trainly`.`quiz_questions`(
    `question_number` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `feedback` VARCHAR(255),
    `text` VARCHAR(255),
    `cid_fk` INT(10) NOT NULL,
    `cmid_fk` INT(10) NOT NULL,
    `sid` INT(10),
    CONSTRAINT `student_answers_question` FOREIGN KEY (sid) REFERENCES enrolls (S_id),
    CONSTRAINT `quiz_must_have_question` FOREIGN KEY (cid_fk, cmid_fk) REFERENCES quiz (quiz_course_id, quiz_course_material_id)
);

/*
Quiz has questions and a multi values attribute ANSWERS.
This table is for the multi valued attribute
Since there can be multiple answers to a question, answer_number and question number together make the PK
*/
CREATE TABLE `trainly`.`quiz_question_answers`(
    `text` VARCHAR(255),
    `correct` TINYINT(1) DEFAULT 0,
    `question_num` INT(10) NOT NULL,
    `answer_number` VARCHAR(255) DEFAULT 1,
    PRIMARY KEY (question_num,answer_number),
    CONSTRAINT `set_of_answers` FOREIGN KEY (question_num) REFERENCES quiz_questions (question_number)
);


/*Course Material has Questions: Relation Between Course_Material and Question*/
CREATE TABLE `trainly`.`course_material_question_relation`(
    `cid_fk` INT(10) NOT NULL,
    `material_id_relation` INT(10) NOT NULL,
    `student_id` INT(10),
    `faculty_id` INT(10),
    `question_id_relation` INT(10) NOT NULL,
    PRIMARY KEY (question_id_relation,material_id_relation),
    CONSTRAINT `question_material_mn` FOREIGN KEY (question_id_relation) REFERENCES question (question_id),
    CONSTRAINT `course_created_by_fac` FOREIGN KEY (faculty_id) REFERENCES f_creates_c (F_id),
    CONSTRAINT `question_asked_by_student` FOREIGN KEY (student_id) REFERENCES enrolls (S_id),
    CONSTRAINT `question_material_n_m` FOREIGN KEY (cid_fk,material_id_relation) REFERENCES course_material (course_id,course_material_id)
);

/*Students create playlist*/
CREATE TABLE `trainly`.`playlist`(
    `name_playlist` VARCHAR(255) NOT NULL PRIMARY KEY,
    `created_by_student` INT(10) NOT NULL,
    CONSTRAINT `playlist_student_fk` FOREIGN KEY (created_by_student) REFERENCES Student (U_id)
);

/**M-N Relation between playlist and Course Material*/
CREATE TABLE `trainly`.`playlist_has_course_materials`(
    `playlist_name_fk` VARCHAR(255) NOT NULL,
    `cid_fk` INT(10) NOT NULL,
    `course_material_id_fk` INT(10) NOT NULL,
    `order_materials` int(10) NOT NULL,
    PRIMARY KEY (cid_fk, course_material_id_fk, playlist_name_fk),
    CONSTRAINT `playlist_cmid_fk` FOREIGN KEY (cid_fk,course_material_id_fk) REFERENCES course_material(course_id, course_material_id),
    CONSTRAINT `playlist_pid_fk` FOREIGN KEY (playlist_name_fk) REFERENCES playlist (name_playlist)
);