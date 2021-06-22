import java.sql.SQLException;

public class MakeNewSaleState implements State {
    private  POSsystem posSystem = POSsystem.getInstance();
    private Sale curSale;
    private ProductCatalog catalog;
    @Override
    public void makeNewSale() {
        posSystem.makeNewSale();
    }

    @Override
    public void enterItem(String id, int qty) throws SQLException {

    }

    @Override
    public void endPurchase() {

    }

    @Override
    public void makePayment(float cash) {

    }
    public void setPossystem(POSsystem possystem) {
        this.posSystem = possystem;
    }
}
