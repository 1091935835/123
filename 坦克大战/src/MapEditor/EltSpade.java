package MapEditor;

import java.awt.*;

public class EltSpade extends Element {
    public EltSpade(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawSpade(g,x,y);
        super.draw(g);
    }
}
