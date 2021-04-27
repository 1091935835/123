import com.sun.source.doctree.AttributeTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private gamePanel gamePanel1;
    private LoginPanel loginPanel1;
    private OverPanel overPanel1;
    private WinPanel winPanel1;
    private gamePanelHard gamePanelHard;
    private HelpPanel helpPanel;
    public void startGame(){
        this.setContentPane(gamePanel1);
        this.addKeyListener(gamePanel1);
        gamePanel1.iniData();
        this.revalidate();
    }
    public void startGameHard(){
        this.setContentPane(gamePanelHard);
        this.addKeyListener(gamePanelHard);
        gamePanelHard.iniData();
        this.revalidate();
    }
    public void gameWIn(){
        this.setContentPane(winPanel1);
        this.addKeyListener(winPanel1);
        this.revalidate();
    }
    public void gameOver(){
        this.setContentPane(overPanel1);
        this.addKeyListener(overPanel1);
        this.revalidate();
    }
    public void login(){
        this.setContentPane(loginPanel1);
        this.addKeyListener(loginPanel1);
        this.revalidate();
    }
    public void help(){
        this.setContentPane(helpPanel);
        this.revalidate();
    }
    public MainFrame(String string) throws IOException {
        super("坦克大战");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu startMenu = new JMenu("开始");
        startMenu.setMnemonic('s');
        menuBar.add(startMenu);

        JMenuItem newItem = new JMenuItem("新游戏");
        newItem.setAccelerator(KeyStroke.getKeyStroke('n'));
        newItem.addActionListener(new startListener());
        startMenu.add(newItem);

        JMenu chooseItem = new JMenu("选择关卡");
        startMenu.add(chooseItem);

        JMenuItem nextItem = new JMenuItem("下一关");
        nextItem.addActionListener(new nextListener());
        chooseItem.add(nextItem);

        JMenuItem lastItem = new JMenuItem("上一关");
        lastItem.addActionListener(new lastListener());
        chooseItem.add(lastItem);

        startMenu.addSeparator();

        JMenuItem pauseItem = new JMenuItem("暂停游戏");
        pauseItem.addActionListener(new PauseListener());
        startMenu.add(pauseItem);

        JMenuItem resumeItem = new JMenuItem("恢复游戏");
        resumeItem.addActionListener(new ResumeListener());
        startMenu.add(resumeItem);

        JMenu helpMenu = new JMenu("帮助");
        helpMenu.setMnemonic('H');
        menuBar.add(helpMenu);

        JMenuItem manualItem = new JMenuItem("操作说明");
        manualItem.addActionListener(new helpListener());
        helpMenu.add(manualItem);

        helpMenu.addSeparator();

        JMenuItem aboutItem = new JMenuItem("关于...");
        aboutItem.addActionListener(new AboutLisener());
        helpMenu.add(aboutItem);


        JMenu modeMenu = new JMenu("以...模式开始");
        menuBar.add(startMenu);

        JMenuItem normalItem = new JMenuItem("普通");
        normalItem.addActionListener(new NormalListener());
        modeMenu.add(normalItem);
        modeMenu.addSeparator();

        JMenuItem hardItem = new JMenuItem("困难");
        hardItem.addActionListener(new HardListener());
        modeMenu.add(hardItem);

        menuBar.add(modeMenu);
        Insets insets = getInsets();
        int x,y,tempW,tempH;
        tempH = HEIGHT+insets.top+insets.bottom;
        tempW = WIDTH +insets.left+insets.right;
        x = (Toolkit.getDefaultToolkit().getScreenSize().width-tempW)/2;
        y = (Toolkit.getDefaultToolkit().getScreenSize().height-tempH)/2;
        setBounds(x,y,tempW,tempH);
        this.loginPanel1 = new LoginPanel(this);
        this.gamePanel1 = new gamePanel(this);
        this.winPanel1 = new WinPanel(this);
        this.overPanel1 = new OverPanel(this);
        this.gamePanelHard = new gamePanelHard(this);
        this.helpPanel = new HelpPanel();
        login();

    }
    public void nextgame(){
        this.setContentPane(gamePanel1);
        this.addKeyListener(gamePanel1);
        gamePanel1.setGameLevel(gamePanel1.getGameLevel()+1);
        gamePanel1.iniData();
        this.revalidate();

    }
    private class AboutLisener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AboutFrame();
        }
    }
    private class NormalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            startGame();
        }
    }
    private class HardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            startGameHard();
        }
    }
    private class PauseListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            gamePanel1.setState(gamePanel.FROZEN);
            gamePanelHard.setState(gamePanel.FROZEN);
        }
    }
    private class ResumeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            gamePanel1.setState(gamePanel.ACTIVE);
            gamePanelHard.setState(gamePanel.ACTIVE);
        }
    }
    private class startListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent){
            gamePanel1.setGameLevel(-1);
            startGame();
        }
    }
    private class helpListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            help();
        }
    }
    private class nextListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (gamePanel1.getGameLevel()<=gamePanel1.getStringLevel().size()){
                gamePanel1.setGameLevel(gamePanel1.getGameLevel()+1);
                startGame();
            }
            else{
                gameOver();
                gamePanel1.setGameLevel(-1);
            }
        }
    }
    private class lastListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (gamePanel1.getGameLevel()>=0){
                gamePanel1.setGameLevel(gamePanel1.getGameLevel()-1);
                startGame();
            }
            else{
                gamePanel1.setGameLevel(-1);
            }
        }
    }
}
