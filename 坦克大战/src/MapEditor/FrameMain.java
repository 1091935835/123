package MapEditor;

import javax.management.remote.JMXServiceURL;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.security.Key;

public class FrameMain extends JFrame {
    private PanelMain panelMain;
    public FrameMain(){
        this.setTitle("地图编辑器");

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        addFileMenu(menuBar);
        addEditMenu(menuBar);
        addHelpMenu(menuBar);
        addToolBar();

        setLayout(new BorderLayout());

        PanelIcon panelIcon = new PanelIcon(this);
         panelIcon.iniPanel();
         add(panelIcon,BorderLayout.WEST);

         panelMain = new PanelMain(this);
         panelMain.setBorder(new LineBorder(Color.BLUE));
         panelMain.setPreferredSize(new Dimension(800,600));

         JScrollPane jScrollPane = new JScrollPane(panelMain,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         add(jScrollPane);

         this.setSize(800,600);
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         Dimension dimension = toolkit.getScreenSize();
         this.setLocation((dimension.width-800)/2,(dimension.height-600)/2);
         setVisible(true);
         setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private void addToolBar(){
        JToolBar toolBar = new JToolBar("工具栏");
        JButton undoButton = new JButton(new ImageIcon("undo.png"));
        undoButton.setToolTipText("undo");
        JButton redoButton = new JButton(new ImageIcon("redo.png"));
        redoButton.setToolTipText("redo");
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        this.add(toolBar, BorderLayout.PAGE_START);
    }
    private void addEditMenu(JMenuBar jMenuBar){
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        jMenuBar.add(editMenu);

        JMenuItem undoItem = new JMenuItem("undo");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        editMenu.add(undoItem);

        JMenuItem redoItem = new JMenuItem("redo");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        editMenu.add(redoItem);




    }
    private void addFileMenu(JMenuBar jMenuBar){
        JMenuItem fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        jMenuBar.add(fileMenu);

        JMenuItem newItem = new JMenuItem("new");
        newItem.setAccelerator(KeyStroke.getKeyStroke('N'));
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
        saveItem.addActionListener(new SaveItemAct());
        fileMenu.add(saveItem);


    }
    private void addHelpMenu(JMenuBar jMenuBar){
        JMenu helpMenu = new JMenu("HELP");
        helpMenu.setMnemonic('H');
        jMenuBar.add(helpMenu);

         JMenuItem manualItem = new JMenuItem("Manual");
         manualItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK));
         helpMenu.add(manualItem);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        helpMenu.add(aboutItem);

    }
    private class SaveItemAct implements ActionListener{
        JFileChooser fileDialog = new JFileChooser();

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int state = fileDialog.showSaveDialog(null);
            if (state==JFileChooser.APPROVE_OPTION){
                try{
                    panelMain.saveMap(fileDialog.getSelectedFile().getAbsoluteFile());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(fileDialog.getSelectedFile().getAbsoluteFile());
            }
        }
    }
}
