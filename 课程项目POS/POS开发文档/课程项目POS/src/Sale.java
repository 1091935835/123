
import java.util.List;
import java.util.ArrayList;

public class Sale {
    private List<SaleItem> lineItems = new ArrayList<SaleItem>();
    private boolean isComplete = false;
    private Pay pay;
    private ReceiptElement re = new ReceiptElement();
    public void makeLineItem(Product desc, int qty) {
        lineItems.add(new SaleItem( desc, qty));
    }
    public void beComplete(){
        isComplete = true;
    }
    public float getTotal(){
        float sumUp = 0;
        for(SaleItem sli:lineItems)   {
            sumUp +=sli.getTotal();
        }
        return sumUp;
    }
    public void finishASale(ReceiptElement RE){
        Receipt receipt = new Receipt();
        receipt.print(this,RE);
    }
    public  void makePayment(float cash){
        pay = new Pay(cash);
    }
    public float getBalance() {
        return pay.getCash() - getTotal();
    }
    public List<SaleItem> getSaleLineItems() {
        return lineItems;
    }
    public Pay getPay() {
        return pay;
    }
}
