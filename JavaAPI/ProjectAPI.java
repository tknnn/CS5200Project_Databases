import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.io.*;
import java.lang.*;
import java.nio.channels.IllegalSelectorException;

public class ProjectAPI {
	public static void main(String[] args) {

		initialize();
	}

	private static void initialize() {
		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Select index of operation: ");
		System.out.println("1. Get all student users");
		System.out.println("2. Get all faculty users");
		System.out.println("3. Get all admin users");
		System.out.println("4. Get all courses");
		System.out.println("5. Get all course materials");
		System.out.println("6. Register a student");
		System.out.println("7. Authenticate a faculty");
		System.out.println("8. List student's courses");
		System.out.println("9. Enroll Student in course");
		System.out.println("10.Student course completion log");
		System.out.println("11.Student completes a course material");
		System.out.println("12.Course completion certificate");
		System.out.println("13. Student account history");
		System.out.println("14. Student progress tracker");
		System.out.println("15. Admin activity log");
		System.out.println("16. Course questions' details with materials ");
		System.out.println("17. Course quiz details");
		System.out.println("18. Student Activities and details");
		System.out.println("19. List Student playlists");
		System.out.println("20. List Course materials from playlists");
		System.out.println("21. Create a playlist");
		System.out.println("22. Remove a playlist");
		System.out.println("23. Add course material to playlist");
		System.out.println("24. Remove course material to playlist");
		System.out.println("25. Move course material within playlist");
		System.out.println("26. Exit");
		System.out.println(
				"==================================================================");
		int key = reader.nextInt(); // Scans the next token of the input as an
									// int.
		// once finished
		// reader.close();

		switch (key) {
		case 1:
			getAllStudents();
			break;
		case 2:
			getAllFaculties();
			break;
		case 3:
			getAllAdmins();
			break;
		case 4:
			getAllCourses();
			break;
		case 5:
			getAllMaterials();
			break;
		case 6:
			registerUser();
			break;
		case 7:
			authenticateFaculty();
			break;
		case 8:
			getStudentCourses();
			break;
		case 9:
			enrollStudent();
			break;
		case 10:
			getCourseMaterials();
			break;
		case 11:
			studentCompletesMaterial();
			break;
		case 12:
			getCertificate();
			break;
		case 13:
			getAccountHistory();
			break;
		case 14:
			getStudentProgress();
			break;
		case 15:
			adminActivityLog();
			break;
		case 16:
			courseDetailed();
			break;
		case 17:
			getCourseQuiz();
			break;
		case 18:
			getStudentDetails();
			break;
		case 19:
			getPlaylists();
			break;
		case 20:
			getPlaylistMaterials();
			break;
		case 21:
			createPlaylist();
			break;
		case 22:
			removePlaylist();
			break;
		case 23:
			addCm();
			break;
		case 24:
			removeCm();
			break;
		case 25:
			moveCm();
			break;
		case 26:
			System.out.println("Bye!!");
			System.exit(0);
			break;

		default:
			System.out.println("Invalid choice! Please try again");
			initialize();
			break;
		}
	}

	private static void getAllStudents() {
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			final String sql;
			sql = "SELECT U_id as id," + " Firstname as fname"
					+ ",Lastname as lname" + ",Email as email"
					+ ",Street as street" + ",City as city"
					+ ",Postalcode as postalCode" + ",Country as country"
					+ " FROM student WHERE 1";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			// get results
			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("id:" + res.getString("id")
							+ "\tFirstname: " + res.getString("fname")
							+ "|\tLastname: " + res.getString("lname")
							+ "|\tEmail: " + res.getString("email")
							+ "|\tStreet: " + res.getString("street")
							+ "|\tCity: " + res.getString("city")
							+ "|\tPostalCode: " + res.getString("postalCode")
							+ "|\tCountry: " + res.getString("country")
							+ "\n=====================");
				}
			} catch (SQLException e) {
				connection.rollback();
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		System.out.println("\n\nSelect another operation");
		System.out.println(
				"=====================================================================================================");
		initialize();
	}

	private static void getAllFaculties() {
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			final String sql;
			sql = "SELECT U_id as id," + " Firstname as fname"
					+ ",Lastname as lname" + ",Email as email"
					+ ",Street as street" + ",City as city"
					+ ",Postalcode as postalCode" + ",Country as country"
					+ " FROM student NATURAL JOIN faculty";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			// get results
			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("id:" + res.getString("id")
							+ "\nFirstname:" + res.getString("fname")
							+ "\nLastname: " + res.getString("lname")
							+ "\nEmail: " + res.getString("email")
							+ "\nStreet: " + res.getString("street")
							+ "\nCity: " + res.getString("city")
							+ "\nPostalCode: " + res.getString("postalCode")
							+ "\nCountry: " + res.getString("country")
							+ "\n=====================");
				}
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Could not finish the query!",
						e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		System.out.println("Select another operation");
		System.out.println("=======================================");
		initialize();
	}

	private static void getAllAdmins() {
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			final String sql;
			sql = "SELECT s.U_id as id," + " s.Firstname as fname"
					+ ",s.Lastname as lname" + ",s.Email as email"
					+ ",s.Street as street" + ",s.City as city"
					+ ",s.Postalcode as postalCode" + ",s.Country as country"
					+ ",a.Grantor_id as grantor"
					+ ",a.Approval_time as approvedTime"
					+ " FROM student s NATURAL JOIN admin a";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			// get results
			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("id:" + res.getString("id")
							+ "\nFirstname:" + res.getString("fname")
							+ "\nLastname: " + res.getString("lname")
							+ "\nEmail: " + res.getString("email")
							+ "\nStreet: " + res.getString("street")
							+ "\nCity: " + res.getString("city")
							+ "\nPostalCode: " + res.getString("postalCode")
							+ "\nCountry: " + res.getString("country")
							+ "\nGrantor Id: " + res.getString("grantor")
							+ "\nApproved Time: "
							+ res.getString("approvedTime")
							+ "\n=====================");
				}
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException(
						"Can not finish query execution!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		System.out.println("Select another operation");
		System.out.println("=======================================");
		initialize();
	}

	private static void getAllCourses() {
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			final String sql;
			sql = "SELECT C_id as id," + " name as cname" + ",cost as fees"
					+ ",description as details" + " FROM course WHERE 1";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			// get results
			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("id:" + res.getString("id")
							+ "\nCourse name:" + res.getString("cname")
							+ "\nCourse fees: " + res.getString("fees")
							+ "\nCourse description: "
							+ res.getString("details")
							+ "\n=====================");
				}
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException(
						"Can not finish query execution.", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		System.out.println("Select another operation");
		System.out.println("=======================================");
		initialize();
	}

	private static void getAllMaterials() {
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Course id");
			int cId = r1.nextInt();

			final String sql;
			sql = "SELECT course_id as cid, course_material_id as cmid,"
					+ "name as cmname "
					+ "FROM course_material WHERE course_id=?";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cId);
			// get results
			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Course Id:" + res.getString("cid")
							+ "\nCourse Material Id :" + res.getString("cmid")
							+ "\nMaterial Name: " + res.getString("cmname")
							+ "\n=====================");
				}
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Can't finish query execution!",
						e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		System.out.println("Select another operation");
		System.out.println("=======================================");
		initialize();
	}

	private static void registerUser() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Firstname");
			String fname = r1.nextLine();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Lastname");
			String lname = r2.nextLine();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter Email");
			String email = r3.nextLine();

			Scanner r4 = new Scanner(System.in);
			System.out.println("Enter password");
			String pwd = r4.nextLine();

			Scanner r5 = new Scanner(System.in);
			System.out.println("Enter street");
			String street = r5.nextLine();

			Scanner r6 = new Scanner(System.in);
			System.out.println("Enter city");
			String city = r6.nextLine();

			Scanner r7 = new Scanner(System.in);
			System.out.println("Enter postal code");
			String postalCode = r7.nextLine();

			Scanner r8 = new Scanner(System.in);
			System.out.println("Enter country");
			String country = r8.nextLine();

			Random rand = new Random();
			int salt = rand.nextInt(99) + 1;
			String s = Integer.toString(salt);
			String newPwd = s + pwd;
			String sha1 = "";
			try {
				MessageDigest crypt = MessageDigest.getInstance("SHA-1");
				crypt.reset();
				crypt.update(newPwd.getBytes("UTF-8"));
				Formatter formatter = new Formatter();
				for (byte b : crypt.digest()) {
					formatter.format("%02x", b);
				}
				sha1 = formatter.toString();
				formatter.close();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			final String sql;
			sql = "INSERT INTO `trainly`.`student`"
					+ "(`Firstname`, `Lastname`, `Email`, `Saltnum`, `Hash`,"
					+ " `Street`, `City`, `Postalcode`, `Country`,`Profilepic`)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,NULL)";

			// prepare statement
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, email);
			stmt.setInt(4, salt);
			stmt.setString(5, sha1);
			stmt.setString(6, street);
			stmt.setString(7, city);
			stmt.setString(8, postalCode);
			stmt.setString(9, country);
			try {
				stmt.executeUpdate();
				connection.commit();
				System.out.println("Insert successful.");
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}

		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void authenticateFaculty() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Admin id");
			int aId = r1.nextInt();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Student id who is a faculty");
			int sId = r2.nextInt();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter faculty title");
			String title = r3.nextLine();

			Scanner r4 = new Scanner(System.in);
			System.out.println("Enter faculty affiliation");
			String aff = r4.nextLine();

			Scanner r5 = new Scanner(System.in);
			System.out.println("Enter faculty website");
			String web = r5.nextLine();

			String time = Instant.now().toString().replace("T", " ")
					.replace("Z", " ");

			final String sql1, sql2;
			sql1 = "INSERT INTO faculty (U_id) VALUES (?);";
			sql2 = "INSERT INTO `trainly`.`validates`("
					+ "faculty_id,admin_id,title,affiliation,work_website,"
					+ "date_time_verify) VALUES" + "(?,?,?,?,?,?);";

			// prepare statement
			final PreparedStatement stmt = connection.prepareStatement(sql1);
			stmt.setInt(1, sId);

			final PreparedStatement stmt2 = connection.prepareStatement(sql2);
			stmt2.setInt(1, sId);
			stmt2.setInt(2, aId);
			stmt2.setString(3, title);
			stmt2.setString(4, aff);
			stmt2.setString(5, web);
			stmt2.setString(6, time);

			try {
				stmt.executeUpdate();
				stmt2.executeUpdate();
				connection.commit();
				System.out.println("Insert successful.");
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getStudentCourses() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT `enrolls`.`Enroll_date` AS 'COURSE ENROLL DATE',"
					+ "`enrolls`.`Completion_date` AS 'COURSE COMPLETION DATE',"
					+ "`course`.`name` AS 'COURSE NAME',"
					+ "`topic`.`name_of_topic` AS 'PRIMARY TOPIC NAME',"
					+ "`secondary_topic`.`name_topic` AS 'SECONDARY TOPICS',"
					+ "`enrolls`.`Ratings` AS 'Ratings',"
					+ "`enrolls`.`Is_Interested` AS 'IS STUDENT INTERESTED'"
					+ "FROM `enrolls`" + "INNER JOIN `course` "
					+ "ON `enrolls`.`C_id`=`course`.`C_id`"
					+ "INNER JOIN `topic` "
					+ "ON `course`.`primary_topic_id`=`topic`.`T_id`"
					+ "INNER JOIN `secondary_topic` "
					+ "ON `course`.`C_id`=`secondary_topic`.`C_id`"
					+ "WHERE `enrolls`.`S_id`=? and `course`.`C_id`= "
					+ "ANY (SELECT `enrolls`.`C_id` FROM `enrolls` WHERE"
					+ " `enrolls`.`S_id`=?)"
					+ "ORDER by `enrolls`.`Ratings` DESC;";

			// prepare statement
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);
			stmt.setInt(2, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Course enroll date:"
							+ res.getString("COURSE ENROLL DATE")
							+ "\nCourse completion date:"
							+ res.getString("COURSE COMPLETION DATE")
							+ "\nCourse name:" + res.getString("COURSE NAME")
							+ "\nPrimary Topic name:"
							+ res.getString("PRIMARY TOPIC NAME")
							+ "\nSecondary Topics:"
							+ res.getString("SECONDARY TOPICS") + "\nRatings:"
							+ res.getString("Ratings")
							+ "\nIs Student interested:"
							+ res.getString("IS STUDENT INTERESTED")
							+ "\n======================");
				}

				System.out.println("Select another operation");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}

		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void enrollStudent() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Course id");
			int cId = r2.nextInt();
			Random rand = new Random();
			int paymentCode = rand.nextInt(9999) + 1;
			String date = new SimpleDateFormat("yyyy-MM-dd")
					.format(Calendar.getInstance().getTime());

			final String sql1, sql2, sql3;
			sql1 = "INSERT INTO `trainly`.`ENROLLS` "
					+ "(`S_id`, `C_id`, `Is_Interested`, `Enroll_date`,"
					+ " `Completion_date`, `Payment_code`, `Ratings`, `Comment`)"
					+ "VALUES (?,?,1,?, NULL,?, NULL, NULL);";
			sql2 = "SELECT COUNT(*) as cnt FROM `course_material` WHERE course_id=?;";
			sql3 = "INSERT INTO `student_completes_course_material` "
					+ "(`student_id`, `c_id`, `course_material_id`,"
					+ " `completed`, `Completion_date_time_each_material`)"
					+ "VALUES (?,?,?,0,NULL);";

			// prepare statement
			final PreparedStatement stmt = connection.prepareStatement(sql1);
			stmt.setInt(1, sId);
			stmt.setInt(2, cId);
			stmt.setString(3, date);
			stmt.setInt(4, paymentCode);

			int cmCnt = 0;
			final PreparedStatement stmt2 = connection.prepareStatement(sql2);
			stmt2.setInt(1, cId);
			final ResultSet res = stmt2.executeQuery();
			while (res.next()) {
				cmCnt = res.getInt("cnt");
			}

			try {
				stmt.executeUpdate();
				for (int i = 1; i <= cmCnt; i++) {
					final PreparedStatement stmt3 = connection
							.prepareStatement(sql3);
					stmt3.setInt(1, sId);
					stmt3.setInt(2, cId);
					stmt3.setInt(3, i);
					stmt3.executeUpdate();
					connection.commit();
				}
				System.out.println("Insert successful.");
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}

		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getCourseMaterials() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT * from student_completes_course_material where "
					+ "student_id = ? and Completion_date_time_each_material "
					+ "IS NULL " + "UNION "
					+ "SELECT * from student_completes_course_material where "
					+ "student_id = ? and Completion_date_time_each_material"
					+ " IS NOT NULL ORDER BY `c_id` ASC";

			// prepare statement
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);
			stmt.setInt(2, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Student id:"
							+ res.getString("student_id") + "\nCourseId:"
							+ res.getString("c_id") + "\nCourse Material Id:"
							+ res.getString("course_material_id")
							+ "\nCourse material completed?:"
							+ (res.getString("completed").equals("1") ? "YES"
									: "NO")
							+ "\nCourse Material Completion on: "
							+ (res.getString(
									"Completion_date_time_each_material") == null
											? "N/A"
											: res.getString(
													"Completion_date_time_each_material"))
							+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}

		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void studentCompletesMaterial() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Course id");
			int cId = r2.nextInt();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter material id");
			int cmId = r3.nextInt();

			String date = new SimpleDateFormat("yyyy-MM-dd")
					.format(Calendar.getInstance().getTime());

			final String sql;
			sql = "SELECT COUNT(*) as cmcnt FROM `course_material` WHERE course_id=?";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cId);
			int cmCnt = 0;
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				cmCnt = res.getInt("cmcnt");
			}

			if (cmId == cmCnt) {
				// Course is completed
				final String sql1, sql2;
				sql1 = "UPDATE `student_completes_course_material`"
						+ " SET `completed`=1,"
						+ "`Completion_date_time_each_material`=?"
						+ " WHERE student_id=? and c_id=? and course_material_id=?";
				final PreparedStatement stmt1 = connection
						.prepareStatement(sql1);
				stmt1.setString(1, date);
				stmt1.setInt(2, sId);
				stmt1.setInt(3, cId);
				stmt1.setInt(4, cmId);

				sql2 = "UPDATE `enrolls` " + "SET `Completion_date`=?"
						+ " WHERE S_id=? and C_id=?";
				final PreparedStatement stmt2 = connection
						.prepareStatement(sql2);
				stmt2.setString(1, date);
				stmt2.setInt(2, sId);
				stmt2.setInt(3, cId);

				try {
					stmt1.executeUpdate();
					stmt2.executeUpdate();
					connection.commit();
					System.out.println("Select another operation");
					System.out
							.println("=======================================");
					initialize();
				} catch (SQLException e) {
					connection.rollback();
					throw new IllegalStateException("Issue while query!", e);
				}
			} else {
				final String sql3;
				sql3 = "UPDATE `student_completes_course_material`"
						+ " SET `completed`=1,`Completion_date_time_each_material`=?"
						+ " WHERE student_id=? and c_id=? and course_material_id=?";
				final PreparedStatement stmt3 = connection
						.prepareStatement(sql3);
				stmt3.setString(1, date);
				stmt3.setInt(2, sId);
				stmt3.setInt(3, cId);
				stmt3.setInt(4, cmId);
				try {
					stmt3.executeUpdate();
					connection.commit();
					System.out.println("Select another operation");
					System.out
							.println("=======================================");
					initialize();
				} catch (SQLException e) {
					connection.rollback();
					throw new IllegalStateException("Issue while query!", e);
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getCertificate() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT CONCAT(`student`.`Firstname`,' ',`student`.`Lastname`)"
					+ " AS 'CERTIFICATE AWARDED TO',`course`.`name` AS 'COURSE NAME',"
					+ "`course_material`.`name` AS 'COURSE MATERIAL INCLUDED IN THIS COURSE',"
					+ "`enrolls`.`Completion_date` AS 'COMPLETION DATE' FROM `enrolls`"
					+ "INNER JOIN `course` ON `enrolls`.`C_id`= `course`.`C_id`"
					+ "INNER JOIN `course_material` ON "
					+ "`course`.`C_id`=`course_material`.`course_id`"
					+ "INNER JOIN `student` ON `enrolls`.`S_id`=`student`.`U_id`"
					+ " WHERE `enrolls`.`S_id`=? and `enrolls`.`Completion_date` IS NOT NULL";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Certificate awarded to:"
							+ res.getString("CERTIFICATE AWARDED TO")
							+ "\nCourse Name:" + res.getString("COURSE NAME")
							+ "\nCourse Material included :"
							+ res.getString(
									"COURSE MATERIAL INCLUDED IN THIS COURSE")
							+ "\nCompletion date:"
							+ res.getString("COMPLETION DATE")
							+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getAccountHistory() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT CONCAT(`student`.`Firstname`, ' ',`student`.`Lastname`)"
					+ " AS 'HISTORY FOR USER',`course`.`name` AS 'COURSE ENROLLED',"
					+ "`Enroll_date` AS 'ENROLL DATE',"
					+ "`Completion_date` AS 'COURSE COMPLETED ON',"
					+ "`Payment_code` as 'PAYMENT CODE',`Course`.`cost` AS 'COURSE COST',"
					+ "(select SUM(`course`.`cost`) from `course` "
					+ "INNER JOIN `enrolls` ON `enrolls`.`C_id`=`course`.`C_id`"
					+ " where `enrolls`.`S_id`=1) AS 'TOTAL MONEY SPENT OVERALL'"
					+ " from `enrolls` "
					+ "INNER JOIN `trainly`.`course` ON `course`.`C_id`=`enrolls`.`C_id` "
					+ "INNER JOIN `trainly`.`student` ON `student`.`U_id`=S_id"
					+ " where s_id=?";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println(
							"Student name:" + res.getString("HISTORY FOR USER")
									+ "\nCourse Name:"
									+ res.getString("COURSE ENROLLED")
									+ "\nEnrolled on :"
									+ res.getString("ENROLL DATE")
									+ "\nCompletion date:"
									+ (res.getString(
											"COURSE COMPLETED ON") == null
													? "N/A"
													: res.getString(
															"COURSE COMPLETED ON"))
									+ "\nPayment code:"
									+ res.getString("PAYMENT CODE")
									+ "\nCourse cost:"
									+ res.getString("COURSE COST")
									+ "\nTotal money spent:"
									+ res.getString("TOTAL MONEY SPENT OVERALL")
									+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getStudentProgress() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT `student`.`Firstname`,"
					+ "`student_completes_course_material`.`Completion_date_time_each_material`,"
					+ "(SELECT COUNT(`student_completes_course_material`.`course_material_id`)"
					+ "FROM `student_completes_course_material`"
					+ "WHERE `student_completes_course_material`.`Completion_date_time_each_material`"
					+ " IS NOT NULL"
					+ " AND `student_completes_course_material`.`student_id` = ?) "
					+ "/ ( (SELECT COUNT(`course_material`.`course_material_id`)"
					+ " FROM `student` "
					+ "INNER JOIN `enrolls` ON `student`.`U_id` = `enrolls`.`S_id` "
					+ "INNER JOIN `course` ON `enrolls`.`C_id` = `course`.`C_id` "
					+ "INNER JOIN `course_material` ON "
					+ "`course`.`C_id` = `course_material`.`course_id` WHERE "
					+ "`student`.`U_id` = ? "
					+ "))*100 AS 'total number of course materials completed / total number of materials enrolled', "
					+ "(SELECT COUNT(`course_material`.`course_material_id`) FROM `student`"
					+ " INNER JOIN `enrolls` ON `student`.`U_id` = `enrolls`.`S_id` "
					+ "INNER JOIN `course` ON `enrolls`.`C_id` = `course`.`C_id` "
					+ "INNER JOIN `course_material` "
					+ "ON `course`.`C_id` = `course_material`.`course_id` WHERE "
					+ "`student`.`U_id` = ? ) AS 'Total Materials Enrolled' FROM "
					+ "`student` INNER JOIN `enrolls` ON `enrolls`.`S_id` = `student`.`U_id` "
					+ "INNER JOIN `course` ON `course`.`C_id` = `enrolls`.`C_id` "
					+ "INNER JOIN `course_material` ON `course`.`C_id` = `course_material`.`course_material_id` "
					+ "INNER JOIN `student_completes_course_material` "
					+ "ON `student_completes_course_material`.`student_id` "
					+ "= `course_material`.`course_material_id` WHERE "
					+ "`student`.`U_id` = ? AND "
					+ "`student_completes_course_material`.`student_id` = ? AND"
					+ "`student_completes_course_material`.`Completion_date_time_each_material` "
					+ "IS NOT NULL GROUP BY `student_completes_course_material`.`Completion_date_time_each_material` "
					+ "ORDER BY `student_completes_course_material`.`Completion_date_time_each_material` DESC";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);
			stmt.setInt(2, sId);
			stmt.setInt(3, sId);
			stmt.setInt(4, sId);
			stmt.setInt(5, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Student Firstname:"
							+ res.getString("Firstname")
							+ "\nCompletion date of each material:"
							+ res.getString(
									"Completion_date_time_each_material")
							+ "\nTotal number of course materials completed / total number of materials enrolled:"
							+ res.getString("total number of course materials "
									+ "completed / total number of materials enrolled")
							+ "\nTotal Materials Enrolled:"
							+ res.getString("Total Materials Enrolled")
							+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void adminActivityLog() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Country name");
			String country = r1.nextLine();

			final String sql;
			sql = "SELECT `student`.`U_id`, `student`.`Firstname`,"
					+ "`student`.`Lastname`,`student`.`Email`, COUNT(*) as totalGranted,"
					+ "`contact_number`.`Phone_type`,"
					+ "`contact_number`.`Phone_number`,ISNULL(`faculty`.`U_id`) AS isNotFaculty"
					+ " FROM `student` LEFT OUTER JOIN `admin` "
					+ "ON `student`.`U_id` = `admin`.`Grantor_id` "
					+ "INNER JOIN `contact_number` "
					+ "ON `contact_number`.`U_id` = `student`.`U_id` "
					+ "INNER JOIN `faculty` ON `faculty`.`U_id`=`student`.`U_id` "
					+ "where `student`.`Country` = ? "
					+ "GROUP BY `admin`.`Grantor_id` HAVING totalGranted>=1 "
					+ "ORDER BY `student`.`U_id`, `student`.`Lastname` DESC";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, country);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out
							.println("Student Id:" + res.getString("U_id")
									+ "\nFirstname:"
									+ res.getString("Firstname") + "\nLastname:"
									+ res.getString("Email")
									+ "\nTotal admin access granted:"
									+ res.getString("totalGranted")
									+ "\nPhone type:"
									+ res.getString("Phone_type")
									+ "\nPhone number:"
									+ res.getString("Phone_number")
									+ "\nUser is faculty?:"
									+ (res.getString("isNotFaculty").equals("0")
											? "YES" : "NO")
									+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void courseDetailed() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter course id");
			int cId = r1.nextInt();

			final String sql;
			sql = "SELECT `course`.`name` AS 'COURSE NAME', "
					+ "(SELECT COUNT(`quiz_questions`.`question_number`) "
					+ "FROM `quiz_questions` WHERE "
					+ "`quiz_questions`.`cid_fk` = ?)AS 'QUIZ QUESTION NUMBERS',"
					+ "(SELECT COUNT(`file`.`type`) " + "FROM `file` WHERE "
					+ "`file`.`file_course_id`=?) AS 'FILE NUMBERS',"
					+ "(SELECT COUNT(`link`.`url`) " + "FROM `link` WHERE "
					+ "`link`.`link_course_id`=?) AS 'LINK NUMBERS' ,"
					+ "(SELECT COUNT(`post`.`text`) " + "FROM `post` WHERE "
					+ "`post`.`post_course_id`=?) AS 'POST NUMBERS' FROM `course` "
					+ "INNER JOIN `course_material` "
					+ "ON `course`.`C_id` = `course_material`.`course_id` "
					+ "INNER JOIN `quiz` ON `quiz`.`quiz_course_material_id` "
					+ "= `course_material`.`course_material_id` "
					+ "INNER JOIN `quiz_questions` ON "
					+ "`quiz_questions`.`cmid_fk` = `course_material`.`course_material_id`"
					+ " WHERE `course`.`C_id`=? GROUP BY `course`.`name` "
					+ "ORDER BY `course`.`name` ASC, 'QUIZ QUESTION NUMBERS' DESC";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cId);
			stmt.setInt(2, cId);
			stmt.setInt(3, cId);
			stmt.setInt(4, cId);
			stmt.setInt(5, cId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println(
							"Course name:" + res.getString("COURSE NAME")
									+ "\nTotal quiz questions:"
									+ res.getString("QUIZ QUESTION NUMBERS")
									+ "\nTotal File materials:"
									+ res.getString("FILE NUMBERS")
									+ "\nTotal Link materials:"
									+ res.getString("LINK NUMBERS")
									+ "\nTotal Post materials:"
									+ res.getString("POST NUMBERS")
									+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getCourseQuiz() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter course id");
			int cId = r1.nextInt();

			final String sql;
			sql = "SELECT DISTINCT `student_id`,`course`.`c_id`,`question_number`,"
					+ "`feedback` AS `question`,`answer_number` AS `answer`, "
					+ "(SELECT COUNT(`question_number`) FROM "
					+ "`quiz_questions` where `quiz_questions`.`cid_fk` = ?) "
					+ "AS 'Number of questions', "
					+ "(SELECT AVG(`rating`) FROM `question` where "
					+ "`question`.`question_id` = `question_number`) "
					+ "AS 'average of question', "
					+ "(SELECT AVG(`passing_score`) FROM `quiz` where "
					+ "`quiz`.`quiz_course_id` = ?) AS 'average passing score',"
					+ "`course`.`name` FROM `student_completes_course_material` "
					+ "INNER JOIN `quiz_questions` ON "
					+ "`student_completes_course_material`.`c_id` = `quiz_questions`.`cid_fk` "
					+ "INNER JOIN `quiz_question_answers` ON "
					+ "`quiz_questions`.`question_number` = "
					+ "`quiz_question_answers`.`question_num` "
					+ "INNER JOIN `course` ON `course`.`C_id` "
					+ "= `student_completes_course_material`.`c_id` "
					+ "WHERE `course`.`C_id` =? "
					+ "GROUP BY `question_number` "
					+ "ORDER BY `question_number` DESC, `student_id`";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cId);
			stmt.setInt(2, cId);
			stmt.setInt(3, cId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Student Id:"
							+ res.getString("student_id") + "\nCourse Id:"
							+ res.getString("c_id") + "\nQuestion number:"
							+ res.getString("question_number") + "\nQuestion:"
							+ res.getString("question") + "\nAnswer number:"
							+ res.getString("answer") + "\nNumber of questions:"
							+ res.getString("number of questions")
							+ "\nAverage rating:"
							+ res.getString("average of question")
							+ "\nAverage passing score:"
							+ res.getString("average passing score")
							+ "\nCourse name:" + res.getString("name")
							+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getStudentDetails() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT CONCAT( `student`.`Firstname`,' ', `student`.`Lastname`) "
					+ "AS 'NAME',`course`.`name` AS 'COURSE NAME', "
					+ "`course`.`cost` AS 'COURSE COST',"
					+ "(select SUM(`course`.`cost`) from `course`"
					+ "INNER JOIN `enrolls` ON `enrolls`.`C_id`=`course`.`C_id`"
					+ " where `enrolls`.`S_id`=?) AS 'MONEY SPENT OVERALL',"
					+ "(SELECT COUNT(`student`.`U_id`) from `student` "
					+ "INNER JOIN `playlist` ON "
					+ "`student`.`U_id`=`playlist`.`created_by_student` "
					+ "WHERE `student`.`U_id`=?) AS 'NUMBER OF PLAYLISTS', "
					+ "(SELECT COUNT(`course_material_question_relation`.`student_id`) "
					+ "from `course_material_question_relation` "
					+ "where `course_material_question_relation`.`student_id`=1 )"
					+ " AS 'QUESTIONS ASKED BY STUDENT' FROM  `student` "
					+ "INNER JOIN `enrolls` ON `student`.`U_id`=`enrolls`.`S_id` "
					+ "INNER JOIN `course` ON `enrolls`.`C_id`=`course`.`C_id` "
					+ "INNER JOIN `course_material` ON "
					+ "`course`.`C_id`=`course_material`.`course_id` "
					+ "WHERE `student`.`U_id`=? GROUP by `course`.`name` "
					+ "ORDER BY `course`.`cost` DESC, `course`.`name` ASC";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);
			stmt.setInt(2, sId);
			stmt.setInt(3, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Student Name:" + res.getString("NAME")
							+ "\nCourse name:" + res.getString("COURSE NAME")
							+ "\nCourse cost:" + res.getString("COURSE COST")
							+ "\nTotal money spent:"
							+ res.getString("MONEY SPENT OVERALL")
							+ "\nNumber of playlists:"
							+ res.getString("NUMBER OF PLAYLISTS")
							+ "\nStudent questions:"
							+ res.getString("QUESTIONS ASKED BY STUDENT")
							+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getPlaylists() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter student id");
			int sId = r1.nextInt();

			final String sql;
			sql = "SELECT *FROM `playlist` WHERE `playlist`.`created_by_student`=?";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, sId);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println("Created by Student Id:"
							+ res.getString("created_by_student")
							+ "\nPlaylist name:"
							+ res.getString("name_playlist")
							+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void getPlaylistMaterials() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Playlist name");
			String name = r1.nextLine();

			final String sql;
			sql = "SELECT `playlist_has_course_materials`.`playlist_name_fk` as "
					+ "'playlist_name',"
					+ " `course_material`.`name` AS 'course_material_name' FROM "
					+ "`playlist_has_course_materials` INNER JOIN `course_material`"
					+ " ON `playlist_has_course_materials`.`course_material_id_fk` "
					+ "= `course_material`.`course_material_id` WHERE "
					+ "`playlist_has_course_materials`.`playlist_name_fk`=?";

			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);

			try {
				final ResultSet res = stmt.executeQuery();
				connection.commit();
				while (res.next()) {
					System.out.println(
							"Playlist name:" + res.getString("playlist_name")
									+ "\nCourse materials:"
									+ res.getString("course_material_name")
									+ "\n===================");
				}
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void createPlaylist() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Playlist name");
			String name = r1.nextLine();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter Student Id");
			int sId = r3.nextInt();

			Scanner r2 = new Scanner(System.in);
			System.out.println(
					"Enter Courses with comma separated (i.e. 1,2,3,..)");
			String courses = r1.nextLine();

			List<String> vals = new ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO `playlist_has_course_materials`"
					+ "(`playlist_name_fk`, `cid_fk`, `course_material_id_fk`,`order_materials`) "
					+ "VALUES ");
			int count = 0;
			for (int i = 0; i < courses.split(",").length; i++) {
				Scanner ri = new Scanner(System.in);
				System.out.println("Enter Course Materials for course id "
						+ courses.split(",")[i] + "(i.e. 1,2,3,..)");
				String courseMaterials = ri.nextLine();
				for (int j = 0; j < courseMaterials.split(",").length; j++) {
					vals.add(name);
					vals.add(courses.split(",")[i]);
					vals.add(courseMaterials.split(",")[j]);
					vals.add("");
					sb.append("(?,?,?,?),");
				}
			}

			final String sql = sb
					.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, ";")
					.toString();
			final String sql1 = "INSERT INTO `playlist`(`name_playlist`, `created_by_student`) VALUES (?,?)";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			final PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setString(1, name);
			stmt1.setInt(2, sId);
			for (int i = 1; i <= vals.size(); i++) {
				if (i % 4 == 0)
					stmt.setInt(i, (i / 4));
				else
					stmt.setString(i, vals.get(i - 1));
			}
			try {
				stmt1.executeUpdate();
				stmt.executeUpdate();
				connection.commit();
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Failure", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void removePlaylist() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Playlist name");
			String name = r1.nextLine();

			final String sql = "DELETE FROM `playlist_has_course_materials` WHERE `playlist_name_fk`=?";
			final String sql1 = "DELETE FROM `playlist` WHERE `name_playlist`=?";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			final PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setString(1, name);
			try {
				stmt.executeUpdate();
				stmt1.executeUpdate();
				connection.commit();
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Failure", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void addCm() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Playlist name");
			String name = r1.nextLine();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Course Id");
			int cId = r2.nextInt();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter Course material id");
			int cmId = r3.nextInt();

			final String sql, sql1;
			sql = "SELECT COUNT(*) as cnt FROM `playlist_has_course_materials` WHERE "
					+ "`playlist_name_fk` = ?";
			sql1 = "INSERT INTO `playlist_has_course_materials`"
					+ "(`playlist_name_fk`, `cid_fk`, `course_material_id_fk`, "
					+ "`order_materials`) VALUES " + "(?,?,?,?)";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			final ResultSet res = stmt.executeQuery();
			int cnt = 0;
			while (res.next()) {
				cnt = res.getInt("cnt");
			}
			final PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setString(1, name);
			stmt1.setInt(2, cId);
			stmt1.setInt(3, cmId);
			stmt1.setInt(4, (cnt + 1));
			try {
				stmt1.executeUpdate();
				connection.commit();
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void removeCm() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Playlist name");
			String name = r1.nextLine();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Course Id");
			int cId = r2.nextInt();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter Course material id");
			int cmId = r3.nextInt();

			final String sql;
			sql = "DELETE FROM `playlist_has_course_materials` WHERE "
					+ "`playlist_name_fk` = ? AND `cid_fk`=? AND `course_material_id_fk` = ?";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setInt(2, cId);
			stmt.setInt(3, cmId);

			try {
				stmt.executeUpdate();
				connection.commit();
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	private static void moveCm() {

		/* Database connection */
		String url = "jdbc:mysql://localhost:3306/trainly";
		String username = "root";
		String password = "";

		try (Connection connection = DriverManager.getConnection(url, username,
				password)) {
			connection.setAutoCommit(false);
			Scanner r1 = new Scanner(System.in);
			System.out.println("Enter Playlist name");
			String name = r1.nextLine();

			Scanner r2 = new Scanner(System.in);
			System.out.println("Enter Course Id");
			int cId = r2.nextInt();

			Scanner r3 = new Scanner(System.in);
			System.out.println("Enter Course material id");
			int cmId = r3.nextInt();

			Scanner r4 = new Scanner(System.in);
			System.out.println("Enter new position");
			int order = r4.nextInt();

			final String sql, sql1, sql2;
			sql = "SELECT order_materials as om FROM playlist_has_course_materials "
					+ "WHERE playlist_name_fk=? AND cid_fk=? AND course_material_id_fk=?";
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setInt(2, cId);
			stmt.setInt(3, cmId);
			int om = 0;
			final ResultSet res = stmt.executeQuery();
			while (res.next()) {
				om = res.getInt("om");
			}
			sql1 = "UPDATE `playlist_has_course_materials` SET "
					+ "`order_materials`=? WHERE playlist_name_fk=? AND "
					+ "cid_fk=? AND course_material_id_fk=?";
			final PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setInt(1, order);
			stmt1.setString(2, name);
			stmt1.setInt(3, cId);
			stmt1.setInt(4, cmId);

			sql2 = "UPDATE `playlist_has_course_materials` SET "
					+ "`order_materials`=? WHERE playlist_name_fk=? "
					+ "AND `order_materials`=? AND cid_fk<>? AND course_material_id_fk<>?";
			final PreparedStatement stmt2 = connection.prepareStatement(sql2);
			stmt2.setInt(1, om);
			stmt2.setString(2, name);
			stmt2.setInt(3, order);
			stmt2.setInt(4, cId);
			stmt2.setInt(5, cmId);

			try {
				stmt1.executeUpdate();
				stmt2.executeUpdate();
				connection.commit();
				System.out.println("Select another operation");
				System.out.println("=======================================");
				initialize();
			} catch (SQLException e) {
				connection.rollback();
				throw new IllegalStateException("Issue while query!", e);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
}
