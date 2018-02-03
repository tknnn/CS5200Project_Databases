/* Register a new user*/

/*Use Parametrized Input- Just an example.
@FIRST_NAME = "nate", @LAST_NAME= "derbinsky", @EMAIL= "nate.derbinsky@gmail.com",
@STREET= "123 Huntington Avenue", @CITY= "Boston",@POSTAL_CODE= 02115, @COUNTRY= "USA";
All entries will be parametrized.
*/
INSERT INTO `trainly`.`student`
    (`U_id`, `Firstname`, `Lastname`, `Email`, `Saltnum`, `Hash`, `Street`, `City`, `Postalcode`, `Country`,`Profilepic`)
    VALUES
    (NULL,
    "nate",
    "derbinsky",
    "nate.derbinsky@gmail.com",
     17,
    "9CEFDF51D9407750C8E5564C64E9286B5F12B4CEFF1A9EC063AC87E2D9D4B664",
    "123 Huntington Avenue",
    "Boston",
    "02115",
    "USA",
    NULL);






/*
b) As an administrator, authenticate a faculty user (based upon title/affiliation/website/email) or fellow
administrator.
faculty id 12 is authenticated by admin with id 10. All other data will be parametrized OR already there when we enter
a student/faculty from front end which again will be done in a parametrized way.

/*This wont work untill we add 12 as a student first.
THAT IS WHY I HAVE ADDED A STUDENT WITH UID 12 ABOVE. IF YOU HAVE NOT, PLEASE UNCOMMENT THIS FIRST

INSERT INTO `trainly`.`student`
    (`U_id`, `Firstname`, `Lastname`, `Email`, `Saltnum`, `Hash`, `Street`, `City`, `Postalcode`, `Country`,`Profilepic`)
    VALUES
    (12,
    "nate",
    "derbinsky",
    "nate.derbinsky@gmail.com",
     17,
     "9CEFDF51D9407750C8E5564C64E9286B5F12B4CEFF1A9EC063AC87E2D9D4B664",
    "123 Huntington Avenue",
    "Boston",
    "02115",
    "USA",
    NULL);*/

/*Then we add the student as a faculty*/
INSERT INTO `trainly`.`faculty` (`U_id`) VALUES (12);


INSERT
INTO
    `trainly`.`validates`(
        faculty_id,
        admin_id,
        title,
        affiliation,
        work_website,
        date_time_verify
    )
VALUES
(12, 10, "Professor", "NEU", "https://course.ccs.neu.edu/cs5200f17/", "2017-08-30 13:52:15");









/*
c) Provide a categorized list of a student’s courses (each with primary/secondary topics, ranked by
average evaluation score): currently enrolled, completed, of interest

For course material, use these as well-

`course_material`.`name` AS "COURSE MATERIAL NAME",
`student_completes_course_material`.`Completion_date_time_each_material` AS "COMPLETION DATE OF MATERIAL"

INNER JOIN `course_material` ON  `student_completes_course_material`.`course_material_id`=`course_material`.`course_material_id`
INNER JOIN `student_completes_course_material` ON `enrolls`.`S_id`=`student_completes_course_material`.`student_id`

Student_id and Course_id will be parametrized here
*/

SELECT
`enrolls`.`Enroll_date` AS "COURSE ENROLL DATE",
`enrolls`.`Completion_date` AS "COURSE COMPLETION DATE",
`course`.`name` AS "COURSE NAME",
`topic`.`name_of_topic` AS "PRIMARY TOPIC NAME",
`secondary_topic`.`name_topic` AS "SECONDARY TOPICS",
`enrolls`.`Ratings` AS "Ratings",
`enrolls`.`Is_Interested` AS "IS STUDENT INTERESTED"
FROM `enrolls`
INNER JOIN `course` ON `enrolls`.`C_id`=`course`.`C_id`
INNER JOIN `topic` ON `course`.`primary_topic_id`=`topic`.`T_id`
INNER JOIN `secondary_topic` ON `course`.`C_id`=`secondary_topic`.`C_id`
WHERE `enrolls`.`S_id`=1 and `course`.`C_id`= ANY (SELECT `enrolls`.`C_id` FROM `enrolls` WHERE `enrolls`.`S_id`=1)
ORDER by `enrolls`.`Ratings` DESC;








/*
 * d) Enroll a student in a course
 */

/*
Request goes to server with order details and a payment code is returned say 1100.
There is no completion date. A default rating of 0 is set as the student is not in a condition to give ratings at enrollment date.
We get the student id of current user. Then-
We get all the details from either a student who is already in student table. If we are registering a new student,
we get all the data for adding details in student table using parametrized input.
Otheriwse we just need to know the course s/he wants to enroll (say course with course id 4). Student chooses a drop down of names from front end.
We get the course id on backend in database. We use the current date as enroll date.
*/

INSERT INTO `trainly`.`ENROLLS` (`S_id`, `C_id`, `Is_Interested`, `Enroll_date`, `Completion_date`, `Payment_code`, `Ratings`, `Comment` )
VALUES (11, 4, 1, "2017-11-27", NULL, 1100, NULL, NULL);


/*
Now student is enrolled in course.
This is done in background for future tracking of each course material and user does not need to know about this.
Get all course material for that course_id=4
SELECT * FROM `course_material` WHERE course_id=4;
*/
INSERT INTO `student_completes_course_material` (`student_id`, `c_id`, `course_material_id`, `completed`, `Completion_date_time_each_material`)
VALUES
(11,4,1,0,NULL),
(11,4,2,0,NULL),
(11,4,3,0,NULL),
(11,4,4,0,NULL);





/*
e) For a student enrolled in a course, list materials, in order, indicating the line of demarcation between
completed/not completed.
The student id will be used from the drop down on the front end comprising of names.
The database will get the student id. If we are using a text box for student name's entry on front end, we have to
use parametrized input.


------------------>If there is a completion date => Course is completed<---------------------------------------

We can get all the students and all the courses in which s/he enrolled and completed/not completed from the enrolls table
SELECT * from `trainly`.`enrolls` where Completion_date is not null;
*/

/*This gives back the course NOT completed and all the INCOMPLETE course material of that course for student 1*/
SELECT * from student_completes_course_material where student_id = 1 and Completion_date_time_each_material is null
order by `student_completes_course_material`.`c_id` ASC;


/*
This gives back the course completed and all the course material of that course for student 1
WE JUST CHANGED THE LAST "IS NUL"L TO "IS NOT NULL"
*/
SELECT * from student_completes_course_material where student_id = 1 and Completion_date_time_each_material is not null
order by `student_completes_course_material`.`c_id` ASC;








/*
f) Mark course material as having been completed by a student (possibly resulting in course completion)
 Since this requires no input from user, we do not need any parametrization here. All process will be internally done when a course material
is completed.


---------------------------> Possibly Resulting in course completion<--------------------------------------------
WE GET THE COUNT FROM course material table. If Course 2 has 7 course material and we try to update the 7th course material,
then course 2 is completed. In such a case, we also update the enrolls table with the completion date as completion date of the course as well

IF
 cmid = SELECT COUNT(*) FROM `course_material` WHERE course_id=2;

 ---> We check this on the fron end. Then we update the enrolls table as well.

THEN

(UPDATE `student_completes_course_material`
SET `completed`=1,`Completion_date_time_each_material`="2017-11-25"
WHERE student_id=1 and c_id=2 and course_material_id=2;)

AND

(UPDATE `enrolls`
SET `Completion_date`="2017-11-25"
WHERE student_id=1 and c_id=2;)
;

--------------------------->  Course NOT completed. Just Course Material is completed<--------------------------------------------
/*If this is not the case (cmid <> count), then just update the course material and not the enrolls table. This will fall in the ELSE CLAUSE*/

/*
ELSE
*/

UPDATE `student_completes_course_material`
SET `completed`=1,`Completion_date_time_each_material`="2017-11-25"
WHERE student_id=1 and c_id=2 and course_material_id=2;

/*
END IF;
*/




/*
g) Provide a certificate of completion for a student (assuming s/he has successfully completed all materials)

-------------------------------> ASSUMING ALL MATERIALS ARE COMPLETED <--------------------------------------
Hence we do not need to check if enrolls table has completion date of null or not. We simply read the values.
Assuming we are providing a certificate for student with id 1 and course with id 1 - the end user will not be exposed to any input entry.
We will be doing an internal process when  course is complete. The data will be read from the table and printed to certificate.

*/
SELECT
CONCAT(`student`.`Firstname`," ",`student`.`Lastname`) AS "CERTIFICATE AWARDED TO",
`course`.`name` AS "COURSE NAME",
`course_material`.`name` AS "COURSE MATERIAL INCLUDED IN THIS COURSE",
`enrolls`.`Completion_date` AS "COMPLETION DATE"
FROM `enrolls`
INNER JOIN `course` ON `enrolls`.`C_id`= `course`.`C_id`
INNER JOIN `course_material` ON `course`.`C_id`=`course_material`.`course_id`
INNER JOIN `student` ON `enrolls`.`S_id`=`student`.`U_id`
WHERE `enrolls`.`S_id`=1 and `enrolls`.`C_id`=1;




/*
h) Provide an account history for a user: dates of enrollment/completion for each course, amount paid
(with confirmation code), and total spent.

The student name will be read from session. We get back an ID. This id will be used.
*/

SELECT
CONCAT(`student`.`Firstname`, " ",`student`.`Lastname`) AS "HISTORY FOR USER",
`course`.`name` AS "COURSE ENROLLED",
`Enroll_date` AS "ENROLL DATE",
`Completion_date` AS "COURSE COMPLETED ON",
`Payment_code` as "PAYMENT CODE",
`Course`.`cost` AS "COURSE COST",
(select SUM(`course`.`cost`) from `course`
INNER JOIN `enrolls` ON `enrolls`.`C_id`=`course`.`C_id`
where `enrolls`.`S_id`=1) AS "TOTAL MONEY SPENT OVERALL"
from `enrolls`
INNER JOIN `trainly`.`course` ON `course`.`C_id`=`enrolls`.`C_id`
INNER JOIN `trainly`.`student` ON `student`.`U_id`=S_id
where s_id=1;