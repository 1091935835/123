package MapEditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ImageUtil {
    public static final int BLOCKW = 34;

    private static ImageUtil singletonInstance = null;
    private Image image = null;

    private int[] blockGrass = {136,238,170,272};
    private int[] blockWater = {0,238,34,272};
    private int[] blockStone = {0,204,17,221};
    private int[] blockBrick = {612,170,629,187};

    private ImageUtil(){
        File file = new File("robots_sprite.png");
        try{
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ImageUtil getSingletonInstance(){
        if (singletonInstance==null){
            singletonInstance = new ImageUtil();
        }
        return singletonInstance;
    }
    public void drawPtank(Graphics graphics,EltPtank eltPtank){
        graphics.drawImage(image,eltPtank.getX(),eltPtank.getY(),eltPtank.getX()+BLOCKW,eltPtank.getY()+BLOCKW,
                eltPtank.getDirection()*68,34,eltPtank.getDirection()*68+BLOCKW,68,null);
    }
    public void drawAtank(Graphics graphics,EltAtank eltAtank){
        graphics.drawImage(image,eltAtank.getX(),eltAtank.getY(),eltAtank.getX()+BLOCKW,eltAtank.getY()+BLOCKW,
                eltAtank.getDirection()*34,0,eltAtank.getDirection()*34+BLOCKW,34,null);
    }
    public void drawGrass(Graphics graphics,int x,int y){
        graphics.drawImage(image,x,y,x+BLOCKW,y+BLOCKW,blockGrass[0],blockGrass[1],blockGrass[2],blockGrass[3],null);
    }
    public void drawWater(Graphics graphics,int x,int y){
        graphics.drawImage(image,x,y,x+BLOCKW,y+BLOCKW,blockWater[0],blockWater[1],blockWater[2],blockWater[3],null);
    }
    public void drawStone(Graphics graphics,int x,int y){
        graphics.drawImage(image,x,y,x+BLOCKW,y+BLOCKW,blockStone[0],blockStone[1],blockStone[2],blockStone[3],null);
    }
    public void drawBrick(Graphics graphics,int x,int y){
        graphics.drawImage(image,x,y,x+BLOCKW,y+BLOCKW,blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
    }
    public void drawSpade(Graphics graphics,int x,int y){
        graphics.drawImage(image,x,y,x+BLOCKW,y+BLOCKW,816,238,850,272,null);
    }



}
