import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class sendEmail {
    public user user;

    public sendEmail(user user) {
        this.user = user;
    }

    public void send(String dest,String content) throws Exception {
        Socket socket = new Socket(user.getSendSN(), 25);//smtp:25

        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os, "gbk");

        writer.write("HELO a\r\n");
        writer.write("auth login\r\n");
        writer.write(Base64.getEncoder().encodeToString(user.getEmailName().getBytes()) + "\r\n");//你的邮箱号的base64加密字符串
        writer.write(Base64.getEncoder().encodeToString(user.getPassword().getBytes()) + "\r\n");//你的邮箱申请码base64加密字符串
        writer.write("MAIL FROM: <" + user.getEmailName() + ">\r\n");
        writer.write("RCPT TO: <" + dest + ">\r\n");    // 确认接收者
        writer.write("DATA\r\n");

        // 正文
        writer.write(content+"\n这是一封自动发送的邮件\r\n");

        writer.write("\r\n.\r\n");    // 正文结束

        writer.write("QUIT\r\n");
        writer.flush();

        // 读取回传的信息
        String line;
        PrintStream ps = new PrintStream(socket.getOutputStream());
        line = reader.readLine();
        while(true) {
            System.out.println(line);
            ps.println(line);
            if("250 OK: queued as.".equals(line)) {
                break;
            }
            line = reader.readLine();
        }
        writer.close();
        System.out.println("发送成功");
    }
}
