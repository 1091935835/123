import java.sql.SQLException;

public class SaleItem {
    private int qty;
    private Product pd;
    private StateUse a;


    public SaleItem(Product desc, int qty) {
        this.qty = qty;
        this.pd = desc;
    }
    public float getTotal() {
        return qty * pd.getPrice();
    }
    public int getqty() {
        return qty;
    }
    public Product getPd() {
        return pd;
    }
    public void use() throws SQLException {
        a.start();
        a.enter("1",1);
        a.Pay(1);
        a.close();
    }
}
