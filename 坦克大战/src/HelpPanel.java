import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {
    private String[] text= {"空格发射","右键向右","左键向左","上键向上","下键向下"};
    JTextArea area;
    public HelpPanel(){
        area = new JTextArea();
        for (int i = 0;i<text.length;i++){
            area.append(text[i]);
            area.append("\n");
        }
        Dimension size = area.getPreferredSize();
        this.add(area);
        setBounds(0,0,size.width,size.height);
    }
}
