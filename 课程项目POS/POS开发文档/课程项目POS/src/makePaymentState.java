import java.sql.SQLException;

public class makePaymentState implements State {
    private static POSsystem posSystem = POSsystem.getInstance();
    private Sale curSale;
    private ProductCatalog catalog;
    @Override
    public void makeNewSale() {

    }

    @Override
    public void enterItem(String id, int qty) throws SQLException {

    }

    @Override
    public void endPurchase() {

    }

    @Override
    public void makePayment(float cash) {
        curSale.makePayment(cash);
    }

    @Override
    public void setPossystem(POSsystem possystem) {
        this.posSystem = possystem;
    }
}
