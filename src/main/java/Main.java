import java.sql.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Anna on 01.02.2017.
 */
public class Main {

    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/flats";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "anna4e";

    static Connection conn;

      public static void main(String[] args) {
        String value_of_param;
        Scanner sc = new Scanner(System.in);

        try {
            try {
                // create connection
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

                while (true) {
                    System.out.println("Enter the parameters to select apartments:");
                    System.out.println("1: price");
                    System.out.println("2: area");
                    System.out.println("3: district");
                    System.out.println("4: number_of_rooms");
                    System.out.println("5: show all information");
                    String param = sc.nextLine();


                    if (param.equals("1")) {
                        System.out.println("Enter value of parameter");
                        value_of_param = sc.nextLine();
                        searchByPrice(value_of_param);

                    } else if (param.equals("2")) {
                        System.out.println("Enter value of parameter");
                        value_of_param = sc.nextLine();
                        searchByArea(value_of_param);

                    } else if (param.equals("3")) {
                        System.out.println("Enter value of parameter");
                        value_of_param = sc.nextLine();
                        searchByDistrict(value_of_param);

                    } else if (param.equals("4")) {
                        System.out.println("Enter value of parameter");
                        value_of_param = sc.nextLine();
                        searchByNumberOfRooms(value_of_param);

                    }  else if (param.equals("5")) {
                       foolTable();

                } else {
                        return;
                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void foolTable() throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT ALL FROM FLATROOMS");
            setResult(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
        }
    }

    private static void searchByArea(String value_of_param) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT ALL FROM FLATROOMS WHERE area >= ?");
            ps.setInt(1, Integer.parseInt(value_of_param));
            setResult(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
        }

    }



    private static void searchByPrice(String value_of_param) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT ALL FROM FLATROOMS WHERE price >= ?");
            ps.setFloat(1, Float.parseFloat(value_of_param));
            setResult(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            ps.close();
        }
    }

    private static void searchByDistrict(String value_of_param) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT ALL FROM FLATROOMS WHERE district >= ?");
            ps.setString(1, value_of_param);
            setResult(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
        }
    }

    private static void searchByNumberOfRooms(String value_of_param) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT ALL FROM FLATROOMS WHERE number_of_rooms >= ?");
            ps.setInt(1, Integer.parseInt(value_of_param));
            setResult(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();

        }
    }

    private static void setResult(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        try {
            ResultSetMetaData md = rs.getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();
                while (rs.next()) {
                    for (i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(md.getColumnName(i) + "\t\t");
                    }
                    System.out.println();
                }

            }
        } finally {
            rs.close();
        }
    }
}
