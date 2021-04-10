import javafx.scene.control.skin.CellSkinBase;
import javafx.scene.control.skin.TextInputControlSkin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Tank extends Spirit {
    public Tank(int x, int y) {
        super(x, y);
        aliveFrame = 2;
        explodeFrame = 3;
        SPEED = 5;
    }

    public Bullet fire() {
        Bullet bullet = null;
        if (super.getState() == Spirit.ALIVE) {
            int bulletX, bulletY;
            bulletX = super.getX();
            bulletY = super.getY();

            switch (super.getDirection()) {
                case Spirit.UP:
                    bulletY -= getWidth() / 2;
                    break;
                case Spirit.RIGHT:
                    bulletX += getWidth() / 2;
                    break;
                case Spirit.DOWN:
                    bulletY += getWidth() / 2;
                    break;
                case Spirit.LEFT:
                    bulletX -= getWidth() / 2;
                    break;
            }
            bullet = new Bullet(bulletX, bulletY, getDirection());

        }
        return bullet;
    }//举行之间的检测，下一步怎么着
    public boolean isConlide(Block block){
        boolean result = false;
        int tempx;
        int tempy;
        tempx = getX();
        tempy = getY();
        switch (getDirection()){
            case Spirit.UP:
                tempy-=SPEED;
                break;
            case Spirit.RIGHT:
                tempx+=SPEED;
                break;
            case Spirit.DOWN:
                tempy+=SPEED;
                break;
            case Spirit.LEFT:
                tempx-=SPEED;
        }
        Rectangle rect1 =new Rectangle(tempx-width/2,tempy-width/2,width,width);
        Rectangle rect2 = new Rectangle(block.getX()-block.getWidth()/2,
                block.getY()-block.getHeight()/2, block.getWidth(),block.getHeight());
        if (rect1.intersects(rect2)){
            result = true;
        }
        return result;

    }
}


