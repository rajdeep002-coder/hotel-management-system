import java.sql.*;
import java.util.Scanner;

public class jdbc_test2 {

    static final String URL = "jdbc:mysql://127.0.0.1:3306/hoteel_db";
    static final String USER = "root";
    static final String PASS = "1234";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);

            System.out.println("Connected to Database!");

            int choice;

          
            while (true) {

                System.out.println("\n HOTEL SYSTEM ");
                System.out.println("1. Book Room");
                System.out.println("2. View All Reservations");
                System.out.println("3. Checkout");
                System.out.println("4. Exit");

                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        bookRoom();
                        break;

                    case 2:
                        viewRecords();
                        break;

                    case 3:
                        checkout();
                        break;

                    case 4:
                        System.out.println("Thank You!");
                        con.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void bookRoom() {

        try {
            sc.nextLine(); 

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Contact No: ");
            String contact = sc.nextLine();

            System.out.print("Enter Room No: ");
            int room = sc.nextInt();

            String sql = "INSERT INTO reservations(name, contact, room_no) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setInt(3, room);

            ps.executeUpdate();

            System.out.println("Room Booked Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    static void viewRecords() {

        try {

            String sql = "SELECT * FROM reservations";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n----- Reservation List -----");

            while (rs.next()) {

                System.out.println(
                        "Reg ID: " + rs.getInt("reg_id") +
                        ", Name: " + rs.getString("name") +
                        ", Contact: " + rs.getString("contact") +
                        ", Room: " + rs.getInt("room_no") +
                        ", Check-in: " + rs.getTimestamp("check_in") +
                        ", Status: " + rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    static void checkout() {

        try {

            System.out.print("Enter Reg ID: ");
            int id = sc.nextInt();

            String sql = "UPDATE reservations SET status='Checked Out' WHERE reg_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Checkout Successful!");
            } else {
                System.out.println("Record Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
