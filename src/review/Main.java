package review;

import java.sql.*;
        import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try{
            int choice = 0;

            //calling the student class and making the object.
            // we are making the student class object to call that class functions/methods
            student s = new student();
            do{
                System.out.println("Press 1 for New Student Record\n 2.Press 2 for getting the Student Data\n 3.Press 3 for updating the student email\n 4.Press 4 for deleting the student data\n 5.Press 5 for exit the Menu");
                Scanner choose = new Scanner(System.in);
                choice = choose.nextInt();
                switch (choice){
                    case 1:
                        s.getStudentDetail();
                        s.insertStudentData();
                        break;
                    case 2:
                        s.searchStudent();
                        break;
                    case 3:
                        //put update function
                        break;
                    case 4:
                        s.deleteStudent();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Please Enter correct choice:");
                }
            }while(choice!=5);{
                System.out.println("Thanks for using the Application!");
            }
        }catch (Exception e){

        }
    }
}

//making the student class that contains every functionality
class student{
    private String name; // we made this private because we dont want anyone to access outside of class
    private String email;
    private String city;
    private int phone;

    //function that takes input from student
    public void getStudentDetail(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Your Name");
        name = input.nextLine();
        System.out.println("Enter Your Email");
        email = input.nextLine();
        System.out.println("Enter Your City");
        city = input.nextLine();
        System.out.println("Enter Your Phone");
        phone = input.nextInt();
    }

    //now making a function that connects the user input and pass it to the database table columns
    public void insertStudentData() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //first we need to call the database connection
        dbmsconnection dbmsconnection = new dbmsconnection("jdbc:mysql://localhost:3306/jdbc_review?SSL=false","root","123456789");
        // I am calling the function/method that is present in my dbmsconnection class
        Connection con = dbmsconnection.getConnection();

        //making the mysql query
        String sql = "insert into student(name,email,city,phone) values (?,?,?,?);";
        //now preparing my above statement
        PreparedStatement stmt = con.prepareStatement(sql);
        // PreparedStatement is used for taking input from user and sending inout to database ; its used for creating, editing, updating, deleting the data
        stmt.setString(1,name);
        stmt.setString(2,email);
        stmt.setString(3,city);
        stmt.setInt(4,phone);

        //finally execute my preparedStatements
        int i = stmt.executeUpdate();
        System.out.println("The Record Has Been Saved Successfully :)");
    }

    //function for showing the specific student record
    public void searchStudent() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //first we need to call the database connection
        dbmsconnection dbmsconnection = new dbmsconnection("jdbc:mysql://localhost:3306/jdbc_again?SSL=false","root","techproedu");
        //here I am calling the function/method that is present in my dbmsconnection classs
        Connection con = dbmsconnection.getConnection();
        System.out.println("Enter Student Email to get all Record:");
        Scanner input = new Scanner(System.in);
        String emailInput = input.nextLine();
        //making the mysql query for fetching the student data
        String sql = "select * from student where email=?";
        //now preparing my above statement
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,emailInput);

        //getting the result set
        ResultSet rs = stmt.executeQuery();

        //showing the error if the email is not present into the database
        if(rs.next() == false){
            System.out.println("No Email found into the database record!");
        }else{
            System.out.println(rs.getInt(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getInt(5));
        }
    }

    //function for deleting the student record
    public void deleteStudent() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //first we need to call the database connection
        dbmsconnection dbmsconnection = new dbmsconnection("jdbc:mysql://localhost:3306/jdbc_again?SSL=false","root","techproedu");
        //here I am calling the function/method that is present in my dbmsconnection classs
        Connection con = dbmsconnection.getConnection();
        System.out.println("Enter Student Email to delete his/her Record:");
        Scanner input = new Scanner(System.in);
        String emailInput = input.nextLine();
        //making the mysql query for fetching the student data
        String sql = "delete from student where email=?";
        //now preparing my above statement
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,emailInput);
        int i = stmt.executeUpdate();

        //if student record deleted successfully!
        if(i>0){
            System.out.println("Student Record Has Been Deleted Successfully!");
        }else{
            System.out.println("No Email found into the database record!");
        }
    }

    //function for updating the student Name
    public void updateStudent(){

    }
}

//making the JDBC class in which we have just mysql connection
class dbmsconnection{ // we are making new class to use Student class table functionality like add, search, delete, update
    String url;
    String username;
    String password;

    //I am going to make a setter for mysql connection
    public dbmsconnection(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    //make a function that contain core code of jdbc connection
    public Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Connection con = null;
        //calling the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url,username,password);
        //print this if our connection got successful
        System.out.println("Your connection has been established successfully!");
        return con;
    }
}
