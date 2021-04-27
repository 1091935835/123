package MapEditor;

import java.awt.*;

public class EltGrass extends Element {
    public EltGrass(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawGrass(g,this.x,this.y);
        super.draw(g);
    }
    public Element clone() {
        return new EltGrass(x,y);

    }
    public String toString(){
        return "grass="+x/34+","+y/34;
    }
}
