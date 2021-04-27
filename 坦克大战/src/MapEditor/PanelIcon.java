package MapEditor;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PanelIcon extends JPanel implements MouseListener {
    private FrameMain frameMain;
    private ArrayList<Element>elements = new ArrayList<Element>();
    private Element eltSelected;
    public PanelIcon(FrameMain frameMain){
        this.frameMain = frameMain;
        eltSelected = null;

        elements.add(new EltAtank(10,150,0));
        elements.add(new EltAtank(50,150,1));
        elements.add(new EltAtank(10,200,2));
        elements.add(new EltAtank(50,200,3));


        elements.add(new EltPtank(10,50,0));
        elements.add(new EltPtank(50,50,1));
        elements.add(new EltPtank(10,100,2));
        elements.add(new EltPtank(50,100,3));

        elements.add(new EltGrass(10,250));
        elements.add(new EltGrass(50,250));
        elements.add(new EltGrass(10,300));
        elements.add(new EltGrass(50,300));
        elements.add(new EltSpade(10,350));
        this.addMouseListener(this);

    }
    public void iniPanel(){
        setBorder(new EtchedBorder());
        JLabel label = new JLabel("地图元素坐标  ");
        add(label);
    }

    public void paint(Graphics graphics){
        for (int i =0;i<elements.size();i++){
            elements.get(i).draw(graphics);
        }
    }

    public Element getEltSelected() {
        return eltSelected;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
            Element elt = null;
        for (int i = 0;i<elements.size();i++){
                Element tempElt;
                tempElt= elements.get(i).click(mouseEvent.getX(),mouseEvent.getY());
                if (tempElt!=null){
                    elt = tempElt;
                }
        }
        Operation.getSingletonInstance().setElement(elt);
        frameMain.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
