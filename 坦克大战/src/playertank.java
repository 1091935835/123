import java.awt.*;
import java.awt.event.KeyEvent;

public class playertank extends Tank {
    private int RebornFrame = 40;
    public playertank(int x, int y) {
        super(x, y);
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key)
        {
            case KeyEvent.VK_RIGHT:
                setDirection(Spirit.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
               setDirection(Spirit.LEFT);
                break;
            case KeyEvent.VK_UP:
                setDirection(Spirit.UP);
                break;
            case KeyEvent.VK_DOWN:
                setDirection(Spirit.DOWN);
                break;
        }
    }

}
