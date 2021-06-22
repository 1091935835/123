import java.sql.SQLException;

public class StateUse {
    private State State;
    public StateUse(State State){
        this.State = State;
    }

    public void setState(State state) {
        State = state;
    }

    public State getState() {
        return State;
    }

    public void start(){
        getState().makeNewSale();
    }

    public void close(){
        getState().endPurchase();
    }
    public void enter(String ID,int qty) throws SQLException {
        getState().enterItem(ID,qty);
    }

    public void Pay(float cash){
        getState().makePayment(cash);
    }
}
