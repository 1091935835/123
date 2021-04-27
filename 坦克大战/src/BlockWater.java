import java.awt.*;

public class BlockWater extends Block {
    private int frameState;
    public BlockWater(int x, int y) {
        super(x, y);
        frameState = 0;
    }

    @Override
    public void draw(Graphics graphics) {

        imageUtil.getSingletonInstance().drawWater(graphics, x, y, frameState);
        frameState = (frameState + 1) % 10;
    }
}
