import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class user {
    private String SendSN;
    private String ReceiveSN;
    private String EmailName;
    private String Password;

    public user() {

    }

    public user(String SendSN, String ReceiveSN, String EmailName, String Password) {
        this.EmailName = EmailName;
        this.SendSN = SendSN;
        this.Password = Password;
        this.ReceiveSN = ReceiveSN;
    }

    public void setEmailName(String emailName) {
        EmailName = emailName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setReceiveSN(String receiveSN) {
        ReceiveSN = receiveSN;
    }

    public void setSendSN(String sendSN) {
        SendSN = sendSN;
    }

    public String getEmailName() {
        return EmailName;
    }

    public String getPassword() {
        return Password;
    }

    public String getReceiveSN() {
        return ReceiveSN;
    }

    public String getSendSN() {
        return SendSN;
    }

    public void Setup() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp;
        System.out.println("开始输入信息");
        System.out.println("请输入邮箱名称");
        temp = br.readLine();
        setEmailName(temp);
        System.out.println("请输入发送服务器名(smtp服务器)");
        temp = br.readLine();
        setSendSN(temp);
        System.out.println("请输入接收服务器名（pop3服务器）");//语义分析
        temp = br.readLine();
        setReceiveSN(temp);
        System.out.println("请输入授权码");
        temp = br.readLine();
        setPassword(temp);
        System.out.println("登陆成功");
    }
}
