package MapEditor;

import java.awt.*;

public class Operation {
    private int x,y;
    private Element element = null;
    private static Operation singletonInstance = null;
    private Operation(){}
    public static Operation getSingletonInstance(){
        if (singletonInstance==null){
            singletonInstance = new Operation();
        }
        return singletonInstance;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setElement(Element element) {
        this.element = element;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void draw(Graphics graphics){
        int tempx,tempy;
        if (element!=null) {
            tempx = element.x;
            tempy = element.y;
            element.x = x / 34 * 34;
            element.y = y / 34 * 34;
            element.draw(graphics);
            element.x = tempx;
            element.y = tempy;
        }
    }

    public Element getElement() {
        return element;
    }
}
