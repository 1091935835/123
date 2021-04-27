import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class imageUtil {
    private Image img = null;
    private int imgX = 0;
    private int imgY = 0;
    public static final int BLOCKWIDTH = 34;
    public static final int GRASS = 0;
    private static imageUtil singletonInstance = null;
    private int[][][] tankX = new int[8][4][2];//类型/方向/状态
    private int[] tankY = {0,0,0,0,BLOCKWIDTH,BLOCKWIDTH,BLOCKWIDTH,BLOCKWIDTH};
    private int[][] mapBlockXY = {{4*BLOCKWIDTH,7*BLOCKWIDTH,5*BLOCKWIDTH,8*BLOCKWIDTH}};
    private int[][][]cartoonXY = {{{544,136},{578,136},{612,136},{646,136}},
            {{680,136},{714,136},{748,136},{-34,-34}},
            {{442,238},{510,238},{476,238},{-34,-34}}};
    private int[] blockGrass = {136,238,170,272};
    private int[] blockBrick = {612,170,629,187};
    private int[] blockWater = {0,238,34,272};
    private int[] blockStone = {0,204,17,221};
    //下构造函数
    private imageUtil()  {
            File f = new File("robots_sprite.png");
            try {
                img = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int catagory = 0;catagory<8;catagory++){
                for (int direction = 0;direction<4;direction++){
                    for (int frameState = 0;frameState<2;frameState++){
                        tankX[catagory][direction][frameState] = (direction*2+frameState+(catagory%4)*8)*BLOCKWIDTH;
                    }
                }
            }
    }
    public static imageUtil getSingletonInstance() {
        if (singletonInstance==null){
            singletonInstance = new imageUtil();
        }
        return singletonInstance;
    }
    //以上通过singleton模式构造imageUtil
    public void drawSpirit(Graphics g,Spirit spirit){
       if(spirit.getState()!=Spirit.DIED)
        {
            if (spirit instanceof Tank){
                drawTank(g, (Tank)spirit); //tank
            }
            else if (spirit instanceof Bullet){
                drawBullet(g, (Bullet) spirit);
            }
        }
    }
    public void drawTank(Graphics g,Tank tank){
        if (tank.getState() == Spirit.ALIVE ) {
            imgX = tankX[tank.getCatagory()][tank.getDirection()][tank.getFramestate()];
            imgY = tankY[tank.getCatagory()];
        }
        else if (tank.getState() == Spirit.EXPLODE){
            imgX = BLOCKWIDTH*20+tank.getFramestate()*BLOCKWIDTH;
            imgY = 4*BLOCKWIDTH;
        }
        g.drawImage(img,tank.getX()-BLOCKWIDTH/2,tank.getY()-BLOCKWIDTH/2,tank.getX()+BLOCKWIDTH/2,tank.getY()+BLOCKWIDTH/2,
                imgX,imgY,imgX+BLOCKWIDTH,imgY+BLOCKWIDTH,null);

    }
    public void drawBullet(Graphics g,Bullet bullet){
        if (bullet.getState()==Spirit.ALIVE ) {
            imgX = BLOCKWIDTH*5;
            imgY = BLOCKWIDTH*6;
        }
        else if (bullet.getState()==Spirit.EXPLODE){
            imgX = BLOCKWIDTH*16+bullet.getFramestate()*BLOCKWIDTH;
            imgY = 4*BLOCKWIDTH;
        }
        g.drawImage(img,bullet.getX()-BLOCKWIDTH/2,bullet.getY()-BLOCKWIDTH/2,bullet.getX()+BLOCKWIDTH/2,bullet.getY()+BLOCKWIDTH/2,
                imgX,imgY,imgX+BLOCKWIDTH,imgY+BLOCKWIDTH,null);

    }
    public void drawMapBlock(Graphics g, int x,int y,int index){
        g.drawImage(img,x-BLOCKWIDTH/2,y-BLOCKWIDTH/2,x+BLOCKWIDTH/2,y+BLOCKWIDTH/2,
                mapBlockXY[GRASS][0],mapBlockXY[GRASS][1],mapBlockXY[GRASS][2],mapBlockXY[GRASS][3],null);//画的图块可以扩展
    }
    public void drawCartoon(Graphics graphics, Cartoon cartoon){
        int[][] pos = new int[4][2];
        pos[0][0] = cartoon.getX()-BLOCKWIDTH/2;
        pos[0][1] = cartoon.getY()-BLOCKWIDTH/2;
        pos[1][0] = pos[0][0]+BLOCKWIDTH;
        pos[1][1] = pos[0][1]+BLOCKWIDTH;
        pos[2][0] = cartoonXY[cartoon.getCartoonType()][cartoon.getFrameNUmber()][0];
        pos[2][1] =cartoonXY[cartoon.getCartoonType()][cartoon.getFrameNUmber()][1];
        pos[3][0] =pos[2][0]+BLOCKWIDTH;
        pos[3][1] =pos[2][1]+BLOCKWIDTH;
        graphics.drawImage(img,pos[0][0],pos[0][1],pos[1][0],pos[1][1],pos[2][0],pos[2][1],pos[3][0],pos[3][1],null);

    }
    public void drawGrass(Graphics g,int x,int y){
        g.drawImage(img,x-BLOCKWIDTH/2,y-BLOCKWIDTH/2,x+BLOCKWIDTH/2,y+BLOCKWIDTH/2,
                blockGrass[0],blockGrass[1],blockGrass[2],blockGrass[3],null);

    }
    public void drawBrick(Graphics g,int x,int y){
        g.drawImage(img,x-BLOCKWIDTH/2,y-BLOCKWIDTH/2,x,y,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
        g.drawImage(img,x,y,x+BLOCKWIDTH/2,y+BLOCKWIDTH/2,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
        g.drawImage(img,x,y-BLOCKWIDTH/2,x+BLOCKWIDTH/2,y,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
        g.drawImage(img,x-BLOCKWIDTH/2,y,x,y+BLOCKWIDTH/2,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
    }
    public void drawWater(Graphics g,int x,int y,int frameState){
        g.drawImage(img,x-BLOCKWIDTH,y-BLOCKWIDTH,x+BLOCKWIDTH,y+BLOCKWIDTH,
               blockWater[0]+frameState,blockWater[1],blockWater[2]+frameState,blockWater[3],null );
    }
    public void drawStone(Graphics g,int x,int y){
        g.drawImage(img,x-BLOCKWIDTH/2,y-BLOCKWIDTH/2,x,y,
                blockStone[0],blockStone[1],blockStone[2],blockStone[3],null);
        g.drawImage(img,x,y,x+BLOCKWIDTH/2,y+BLOCKWIDTH/2,
                blockStone[0],blockStone[1],blockStone[2],blockStone[3],null);
        g.drawImage(img,x,y-BLOCKWIDTH/2,x+BLOCKWIDTH/2,y,
                blockStone[0],blockStone[1],blockStone[2],blockStone[3],null);
        g.drawImage(img,x-BLOCKWIDTH/2,y,x,y+BLOCKWIDTH/2,
                blockStone[0],blockStone[1],blockStone[2],blockStone[3],null);
    }
}
