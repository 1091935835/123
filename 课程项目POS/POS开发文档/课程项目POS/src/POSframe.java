

import javax.management.loading.PrivateClassLoader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class POSframe extends JFrame {
    private POSsystem posSystem;
    private static POSframe saleFrame = null;
    private ProductCatalog catalog = new ProductCatalog();
    private int Num_product = 0;
    private endPurchaseState eps;
    private enterItemState eis;
    private MakeNewSaleState mss;
    private makePaymentState mps;
    private StateUse su = new StateUse(mss);

    //该窗口容纳的窗口组件
    private JPanel panel = new JPanel();
    private JButton saleButton = new JButton("开始一次新的销售活动");
    private JButton addProduct = new JButton("加购");
    private JLabel productIDLabel = new JLabel("ID");
    private JLabel productNumLabel = new JLabel("数量");
    private JTextField productID = new JTextField();
    private JTextField productNum = new JTextField();

    private JTable curSaleTable = new JTable();
    private DefaultTableModel tableModel;
    private JScrollPane table;
    private String[][] tableVales;
    private JLabel productListLabel = new JLabel("购物清单");
    private JButton endAddProduct = new JButton("添加完成");

    private JLabel originalPayLabel = new JLabel("应付款:");
    private JLabel realPayLabel = new JLabel("实付款:");
    private JLabel changePayLabel = new JLabel("找零:");
    private JLabel originalPay = new JLabel();
    private JTextField realPay = new JTextField();
    private JLabel changePay = new JLabel();
    private JButton finishSale = new JButton("结算");
    private JButton finishAll = new JButton("完成");

    //单据可定制相关
    private String cashier = "123123";
    private String machine_num = "98";
    private String order_num = "123412125";
    private String member_num = "1019312";
    private String phone = "12345678909";

    //初始化销售窗口
    private POSframe() {
        super("永丰超市");
        posSystem = POSsystem.getInstance();
        panel.setLayout(null);
        saleButton.setBounds(100, 160, 160, 35);
        saleButton.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.add(saleButton);
        add(panel);
        setBounds(400, 400, 360, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 添加商品
        productIDLabel.setBounds(80, 300, 50, 25);
        productID.setBounds(120, 300, 120, 25);
        productNumLabel.setBounds(80, 330, 50, 25);
        productNum.setBounds(120, 330, 120, 25);
        addProduct.setBounds(80, 365, 100, 25);
        productListLabel.setBounds(140, 4, 80, 26);
        endAddProduct.setBounds(190, 365, 100, 25);
        endAddProduct.setBorder(BorderFactory.createRaisedBevelBorder());

        //结算
        originalPayLabel.setBounds(60, 300, 50, 25);
        originalPay.setBounds(105, 300, 120, 25);
        realPayLabel.setBounds(180, 300, 50, 25);
        realPay.setBounds(225, 300, 80, 25);
        changePayLabel.setBounds(60, 330, 50, 25);
        changePay.setBounds(105, 330, 120, 25);
        finishSale.setBounds(60, 365, 100, 25);
        finishSale.setBorder(BorderFactory.createRaisedBevelBorder());
        finishAll.setBounds(190, 365, 100, 25);
        finishAll.setBorder(BorderFactory.createRaisedBevelBorder());


        // 开始一次销售
        saleButton.addActionListener(new MakeNewSaleListener());
        // 添加商品
        addProduct.addActionListener(new EnterItemListener());
        // 结束添加商品
        endAddProduct.addActionListener(new EndPurchaseListener());
        // 结算
        finishSale.addActionListener(new finishSaleListener());
        // 完成
        finishAll.addActionListener(new finishAllListener());

    }

    //单例模式，返回实例
    public static POSframe getInstance() {
        if (saleFrame == null) {
            saleFrame = new POSframe();
        }
        return saleFrame;
    }
    class MakeNewSaleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            su.setState(new MakeNewSaleState());
            su.start();
            posSystem.makeNewSale();
            posSystem.getRE().setCashier(cashier);
            posSystem.getRE().setMachine_num(machine_num);
            posSystem.getRE().setMember_num(member_num);
            posSystem.getRE().setOrder_num(order_num);
            posSystem.getRE().setPhone(phone);
            panel.revalidate();
            panel.remove(saleButton);
            panel.add(addProduct);
            panel.add(endAddProduct);
            panel.add(productIDLabel);
            panel.add(productID);
            panel.add(productNumLabel);
            panel.add(productNum);
            panel.add(productListLabel);
            //表
            String[] columnNames = {"ID", "名称", "单价", "数量", "金额"};
            tableVales = new String[][] {}; //数据
            tableModel = new DefaultTableModel(tableVales, columnNames);
            curSaleTable = new JTable(tableModel);
            table = new JScrollPane(curSaleTable);
            table.setBounds(0, 30, 360, 250);
            panel.add(table, BorderLayout.CENTER);
            repaint();
        }
    }
    class EnterItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String id = productID.getText();
            int num = Integer.parseInt(productNum.getText());
            try {
                //su.setState(new enterItemState());
                //su.enter(id,num);
                posSystem.enterItem(id, num);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            //向表格中加数据
            Product desc = catalog.getProductDesc(id);
            if (Num_product != 0) {
                tableModel.removeRow(Num_product);
            }
            String []rowValues = {id, desc.getName(), String.valueOf(desc.getPrice()), String.valueOf(num), String.valueOf(num * desc.getPrice())};
            tableModel.addRow(rowValues);
            String []LastRow = {"总计", "", "", "", String.valueOf(posSystem.getCurSale().getTotal())};
            tableModel.addRow(LastRow);
            Num_product ++;
        }
    }
    class EndPurchaseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           // su.setState(new endPurchaseState());
           // su.close();
            posSystem.endPurchase();
            panel.revalidate();
            panel.removeAll();
            panel.add(table);
            panel.add(productListLabel);
            panel.add(originalPayLabel);
            panel.add(realPayLabel);
            panel.add(originalPay);
            panel.add(realPay);
            panel.add(changePayLabel);
            panel.add(changePay);
            panel.add(finishSale);
            panel.add(finishAll);
            originalPay.setText(String.valueOf(posSystem.getCurSale().getTotal()));
            repaint();
        }
    }
    class finishSaleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            float payment = Float.valueOf(realPay.getText());
            //su.setState(new makePaymentState());
           // su.Pay(payment);
            posSystem.makePayment(payment);
            changePay.setText(String.valueOf(posSystem.getCurSale().getBalance()));
            posSystem.finishASale();
        }
    }
    class finishAllListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Num_product = 0;
            panel.revalidate();
            panel.removeAll();
            panel.add(saleButton);
            repaint();
        }
    }

    public static void main(String[] args) {
        Frame frame = POSframe.getInstance();
        frame.setVisible(true);
    }
}
