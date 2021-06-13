import java.sql.*;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class BasicFunctionController {
    int state = 0;
    public BasicFunctionController() throws SQLException {
        Connection conn = DataBase.getConn();
        String sql = "" + "USE university";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.execute();
    }
    public  void addStu(Student stu) throws SQLException {
        Connection conn = DataBase.getConn();
        String sql = "" + "insert into Students" + "(学号,姓名,性别,省份,学院,专业,年级)" + "values(?,?,?,?,?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setInt(1, stu.getStunum());
        ptmt.setString(2, stu.getName());
        ptmt.setString(3, stu.getGender());
        ptmt.setString(4, stu.getShengfen());
        ptmt.setString(5, stu.getSchool());
        ptmt.setString(6, stu.getMajor());
        ptmt.setString(7, stu.getYear());
        ptmt.execute();
    }
    public Student SELECTStu(int Stunum)throws SQLException{
        Connection conn = DataBase.getConn();
        String sql = "" + "select * from Students where 学号=? ";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, Stunum);
        ResultSet rs = ptmt.executeQuery();
       Student stu1 = new Student();
        if (rs.next()) {
            stu1.setStunum((Integer.parseInt(rs.getString("学号"))));
            stu1.setName(rs.getString("姓名"));
            stu1.setGender(rs.getString("性别"));
            stu1.setShengfen(rs.getString("省份"));
            stu1.setSchool(rs.getString("学院"));
            stu1.setMajor(rs.getString("专业"));
            stu1.setYear(rs.getString("年级"));
        } else {
            stu1.setStunum(-1);
        }
        return stu1;

    }
    public void deleStu(Student stu) throws SQLException {
        Connection conn = DataBase.getConn();
        String sql = "" + "delete from Students " + "where 学号=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, stu.getStunum());
        ptmt.execute();

    }
    public void alter(Student stu) throws SQLException {
        System.out.println("请重新输入个人信息：");
        int temp = stu.getStunum();
        Scanner in = new Scanner(System.in);
        System.out.println("输入新的学号");
        stu.setStunum(Integer.parseInt(in.nextLine()));
        System.out.println("输入新名");
        stu.setName(in.nextLine());
        System.out.println("输入性别");
        stu.setGender(in.nextLine());
        System.out.println("输入省份");
        stu.setShengfen(in.nextLine());
        System.out.println("输入学院");
        stu.setSchool(in.nextLine());
        System.out.println("输入专业");
        stu.setMajor(in.nextLine());
        System.out.println("输入年级");
        stu.setYear(in.nextLine());

        Connection conn = DataBase.getConn();
        String sql = "" + "UPDATE Students set 学号=?, 姓名=?,性别=?,省份=?,学院=?,专业=?,年级=?" + "where 学号=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, stu.getStunum());
        ptmt.setString(2, stu.getName());
        ptmt.setString(3, stu.getGender());
        ptmt.setString(4, stu.getShengfen());
        ptmt.setString(5, stu.getSchool());
        ptmt.setString(6, stu.getMajor());
        ptmt.setString(7, stu.getYear());
        ptmt.setInt(8, temp);
        ptmt.execute();
    }
    public int getCountByYear(String year) throws SQLException{
        Connection conn = DataBase.getConn();
        String sql = "" + "SELECT COUNT(学号) FROM Students WHERE 年级= ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1,year);
        ResultSet rs = ptmt.executeQuery();
        if (rs.next()){
            int getCount = rs.getInt(1);
            return getCount;
        }
        else {
            return -1;
        }

    }
    public ArrayList<String> getYears() throws SQLException{
        Connection conn = DataBase.getConn();
        String sql = "" + "SELECT 年级 FROM Students GROUP BY 年级";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ResultSet rs = ptmt.executeQuery();
        ArrayList<String> Results = new ArrayList<String>();
        while (rs.next()){
             Results.add(rs.getString(1));
        }
        return Results;

    }
    public  void CreateAndAuthUser() throws SQLException {
        Connection conn = DataBase.getConn();
        String sql ="grant select on university.* to user@localhost";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.execute();

        DataBase.ChooseUser("user","123456");

    }





}

