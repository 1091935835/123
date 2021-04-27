import java.awt.*;

public class BlockFrame extends Block {
    public BlockFrame(int x, int y,int width,int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.x = x+width/2;
        this.y = y+height/2;
    }

    @Override
    public void draw(Graphics graphics) {

    }
}
