import java.awt.*;

public abstract class Block {
    protected int x;
    protected int y;
    protected int width;
    protected int height;//地图长宽不同
    public Block(int x,int y){
        this.x = x*imageUtil.BLOCKWIDTH+imageUtil.BLOCKWIDTH/2;
        this.y = y*imageUtil.BLOCKWIDTH+imageUtil.BLOCKWIDTH/2;
        width = imageUtil.BLOCKWIDTH;
        height = imageUtil.BLOCKWIDTH;

    }
    public abstract void draw(Graphics graphics);

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
}
