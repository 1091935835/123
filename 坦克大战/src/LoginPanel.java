import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JPanel implements KeyListener {
    private Image img;//Login图片资源
    private MainFrame mainFrame;
    private int[][] tankPos = {{280, 352}, {280, 380}};
    private int choice;
    public playertank playertank1 = null;

    public LoginPanel(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        File f = new File("login.png");
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        playertank1 = new playertank(tankPos[choice][0], tankPos[choice][1]);
        playertank1.setDirection(Spirit.RIGHT);


    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, MainFrame.WIDTH, MainFrame.HEIGHT, 0, 0, MainFrame.WIDTH, MainFrame.HEIGHT, null);
        imageUtil.getSingletonInstance().drawTank(g,
                playertank1);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key){
            case KeyEvent.VK_ENTER:
                mainFrame.removeKeyListener(this);
                mainFrame.startGame();
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
                choice = (choice+1)%2;
                playertank1.setX(tankPos[choice][0]);
                playertank1.sety(tankPos[choice][1]);
                repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}