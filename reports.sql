/*SQL that implements each of the 5 complex reports (provide
comments describing the purpose, as well as justification of the complexity based upon the scoring
algorithm)*/

/*ASSUMING DATABASE TRAINLY IS SELECTED*/

/*QUERY 1
Track the progress of student
Student with student id 1 is tracked

Divide the total number of course materials completed/ total number of course materials enrolled in *100
Gives back the percentage of progress made by student ordered by the last course material completion date.

Tables joined >3 => 1 point
3 Subqueries  => 2 points (2 subqueries -> 1 point)
grouping yes => 1 point
Aggregate function(s)? COUNT => 1 POINT
ordering fields YES => 1 POINT
STRONG MOTIVATION/JUSTIFICATION => 1 POINT

TOTAL - 7 POINTS
*/
SELECT
    `student`.`Firstname`,
    `student_completes_course_material`.`Completion_date_time_each_material`,
    (SELECT COUNT(`student_completes_course_material`.`course_material_id`)
    FROM `student_completes_course_material`
    WHERE `student_completes_course_material`.`Completion_date_time_each_material` IS NOT NULL
    AND `student_completes_course_material`.`student_id` = 1
) / (
    (SELECT COUNT(`course_material`.`course_material_id`)
FROM `student`
INNER JOIN `enrolls` ON `student`.`U_id` = `enrolls`.`S_id`
INNER JOIN `course` ON `enrolls`.`C_id` = `course`.`C_id`
INNER JOIN `course_material` ON `course`.`C_id` = `course_material`.`course_id`
WHERE
    `student`.`U_id` = 1
))*100 AS "total number of course materials completed / total number of materials enrolled",

(SELECT COUNT(`course_material`.`course_material_id`)
FROM `student`
INNER JOIN `enrolls` ON `student`.`U_id` = `enrolls`.`S_id`
INNER JOIN `course` ON `enrolls`.`C_id` = `course`.`C_id`
INNER JOIN `course_material` ON `course`.`C_id` = `course_material`.`course_id`
WHERE
    `student`.`U_id` = 1
) AS "Total Materials Enrolled"
FROM
    `student`
INNER JOIN `enrolls` ON `enrolls`.`S_id` = `student`.`U_id`
INNER JOIN `course` ON `course`.`C_id` = `enrolls`.`C_id`
INNER JOIN `course_material` ON `course`.`C_id` = `course_material`.`course_material_id`
INNER JOIN `student_completes_course_material` ON `student_completes_course_material`.`student_id` = `course_material`.`course_material_id`
WHERE
    `student`.`U_id` = 1 AND `student_completes_course_material`.`student_id` = 1 AND
    `student_completes_course_material`.`Completion_date_time_each_material` IS NOT NULL
GROUP BY
    `student_completes_course_material`.`Completion_date_time_each_material`
ORDER BY `student_completes_course_material`.`Completion_date_time_each_material` DESC;



/*QUERY 2*/
 /* Get the first name, last name, email, number of times a user has granted admin access to other user as totalGranted, phone type, phone number of student from specific country and also check if that student is a faculty or not where the student has granted admin access to at least one other user.*/

 /*
 # Tables joined (1–2:0 points, ≥3:1 point) - student, admin, faculty ,contact_number  <1POINT>
  Non-inner/natural join? (no:0 points, yes:1 point) <1 POINT>
  Aggregate function(s)? (no:0 points, yes:1 point) - using count() <1 POINT>
  2 ordering fields (≤1:0 points, >1:1 point) <1 point>
  Grouping? (no:0 points, yes:1 point) <1 POINT>
  # WHERE/HAVING conditions not for joins (≤1:0 points, >1:1 point) <1 POINT>
 Strong motivation/justification for the query in the domain? (no:0 points, yes:1 point) <1 POINT>

 JUSTIFICATION: At any point an Admin user may want to know the details of all the students who have granted admin access to other users from specific country to generate report.
 */

 /*This query is parameterized query, which works from the user input. Where user can insert the country name from which h/she wants to pull out this data of students. So in the below query 'USA' will be replaced by name that is inserted by user.*/

 SELECT
    `student`.`U_id`, `student`.`Firstname`,`student`.`Lastname`,`student`.`Email`, COUNT(*) as totalGranted,
    `contact_number`.`Phone_type`,`contact_number`.`Phone_number`,ISNULL(`faculty`.`U_id`) AS isNotFaculty
 FROM
     `student`
 LEFT OUTER JOIN `admin` ON `student`.`U_id` = `admin`.`Grantor_id`
 INNER JOIN `contact_number` ON `contact_number`.`U_id` = `student`.`U_id`
 INNER JOIN `faculty` ON `faculty`.`U_id`=`student`.`U_id`
 where `student`.`Country` = 'USA'
 GROUP BY `admin`.`Grantor_id`
  HAVING totalGranted>=1
  ORDER BY `student`.`U_id`, `student`.`Lastname` DESC;






/*
QUERY 3*
Get the course name and the number of quiz questions as well as the numbers of course materials posts
, then group by course name and order by course name and QUIZ QUESTION NUMBERS(DESC).

3 Tables joined (1–2:0 points, ≥3:1 point) - course , course material , quiz, quiz question <1 POINT>
4 Aggregate function(s)? (no:0 points, yes:1 point) <1 POINT>
1 Grouping (no:0 points, yes:1 point) <1 POINT>
2 ordering fields (≤1:0 points, >1:1 point) <1 POINT>
3 of subqueries (0:0 points, 1:1 point, >2:2 points) < 2 POINTS>
3 WHERE/HAVING conditions not for joins  <1 POINT>
 TOTAL - 7 POINTS => COMPLEX
 
JUSTIFICATION: At any time when an admin user wants to know the richness of a course, he can use the complex
query to get the course name the number of quiz questions as well as the files, links and posts of the course.
Then he can get the material numbers of course by course_material_id and course_id.
In this case, we can get the information of course_material_id=3 and course_id=1.
 */
SELECT `course`.`name` AS "COURSE NAME", 

(SELECT COUNT(`quiz_questions`.`question_number`) FROM `quiz_questions` WHERE `quiz_questions`.`cid_fk` = 1)AS "QUIZ QUESTION NUMBERS",

(SELECT COUNT(`file`.`type`) FROM `file` WHERE `file`.`file_course_id`=1) AS "FILE NUMBERS",

(SELECT COUNT(`link`.`url`) FROM `link` WHERE `link`.`link_course_id`=1) AS "LINK NUMBERS" ,

(SELECT COUNT(`post`.`text`) FROM `post` WHERE `post`.`post_course_id`=1) AS "POST NUMBERS"

FROM `course`
INNER JOIN `course_material` ON `course`.`C_id` = `course_material`.`course_id`
INNER JOIN `quiz` ON `quiz`.`quiz_course_material_id` = `course_material`.`course_material_id`
INNER JOIN `quiz_questions` ON `quiz_questions`.`cmid_fk` = `course_material`.`course_material_id`
WHERE `course`.`C_id`=1
GROUP BY `course`.`name`
ORDER BY `course`.`name` ASC, "QUIZ QUESTION NUMBERS" DESC





/*QUERY 4*/
/*
this query's function is input one Course ID,can get the student ID who enrolled in this course.
The important function is to get all information about this course's quiz. We can get quiz text,
quiz number, quize answer, feedback. Also，We can get the course's average passing score, rate of
questions, and number of questions. Through all these information, manager can have a whole plane 
about whether or not this course interested by student.
Tables joined (1–2:0 points,  3:1 point) <1 POINT>
 1 of subqueries (0:0 points, 1:1 point, >2:2 points) < 2 POINTS>
 2 ordering fields (≤1:0 points, >1:1 point) <1 POINT>
 Grouping? (no:0 points, yes:1 point) <1 POINT>
 Non-aggregation functions or expressions in SELECT/WHERE? < 1 POINTS>
  TOTAL - 6 POINTS => COMPLEX
*/
SELECT DISTINCT
`student_id`,
`course`.`c_id`,
`question_number`,
`feedback` AS `question`,
`answer_number` AS `answer`,
(SELECT COUNT(`question_number`) FROM `quiz_questions` where quiz_questions.cid_fk = 1) AS "Number of questions",
(SELECT AVG(`rating`) FROM `question` where question.question_id = question_number) AS "average of question",
(SELECT AVG(`passing_score`) FROM `quiz` where quiz.quiz_course_id = 1) AS "average passing score",
`course`.`name`
FROM `student_completes_course_material`
INNER JOIN `quiz_questions` ON `student_completes_course_material`.`c_id` = `quiz_questions`.`cid_fk`
INNER JOIN `quiz_question_answers` ON `quiz_questions`.`question_number` = `quiz_question_answers`.`question_num`
INNER JOIN `course` ON `course`.`C_id` = `student_completes_course_material`.`c_id`
WHERE `course`.`C_id` =1
GROUP BY `question_number`
ORDER BY `question_number` DESC, `student_id`



/*
QUERY 5*
Get me the details for a student - all the courses, money spent on each course, total money spent,
number of playlists made and number of questions asked (how active in class)
and order by the money spend and then by name(Higher series courses have more cost in general)
and group by course.

 # Tables joined (1–2:0 points, ≥3:1 point) - student, enrolls, course , course material <1POINT>
 3 of subqueries (0:0 points, 1:1 point, >2:2 points) < 2 POINTS>
 Aggregate function(s)? (no:0 points, yes:1 point) - using sum() <1 POINT>
 2 ordering fields (≤1:0 points, >1:1 point) <1 point> <1 POINT>
 Grouping? (no:0 points, yes:1 point) <1 POINT>
Strong motivation/justification for the query in the domain? (no:0 points, yes:1 point) <1 POINT>

 TOTAL - 7 POINTS => COMPLEX
 */

SELECT
CONCAT( `student`.`Firstname`," ", `student`.`Lastname`) AS "NAME",
`course`.`name` AS "COURSE NAME",
`course`.`cost` AS "COURSE COST",

(select SUM(`course`.`cost`) from `course`
INNER JOIN `enrolls` ON `enrolls`.`C_id`=`course`.`C_id`
where `enrolls`.`S_id`=1) AS "MONEY SPENT OVERALL",

(SELECT COUNT(`student`.`U_id`) from `student`
 INNER JOIN `playlist` ON `student`.`U_id`=`playlist`.`created_by_student`
 WHERE `student`.`U_id`=1) AS "NUMBER OF PLAYLISTS",

(SELECT COUNT(`course_material_question_relation`.`student_id`) from `course_material_question_relation`
 where `course_material_question_relation`.`student_id`=1 ) AS "QUESTIONS ASKED BY STUDENT"


FROM  `student`
INNER JOIN `enrolls` ON `student`.`U_id`=`enrolls`.`S_id`
INNER JOIN `course` ON `enrolls`.`C_id`=`course`.`C_id`
INNER JOIN `course_material` ON `course`.`C_id`=`course_material`.`course_id`
WHERE `student`.`U_id`=1
GROUP by `course`.`name`
ORDER BY `course`.`cost` DESC, `course`.`name` ASC;
