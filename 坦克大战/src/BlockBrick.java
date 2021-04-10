import java.awt.*;

public class BlockBrick extends Block {
    public BlockBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        imageUtil.getSingletonInstance().drawBrick(graphics, x, y);
    }
}
