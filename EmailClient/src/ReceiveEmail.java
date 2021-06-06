import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class ReceiveEmail {

    private Socket socket = null;

    private boolean debug = true;

    public user user;

    /*构造函数*/
    public ReceiveEmail(int port, user user) throws UnknownHostException, IOException {
        try {
            this.user = user;
            socket = new Socket(user.getReceiveSN(), port);
            //在新建socket的时候就已经与服务器建立了连接

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            System.out.println("建立连接！");
        }
    }
    //接收邮件程序
    public boolean recieveMail(int mailNum) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            user(in, out);

            System.out.println("user 命令执行完毕！");

            pass(in, out);

            System.out.println("pass 命令执行完毕！");

            stat(in, out);

            System.out.println("stat 命令执行完毕！");

            list(in, out);

            System.out.println("list 命令执行完毕！");
            System.out.println("已保存到本地");

            retr(mailNum, in, out);

            System.out.println("retr 命令执行完毕！\n邮件已保存至目录下\n");

            quit(in, out);

            System.out.println("quit 命令执行完毕！");

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
        return true;
    }
    //得到服务器返回的一行命令
    public String getReturn(BufferedReader in) {

        String line = "";

        try {
            line = in.readLine();

            if (debug) {

                System.out.println("服务器返回状态:" + line);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return line;
    }
    //从返回的命令中得到第一个字段,
    public String getResult(String line) {

        StringTokenizer st = new StringTokenizer(line, " ");

        return st.nextToken();
    }
    //发送
    private String sendServer(String str, BufferedReader in, BufferedWriter out) throws IOException {

        out.write(str);//发送命令

        out.newLine();//发送空行

        out.flush();//清空缓冲区

        if (debug) {

            System.out.println("已发送命令:" + str);
        }
        return getReturn(in);
    }
    //user命令
    public void user(BufferedReader in, BufferedWriter out) throws IOException {

        String result;

        result = getResult(getReturn(in));//先检测连接服务器是否已经成功

        if (!"+OK".equals(result)) {

            throw new IOException("连接服务器失败!");
        }

        result = getResult(sendServer("user " + user.getEmailName(), in, out));//发送user命令

        if (!"+OK".equals(result)) {

            throw new IOException("用户名错误!");
        }
    }
    //pass命令
    public void pass(BufferedReader in, BufferedWriter out) throws IOException {

        String result;

        result = getResult(sendServer("pass " + user.getPassword(), in, out));

        if (!"+OK".equals(result)) {

            throw new IOException("密码错误!");
        }
    }
    //stat命令
    public int stat(BufferedReader in, BufferedWriter out) throws IOException {

        String result;

        String line;

        int mailNum = 0;

        line = sendServer("stat", in, out);

        StringTokenizer st = new StringTokenizer(line, " ");

        result = st.nextToken();

        if (st.hasMoreTokens())

            mailNum = Integer.parseInt(st.nextToken());

        if (!"+OK".equals(result)) {

            throw new IOException("查看邮箱状态出错!");
        }

        System.out.println("共有邮件" + mailNum + "封");
        return mailNum;
    }
    //无参数list命令
    public void list(BufferedReader in, BufferedWriter out) throws IOException {

        StringBuilder message = new StringBuilder();

        String line;

        line = sendServer("list", in, out);

        while (!".".equalsIgnoreCase(line)) {

            message.append(line).append("\n");

            line = in.readLine();
        }

        System.out.println(message);
    }
    //带参数list命令
    public void list_one(int mailNumber, BufferedReader in, BufferedWriter out) throws IOException {

        String result;

        result = getResult(sendServer("list " + mailNumber, in, out));

        if (!"+OK".equals(result)) {

            throw new IOException("list错误!");
        }
    }
    //得到邮件详细信息
    public String getMessagedetail(BufferedReader in) throws UnsupportedEncodingException {

        StringBuilder message = new StringBuilder();

        String line;

        try {
            line = in.readLine();

            while (!".".equalsIgnoreCase(line)) {

                message.append(line).append("\n");

                line = in.readLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return message.toString();
    }
    //retr命令
    public void retr(int mailNum, BufferedReader in, BufferedWriter out) throws IOException, InterruptedException {

        String result;
        String temp;
        result = getResult(sendServer("retr " + mailNum, in, out));

        if (!"+OK".equals(result)) {

            throw new IOException("接收邮件出错!");
        }

        System.out.println("第" + mailNum + "封");
        //System.out.println(getMessagedetail(in));
        save("Email",getMessagedetail(in));
        Thread.sleep(30);
    }
    //退出
    public void quit(BufferedReader in, BufferedWriter out) throws IOException {

        String result;

        result = getResult(sendServer("QUIT", in, out));

        if (!"+OK".equals(result)) {

            throw new IOException("未能正确退出");
        }
    }
    //下载保存
    public void save(String Filename, String content) throws IOException {
        File tempFile = new File(Filename + ".eml");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8"));
        out.write(content);
        out.close();

    }
}