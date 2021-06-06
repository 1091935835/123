import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner in =new Scanner(System.in);
        int option;
        //user user = new user("smtp.qq.com", "pop.qq.com", "1091935835@qq.com", "okxqdoskkjkgbabe");
        user user = new user();
        user.Setup();
        sendEmail se = new sendEmail(user);
        ReceiveEmail re = new ReceiveEmail(110,user);

        System.out.println("1:写信（基于smtp）\n");
        System.out.println("2:收信（基于pop3）\n");
        System.out.println("0:退出\n");
        option = in.nextInt();
        while (option!=0) {
            switch (option) {
                case 1:
                    String content = null;
                    String dest = null;

                    content = in.next();
                    System.out.println("请输入收件人\n");
                    dest = in.next();
                    se.send(dest, content);
                    break;
                case 2:
                    int mailNum;
                    System.out.println("请输入下载第几封邮件\n");
                    mailNum = in.nextInt();
                    re.recieveMail(mailNum);
                    break;
                default:
                    System.out.println("出错\n");
                    break;
            }
            option = in.nextInt();
            System.out.println(option);
        }
        System.out.println("感谢使用\n");
    }
}
