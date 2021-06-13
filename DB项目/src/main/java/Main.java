import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        int option = -1;
        int option2 = -1;
        FunctionController fc = new FunctionController();
        Scanner sc = new Scanner(System.in);
        System.out.println("欢迎使用学生信息管理系统");
        System.out.println("0:管理员");
        System.out.println("1:普通用户（仅可使用查询）");
        option2 = sc.nextInt();
        switch (option2){
            case 0:
                DataBase.ChooseUser("root","123456");
                break;
            case 1:
                fc.Useraccount();
        }
        while (option!=0) {
            System.out.println("选择一下操作");
            System.out.println("1:查询");
            System.out.println("2:增加");
            System.out.println("3:更改");
            System.out.println("4:删除");
            System.out.println("5:显示分析表");
            System.out.println("0:退出");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    fc.SelectAndShow();
                    break;
                case 2:
                    fc.Add();
                    break;
                case 3:
                    fc.Alter();
                    break;
                case 4:
                    fc.Delete();
                    break;
                case 5:
                    fc.DrawrGaphic();
                    System.out.println("已保存在目录中");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("出错");
            }
        }


    }
}
