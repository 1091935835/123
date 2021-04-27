import java.awt.*;

public class BlockStone extends Block {
    public BlockStone(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        imageUtil.getSingletonInstance().drawStone(graphics, x,y);
    }
}
