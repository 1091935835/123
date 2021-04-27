import java.awt.*;
import java.beans.PropertyEditorSupport;

public class Spirit {

    public static final int ALIVE = 0;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int EXPLODE = 1;
    public static final int DIED= 2;
    protected int x,y;
    protected double SPEED;
    protected int catagory;
    protected int direction;
    protected int state;
    protected int framestate;
    protected int width;
    protected int aliveFrame;
    protected int explodeFrame;
    public Spirit(int x, int y){
        this.x = x;
        this.y = y;
        width = 34;
        catagory = 0;
        direction = UP;
        state = ALIVE;
        framestate = 0;
        SPEED = 10;
        aliveFrame = 0;
        explodeFrame = 0;
    }
    //以下写get/set函数
    public void setX(int x){
        this.x = x;
    }
    public void sety(int y){
        this.y = y;
    }
    public void setCatagory(int catagory){
        this.catagory = catagory;
    }
    public void setSPEED(double speed){
        this.SPEED= speed;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public void setFramestate(int framestate){
        this.framestate = framestate;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setState(int state){
        this.state = state;
    }

    public int getCatagory() {
        return catagory;
    }
    public int getDirection() {
        return direction;
    }
    public int getFramestate() {
        return framestate;
    }
    public double getSPEED() {
        return SPEED;
    }
    public int getState() {
        return state;
    }
    public int getWidth() {
        return width;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    //setget设置完成,下为动的方法
    public void move(){
        if (state == ALIVE){
            switch (direction){
                case UP:
                    y-=SPEED;
                    break;//up
                case RIGHT:
                    x+=SPEED;
                    break;//right
                case DOWN:
                    y+=SPEED;
                    break;//down
                 case LEFT:
                    x-=SPEED;
                    break;//left
            }
        }
    }
    public boolean isCollide(Spirit spirit){
        boolean result = false;
        if (state == ALIVE){
            int tempx;
            int tempy;
            tempx = x;
            tempy = y;

            double length = Math.sqrt(Math.pow(tempx-spirit.x,2)+Math.pow(tempy-spirit.y,2));
            if (length<(this.width+spirit.width)/2){
                result = true;
            }
        }
        return result;
    }
    public void caculateData(){
        switch (state){
            case Spirit.ALIVE:
                framestate++;
                if (framestate==aliveFrame){
                    framestate=0;
                }
                break;
            case Spirit.EXPLODE:
                framestate++;
                if (framestate==explodeFrame){
                    state = Spirit.DIED;
                }
                break;
        }
    }
    public void draw(Graphics g){
        imageUtil.getSingletonInstance().drawSpirit(g,this);
    }
}
