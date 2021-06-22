import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {
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
            String sql = "use university";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.execute();
            //头部定义的三个变量，分别确定连接数据库的位置，用户名，密码
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConn() {
        return conn;
    }
    public static void addDB(Product desc, int qty) throws SQLException {
        Connection conn = DB.getConn();
        String sql = "" + "insert into Sale" + "(ID,Proname,price,qty)" + "values(?,?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, desc.getID());
        ptmt.setString(2, desc.getName());
        ptmt.setFloat(3, desc.getPrice());
        ptmt.setInt(4, qty);
        ptmt.execute();
    }
}
