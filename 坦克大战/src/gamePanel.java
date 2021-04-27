import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.http.WebSocket;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Random;

public class gamePanel extends JPanel implements KeyListener {
    String string1,string2,line[];
    public static final int FROZEN = 0;
    public static final int ACTIVE = 1;
    private int state = 1;
    protected MainFrame mainFrame;
    protected Image imgOffScreen;
    protected Graphics gOffScrenn;
    //以下为游戏数据
    protected playertank playertank = null;
    protected ArrayList<AItank> AItanks = new ArrayList<AItank>();
    protected ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();//储存玩家炮弹
    protected ArrayList<Bullet> AIBullets = new ArrayList<Bullet>();//存储AI炮弹
    protected ArrayList<Cartoon> cartoons = new ArrayList<Cartoon>();//管理所有动画
    protected int[][] hotPs = {{100, 100}, {300, 100}, {550, 100}};
    protected Map map;
    protected Thread tankThread;
    protected int aCreate;
    protected int aDestroy;
    protected int pREnumber;
    protected ArrayList<String>StringLevel = new ArrayList<String>();// = {"map1.txt","map2.txt","map3.txt"};
    protected int gameLevel = -1;

    public gamePanel(MainFrame mainFrame) throws IOException {
        super();
        try {
            FileReader fileReader = new FileReader("config.tank");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String string;
            while ( (string = bufferedReader.readLine()) != null) {
                StringLevel.add(string);
            }
            bufferedReader.close();
            fileReader.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        this.mainFrame = mainFrame;
        aCreate = 0;
        aDestroy = 0;
        pREnumber = 0;
        map = new Map();
        playertank = new playertank(0, 0);
        playertank.setCatagory(4);
        map.iniPtankData(playertank, Map.POS1);
        tankThread = new Thread(new myThread());
        tankThread.start();
        setGameLevel(0);
    }
    private class myThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; ) {
                try {
                    Thread.sleep(100);
                    caculateData();
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void iniData(){
        aDestroy = 0;
        aCreate = 0;
        map.iniDate();
        map.iniPtankData(playertank,Map.POS1);
        AIBullets.clear();
        playerBullets.clear();
        AItanks.clear();
    }
    public void caculateData() {
        //处理动画
        for (int i = cartoons.size() - 1; i >= 0; i--) {
            Cartoon cartoon = cartoons.get(i);
            cartoon.caculateData();
            if (cartoon.isDied()) {
                cartoons.remove(i);
            }
        }
        //处理玩家坦克
        {
            //没碰边框，没碰硬砖
            if ((!map.isCollide(playertank)) && (state == ACTIVE)) {
                playertank.move();
            }
            playertank.caculateData();
        }
        //生成ai坦克
        {
            if (aCreate < map.getaCount()) {
                AItank aItanktemp = map.createTank();
                if (aItanktemp != null) {
                    AItanks.add(aItanktemp);
                    aCreate++;
                }
            }
        }
        //处理AI坦克
        for (int i = AItanks.size() - 1; i >= 0; i--) {
            AItank tempAItank = AItanks.get(i);
            switch (tempAItank.getState()) {
                case Spirit.ALIVE:
                    if ((!map.isCollide(tempAItank)) && (state == ACTIVE)) {
                        tempAItank.move();
                    }
                    if (tempAItank.isCollide(playertank)) {
                        Cartoon cartoon = new Cartoon(Cartoon.TEXPLOSION, tempAItank.getX(), tempAItank.getY());
                        cartoon.addFinishLitener(new Listener1());
                        cartoons.add(cartoon);
                        AItanks.remove(i);
                        playertank.sety(-1000);

                        continue;//TODO
                    }
                    if (!(map.waterIsConllide(tempAItank))) {
                        Bullet aibullet = tempAItank.fire();
                        if (aibullet != null) {
                            AIBullets.add(aibullet);
                        }
                    }
                    tempAItank.caculateData();
            }
        }
        //处理玩家炮弹
        for (int i = playerBullets.size() - 1; i >= 0; i--) {
            if (state == ACTIVE) {
                Bullet playerbullet = playerBullets.get(i);
                if (map.isCollide(playerbullet)) {
                    cartoons.add(new Cartoon(Cartoon.BEXPLOSION, playerbullet.getX(), playerbullet.getY()));
                    playerBullets.remove(playerbullet);
                    continue;
                }//炮弹与边框撞
                playerbullet.move();
                playerbullet.caculateData();
                for (int j = AItanks.size() - 1; j >= 0; j--) {
                    AItank aItank = AItanks.get(j);
                    if (aItank.isCollide(playerbullet)) {
                        cartoons.add(new Cartoon(Cartoon.BEXPLOSION, playerbullet.getX(), playerbullet.getY()));
                        cartoons.add(new Cartoon(Cartoon.TEXPLOSION, aItank.getX(), aItank.getY()));
                        AItanks.remove(j);
                        aDestroy++;
                        if (aDestroy >= map.getaCount()) {
                            mainFrame.gameWIn();
                            //TODO:win
                        }
                        playerBullets.remove(i);
                        playerbullet = null;
                        continue;
                    }

                }
                if (playerbullet != null) {//炮弹与炮弹撞
                    for (int z = AIBullets.size() - 1; z >= 0; z--) {
                        Bullet tempBullet = AIBullets.get(z);
                        if (tempBullet.isCollide(playerbullet)) {
                            cartoons.add(new Cartoon(Cartoon.BEXPLOSION, tempBullet.getX(), tempBullet.getY()));
                            AIBullets.remove(z);
                            playerBullets.remove(i);
                            playerbullet = null;
                            break;
                        }
                    }

                }
                if (state == FROZEN) {
                    playerBullets.clear();
                    break;//炮弹与坦克撞
                }

            }
        }
        //处理精灵炮弹
        for (int i = AIBullets.size() - 1; i >= 0; i--) {
            if (state == ACTIVE) {
                Bullet aiBUllet = AIBullets.get(i);
                if (map.isCollide(aiBUllet)) {
                    cartoons.add(new Cartoon(Cartoon.BEXPLOSION, aiBUllet.getX(), aiBUllet.getY()));
                    playerBullets.remove(aiBUllet);
                    continue;
                }//处理ai炮弹碰到边框
                aiBUllet.move();
                aiBUllet.caculateData();
                if (playertank.isCollide(aiBUllet)) {
                    cartoons.add(new Cartoon(Cartoon.BEXPLOSION, aiBUllet.getX(), aiBUllet.getY()));
                    Cartoon cartoon = new Cartoon(Cartoon.TEXPLOSION, playertank.getX(), playertank.getY());
                    cartoon.addFinishLitener(new Listener1());
                    cartoons.add(cartoon);
                    AIBullets.remove(i);
                    playertank.sety(-1000);
                }
                if (state == FROZEN) {
                    playerBullets.clear();
                    break;//炮弹与坦克撞
                }
            }
        }
    }
    public void paint(Graphics g) {
        if (imgOffScreen == null) {
            imgOffScreen = this.createImage(MainFrame.WIDTH, MainFrame.HEIGHT);
            gOffScrenn = imgOffScreen.getGraphics();
        }
        //双缓存绘制图象
        {
        super.paint(gOffScrenn);
        Color c = gOffScrenn.getColor();
        gOffScrenn.setColor(Color.BLACK);
        gOffScrenn.fillRect(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
        gOffScrenn.setColor(c);
    }
        map.draw(gOffScrenn);
        //画玩家坦克
        playertank.draw(gOffScrenn);
        //生成动画
        for (int i = cartoons.size()-1;i>=0;i--){
                cartoons.get(i).draw(gOffScrenn);

        }
        //绘制热点图象
        for (int i = 0; i < hotPs.length; i++) {
            imageUtil.getSingletonInstance().drawMapBlock(gOffScrenn, hotPs[i][0], hotPs[i][1], imageUtil.GRASS);
        }
        //画AI坦克
        for (int i = AItanks.size() - 1; i >= 0; i--) {
            AItanks.get(i).draw(gOffScrenn);

        }
        //处理玩家发射炮弹
        for (int i = playerBullets.size() - 1; i >= 0; i--) {
            playerBullets.get(i).draw(gOffScrenn);

        }
        //处理精灵炮弹
         for (int i = AIBullets.size() - 1; i >= 0; i--) {
            AIBullets.get(i).draw(gOffScrenn);
            }
        g.drawImage(imgOffScreen, 0, 0, null);
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        playertank.keyPressed(keyEvent);
        int key = keyEvent.getKeyCode();
        switch (key) {
            case KeyEvent.VK_SPACE:
                if (!(map.waterIsConllide(playertank))) {
                    playerBullets.add(playertank.fire());
                }
                break;
            case KeyEvent.VK_ESCAPE:
                mainFrame.removeKeyListener(this);
                mainFrame.login();
                break;


        }
    }
    private class Listener1 extends FinishListener{
        public void doFinish() {
            pREnumber++;
            if (pREnumber<map.getpRECount()) {
                Cartoon ct = new Cartoon(Cartoon.TRESPAWN, 0, 0);
                map.iniPtankCartoon(ct, Map.POS1);
                ct.addFinishLitener(new Listener2());
                cartoons.add(ct);
            }
            else{
                mainFrame.gameOver();
            }
        }
    }
    private class Listener2 extends FinishListener{
        @Override
        public void doFinish() {
            map.iniPtankData(playertank,Map.POS1);
            playertank.setDirection(Spirit.UP);
        }
    }
    public int getGameLevel() {
        return gameLevel;
    }
    public void setGameLevel(int gameLevel) {
        if (gameLevel >= 0 && gameLevel < StringLevel.size()) {
            if (this.gameLevel != gameLevel) {
                this.gameLevel = gameLevel;
                try {
                    map.readData(StringLevel.get(gameLevel));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            map.iniDate();
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<String> getStringLevel() {
        return StringLevel;
    }
}
