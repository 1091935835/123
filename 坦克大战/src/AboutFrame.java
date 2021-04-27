import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutFrame extends JFrame implements ActionListener {
    public AboutFrame(){
        setTitle("关于...");
        setLayout(null);
        setSize(380,150);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        this.setLocation((size.width-380)/2,(size.height-150)/2);
        this.setResizable(false);

        JLabel label1 = new JLabel("仅供学习使用");
        label1.setSize(320,20);
        label1.setLocation(20,20);
        add(label1);

        JButton button1 = new JButton("确定");
        button1.setBounds(280,80,80,30);
        button1.addActionListener(this);
        add(button1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.dispose();

    }
}
