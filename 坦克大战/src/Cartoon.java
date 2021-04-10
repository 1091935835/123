import java.awt.*;

public class Cartoon {
    public static final int BEXPLOSION = 0;
    public static final int TEXPLOSION = 1;
    public static final int TRESPAWN = 2;

    private int x,y;
    private int frameCount;
    private int cartoonType;
    private int frameNUmber;
    private int repeatTime;

    public FinishListener finishListener = null;

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public int getCartoonType() {
        return cartoonType;
    }
    public int getFrameNUmber() {
        return frameNUmber;
    }
    public Cartoon(int cartoonType,int x,int y){
        this.x = x;
        this.y = y;
        this.cartoonType = cartoonType;
        frameNUmber = 0;
        repeatTime = 1;
        switch (cartoonType){
            case BEXPLOSION:
                frameCount = 4;
                break;
            case TEXPLOSION:
                frameCount = 3;
                break;
            case TRESPAWN:
                frameCount = 3;
                repeatTime = 10;
                break;
        }
    }
    public void addFinishLitener(FinishListener finishListener){
        this.finishListener = finishListener;
    }
    public void draw(Graphics g){
        if (repeatTime!=0){
            imageUtil.getSingletonInstance().drawCartoon(g,this);
        }
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void caculateData(){
        if (repeatTime!=0){
            frameNUmber++;
            if (frameNUmber==frameCount){
                repeatTime--;
                frameNUmber=0;
            }
            if((finishListener!=null)&&(repeatTime==0)){
                finishListener.doFinish();
            }
        }
    }
    public boolean isDied(){
        return repeatTime==0;
    }
}
