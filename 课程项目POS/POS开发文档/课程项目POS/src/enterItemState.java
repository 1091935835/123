import java.sql.SQLException;

public class enterItemState implements State{
    private  POSsystem posSystem = POSsystem.getInstance();
    private Sale curSale;
    private ProductCatalog catalog;
    @Override
    public void makeNewSale() {

    }

    @Override
    public void enterItem(String id, int qty) throws SQLException {
        Product desc = catalog.getProductDesc(id);
        curSale.makeLineItem(desc, qty);
        DB.addDB(desc,qty);
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
