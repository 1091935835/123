package MapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.ArrayList;

public class PanelMain extends JPanel implements MouseMotionListener, MouseListener {
    private FrameMain frameMain;
    private int pTankCount = 5;
    private int sTankCount = 1;
    private int tsTankTimeCount=100;
    private ArrayList<EltAtank> eltAtanks = new ArrayList<EltAtank>();
    private ArrayList<EltPtank> eltptanks = new ArrayList<EltPtank>();
    private ArrayList<Element> elements = new ArrayList<Element>();

    public PanelMain(FrameMain frameMain){
        this.frameMain = frameMain;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public void paint(Graphics graphics){
        super.paint(graphics);
        //graphics.drawString("这里是地图编辑面板",200,200);
        graphics.fillRect(0,0,800,600);
        graphics.setColor(Color.GRAY);

        Operation.getSingletonInstance().draw(graphics);
        for (int i =0;i<elements.size();i++){
            elements.get(i).draw(graphics);
        }
        for (int i =0;i<eltAtanks.size();i++){
            eltAtanks.get(i).draw(graphics);
        }
        for (int i =0;i<eltptanks.size();i++){
            eltptanks.get(i).draw(graphics);
        }
        for (int i = 1;i<=800/34;i++){
            graphics.drawLine(i*34,0,i*34,600);
        }
        for (int i = 1;i<=600/34;i++){
            graphics.drawLine(0,i*34,800,i*34);
        }
    }
    public void saveMap(File file)throws IOException{
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter,true);

        printWriter.println("pTankCount=5");
        printWriter.println("sTankCount=1");
        printWriter.println("sTankTimeCount=100");
        for (int i = 0;i<eltptanks.size();i++){
            printWriter.println(eltptanks.get(i).toString());
        }
        for (int i = 0;i<2-eltptanks.size();i++) {
            printWriter.println("pTankPos=0,0,0");
        }
        for (int i = 0;i<eltAtanks.size();i++){
            printWriter.println(eltAtanks.get(i).toString());
        }
        for (int i = 0;i<2-eltAtanks.size();i++) {
            printWriter.println("pTankPos=0,0,0");
        }
        for (int i = 0;i<elements.size();i++){
            printWriter.println(elements.get(i).toString());
        }

        printWriter.close();
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Operation.getSingletonInstance().setX(mouseEvent.getX());
        Operation.getSingletonInstance().setY(mouseEvent.getY());
        frameMain.repaint();

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Element elt;
        elt = Operation.getSingletonInstance().getElement();
        if (elt!=null){
            if (elt instanceof EltPtank){
                EltPtank addElt = (EltPtank)elt.clone();
                addElt.x = mouseEvent.getX()/34*34;
                addElt.y = mouseEvent.getY()/34*34;
                eltptanks.add(addElt);
            }
            else if (elt instanceof EltAtank){
                EltAtank addElt = (EltAtank)elt.clone();
                addElt.x = mouseEvent.getX()/34*34;
                addElt.y = mouseEvent.getY()/34*34;
                eltAtanks.add(addElt);
            }
            else if (elt instanceof EltSpade){
                int tempx,tempy;
                tempx = mouseEvent.getX()/34*34;
                tempy = mouseEvent.getY()/34*34;
                for (int i = elements.size()-1;i>=0;i--){
                    Element deleteElt = elements.get(i);
                    if (deleteElt.x==tempx&&deleteElt.y==tempy){
                        elements.remove(i);
                        break;
                    }
                }
            }
            else {
                Element addElt = elt.clone();
                addElt.x = mouseEvent.getX()/34*34;
                addElt.y = mouseEvent.getY()/34*34;
               elements.add(addElt);
            }
            frameMain.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) { }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }
    @Override
    public void mouseExited(MouseEvent mouseEvent) { }
}
