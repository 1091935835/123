package MapEditor;

import java.awt.*;

public class  Element {
    protected int x,y;
    private boolean isSelected;

    public Element(int x,int y){
        this.x = x;
        this.y = y;
        isSelected = false;
    }
    public void draw(Graphics g){
        Graphics2D graphics2D = (Graphics2D)g;
        Stroke stroke = new BasicStroke(2.0f);
        graphics2D.setStroke(stroke);
        graphics2D.drawRect(x,y,ImageUtil.BLOCKW,ImageUtil.BLOCKW);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public Element click(int x,int y){
        if ((x>=this.x)&&(x<=this.x+ImageUtil.BLOCKW)&&(y>=this.y)&&(x<this.y+ImageUtil.BLOCKW)){
            isSelected = true;
            return this;
        }else {
            isSelected =false;
            return null;
        }
    }
    public Element clone(){
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}
