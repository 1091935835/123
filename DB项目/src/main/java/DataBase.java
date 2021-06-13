
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "123456";

    private static Connection conn = null;
    static {
        //使用try-catch语句，抛出错误
        try {
            Class.forName(JDBC_DRIVER);
            conn= DriverManager.getConnection(DB_URL, USER,PASS);
            //头部定义的三个变量，分别确定连接数据库的位置，用户名，密码
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void ChooseUser(String USER1,String PASS1) throws SQLException {
        conn.close();
        conn= DriverManager.getConnection(DB_URL, USER1,PASS1);
        Connection conn = DataBase.getConn();
        String sql = "" + "USE university";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.execute();
    }
    public static Connection getConn() {
        return conn;
    }
}
