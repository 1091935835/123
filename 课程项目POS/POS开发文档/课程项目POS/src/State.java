import java.sql.SQLException;

public interface State {
    public void makeNewSale();
    public void enterItem(String id, int qty) throws SQLException;
    public void endPurchase();
    public void makePayment(float cash);
    public void setPossystem(POSsystem possystem);
}
