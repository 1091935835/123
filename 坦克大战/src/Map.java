import com.sun.tools.javac.Main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    //以34*34划分窗口
    private ArrayList<Block> blocks= new ArrayList<Block>();
    private int pRECount;
    private int aCount;
    private int aTankTime;
    private int aTankTimeCount;

    private int[][] pTankPos=new int[2][3];//{{350,550,Spirit.UP},{450,550,Spirit.UP}};
    public static final int POS1 = 0;
    public static final int POS2 = 1;
    private int[][] arrFrame = {{0,-imageUtil.BLOCKWIDTH,MainFrame.WIDTH,imageUtil.BLOCKWIDTH},
            {MainFrame.WIDTH,0,imageUtil.BLOCKWIDTH,MainFrame.HEIGHT},
            {0,MainFrame.HEIGHT,MainFrame.WIDTH,imageUtil.BLOCKWIDTH},
            {-imageUtil.BLOCKWIDTH,0,imageUtil.BLOCKWIDTH,MainFrame.HEIGHT}};
    private int[][] aTankPos = new int[2][3];//{{100,100,Spirit.RIGHT},{550,100,Spirit.LEFT}};
    private ArrayList<Point> brickBlocks;
    private ArrayList<Point> waterBlocks;
    private ArrayList<Point> stoneBlocks;
    private ArrayList<Point> grassBlocks;
    public void readData(String file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String string1,string2,line[];
        stoneBlocks.clear();
        grassBlocks.clear();
        brickBlocks.clear();
        waterBlocks.clear();
        while ((string1 = bufferedReader.readLine())!=null){
            line = string1.split("=");
            string1 = line[0];
            string2 = line[1];

            if (string1.compareTo("pTankCount")==0){
                pRECount = Integer.parseInt(string2);
            }
            else if (string1.compareTo("sTankCount")==0)
            {
                aCount = Integer.parseInt(string2);
            }
            else if (string1.compareTo("sTankTimeCount")==0)
            {
                aTankTimeCount = Integer.parseInt(string2);
            }
            else if (string1.compareTo("pTankPos")==0)
            {
                line = string2.split(",");
                pTankPos[0][0] = Integer.parseInt(line[0]);
                pTankPos[0][1] = Integer.parseInt(line[1]);
                pTankPos[0][2] = Integer.parseInt(line[2]);
                string1 = bufferedReader.readLine();
                line = string1.split("=");
                line = line[1].split(",");
                pTankPos[1][0] = Integer.parseInt(line[0]);
                pTankPos[1][1] = Integer.parseInt(line[1]);
                pTankPos[1][2] = Integer.parseInt(line[2]);
            }
            else if (string1.compareTo("sTankPos")==0)
            {
                line = string2.split(",");
                aTankPos[0][0] = Integer.parseInt(line[0]);
                aTankPos[0][1] = Integer.parseInt(line[1]);
                aTankPos[0][2] = Integer.parseInt(line[2]);
                string1 = bufferedReader.readLine();
                line = string1.split("=");
                line = line[1].split(",");
                aTankPos[1][0] = Integer.parseInt(line[0]);
                aTankPos[1][1] = Integer.parseInt(line[1]);
                aTankPos[1][2] = Integer.parseInt(line[2]);
            }
            else if (string1.compareTo("brick")==0){
                addBlock(brickBlocks,string2);
            }
            else if (string1.compareTo("grass")==0){
                addBlock(grassBlocks,string2);
            }
            else if (string1.compareTo("water")==0){
                addBlock(waterBlocks,string2);
            }
            else if (string1.compareTo("stone")==0){
                addBlock(stoneBlocks,string2);
            }


        }
    }
    private void addBlock(ArrayList<Point> arrayList, String string){
        String[] line = string.split(",");
        int x,y;
        x = Integer.parseInt(line[0]);
        y= Integer.parseInt(line[1]);
        arrayList.add(new Point(x,y));

    }
    public Map(){
        aTankTimeCount = 30;
        aTankTime = 0;
        pRECount =10;
        aCount = 10;
        brickBlocks = new ArrayList<Point>();
        waterBlocks = new ArrayList<Point>();
        stoneBlocks = new ArrayList<Point>();
        grassBlocks = new ArrayList<Point>();
        iniDate();
    }
    public void iniDate(){
        blocks.clear();
        for (int i = 0;i<brickBlocks.size();i++) {
            blocks.add(new BlockBrick(brickBlocks.get(i).x,brickBlocks.get(i).y));
        }
        for (int i = 0;i<arrFrame.length;i++) {
            blocks.add(new BlockFrame(arrFrame[i][0],arrFrame[i][1],arrFrame[i][2],arrFrame[i][3]));
        }
        for (int i = 0;i<grassBlocks.size();i++) {
            blocks.add(new BlockGrass(grassBlocks.get(i).x,grassBlocks.get(i).y));
        }
        for (int i = 0;i<stoneBlocks.size();i++) {
            blocks.add(new BlockStone(stoneBlocks.get(i).x,stoneBlocks.get(i).y));
        }
        for (int i = 0;i<waterBlocks.size();i++) {
            blocks.add(new BlockWater(waterBlocks.get(i).x,waterBlocks.get(i).y));
        }
    }
    public void draw(Graphics graphics){
       for (int i = blocks.size()-1;i>=0;i--){
           Block temp = blocks.get(i);
           temp.draw(graphics);
       }
    }
    //tank地图碰撞检测
    public boolean isCollide(Tank tank){
        boolean result = false;
        int trigger = 0;
        for (int i = blocks.size()-1;i>=0;i--){
            Block temp = blocks.get(i);
            if ((temp instanceof BlockBrick)||(temp instanceof BlockStone)||(temp instanceof BlockFrame)){
                if (tank.isConlide(temp)){
                    result = true;
                }
            }
            }
        return result;
    }
    public boolean isCollide(Bullet bullet){
        boolean result = false;
        for (int i = blocks.size()-1;i>=0;i--){
            Block temp = blocks.get(i);
            if (temp instanceof BlockBrick){
                if (bullet.isCollide(temp)) {
                        blocks.remove(temp);
                        result = true;
                }
            }
            if ((temp instanceof BlockStone)||(temp instanceof BlockFrame)){
                if (bullet.isCollide(temp)){
                    result = true;
                }
            }
        }
        return  result;

    }
    public AItank createTank(){
        AItank aItank = null;
        aTankTime++;
        if (aTankTime>=aTankTimeCount){
            Random random = new Random();
            int pos = random.nextInt(aTankPos.length);
            aItank = new AItank(aTankPos[pos][0],aTankPos[pos][1]);
            aItank.setDirection(aTankPos[pos][2]);
            aTankTime = 0;
        }
        return aItank;
    }
    public void iniPtankCartoon(Cartoon cartoon,int pos){
        cartoon.setX(pTankPos[pos][0]);
        cartoon.setY(pTankPos[pos][1]);
    }
    public void iniPtankData(playertank playertank,int pos){
        playertank.setX(pTankPos[pos][0]);
        playertank.sety(pTankPos[pos][1]);
        playertank.setDirection(pTankPos[pos][2]);
    }
    public void setaTankTimeCount(int aTankTimeCount) {
        this.aTankTimeCount = aTankTimeCount;
    }
    public int getaCount() {
        return aCount;
    }
    public int getpRECount() {
        return pRECount;
    }
    public boolean waterIsConllide(Tank tank){
        boolean result = false;
        for (int i = blocks.size()-1;i>=0;i--){
            Block temp = blocks.get(i);
            if (temp instanceof BlockWater){
                if (tank.isConlide(temp)){
                    result = true;
                }
            }
        }
        return result;
    }

    public void setaCount(int aCount) {
        this.aCount = aCount;
    }

    public void setpRECount(int pRECount) {
        this.pRECount = pRECount;
    }
}
