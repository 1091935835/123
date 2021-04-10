import java.awt.*;

public class Bullet extends Spirit{
   public Bullet(int x,int y,int direction){
       super(x,y);
       setDirection(direction);
       setSPEED(10);
       explodeFrame = 3;
       setWidth(14);
   }
   public boolean isCollide(Block block){
       boolean result = false;
       Rectangle rect1 =new Rectangle(x-width/2,y-width/2,width,width);
       Rectangle rect2 = new Rectangle(block.getX()-block.getWidth()/2,
               block.getY()-block.getHeight()/2, block.getWidth(),block.getHeight());
       if (rect1.intersects(rect2)) {
           result = true;
       }
       return result;
   }



}
