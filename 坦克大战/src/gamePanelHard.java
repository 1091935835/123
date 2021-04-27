import java.io.IOException;

public class gamePanelHard extends gamePanel {
    public gamePanelHard(MainFrame mainFrame) throws IOException {
        super(mainFrame);
        Change();
    }
    public void Change(){
        for (int i = AItanks.size()-1;i>=0; i--){
            AItank tempT = AItanks.get(i);
            tempT.setSPEED(30);
            tempT.setStepNumber(5);
        }
        for (int i = AIBullets.size()-1;i>=0;i--){
            AIBullets.get(i).setSPEED(25);
        }
        map.setaTankTimeCount(10);
        map.setaCount(1000000);
        map.setpRECount(0);
    }
}
