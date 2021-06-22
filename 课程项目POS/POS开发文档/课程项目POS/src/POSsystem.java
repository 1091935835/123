import java.sql.SQLException;

public class POSsystem {
    private static POSsystem posSystem = null;
    private Sale curSale;
    private ProductCatalog catalog;
    private int InterruptFlag = 1;
    private int curState = 0;
    private ReceiptElement RE = new ReceiptElement();
    public ReceiptElement getRE() {
        return RE;
    }
    public Sale getCurSale() {
        return curSale;
    }
    public static POSsystem getInstance() {
        if (posSystem == null) {
            posSystem = new POSsystem();
            posSystem.startUp();
        }
        return posSystem;
    }
    public void startUp() {
        catalog = new ProductCatalog();//加载产品目录
    }
    public void makeNewSale() {
        curSale = new Sale();
    }
    public void enterItem(String id, int qty) throws SQLException {
        Product desc = catalog.getProductDesc(id);
        curSale.makeLineItem(desc, qty);
        DB.addDB(desc,qty);
    }
    public void endPurchase() {
        curSale.beComplete();
    }
    public void makePayment(float cash) {
        curSale.makePayment(cash);
    }
    public void finishASale() {
        curSale.finishASale(RE);
    }
    public void run() throws SQLException {
        startUp();
        cmdPanel();
    }
    public void cmdPanel() throws SQLException {
        while (InterruptFlag==1) {
            switch (curState) {
                case 0:
                    UI.show("要开始一次新的销售吗？\ny:是\nn:否 \n");
                    String option = UI.getReturn();
                    if (option.charAt(0) == 'y') {
                        makeNewSale();
                        UI.show("ID  名称 价格\n");
                        catalog.showCatalog();
                        curState = 1;
                    }
                    else {
                        InterruptFlag = 0;
                    }
                    break;
                case 1:
                    UI.show("继续加购商品吗？\ny:是\nn:否\n");
                    while(UI.getReturn().charAt(0) == 'y') {
                        UI.show("请输入商品ID: ");
                        String ID = UI.getID();
                        UI.show("请输入商品数量: ");
                        int qty = UI.getqty();
                        enterItem(ID, qty);
                        UI.show("继续加购商品吗？\ny:是\nn:否 \n");
                    }
                    endPurchase();
                    curState = 2;
                    break;
                case 2:
                    float tot = curSale.getTotal();
                    UI.show("应付款:", tot);
                    UI.show("实付额: ");
                    makePayment(UI.getCash());
                    UI.show("找零: ", curSale.getBalance());
                    finishASale();
                    UI.show("打印单据......\n");
                    curState = 0;
                    break;
            }
        }
        UI.show("感谢使用\n");
    }
    public static void main(String[] args) throws SQLException {
        POSsystem pos = new POSsystem();
        pos.run();
    }

}

