public class ReceiptElement {
    private String cashier;
    private String machine_num;
    private String order_num;
    private String member_num;
    private String phone;
    public ReceiptElement(){
        this.cashier = null;
        this.machine_num = null;
        this.member_num = null;
        this.order_num = null;
        this.phone = null;
    }
    public ReceiptElement(String cashier,
            String machine_num,
            String order_num,
            String member_num,
            String phone){
        this.cashier = cashier;
        this.machine_num = machine_num;
        this.member_num = member_num;
        this.order_num = order_num;
        this.phone = phone;
    }
    public String getCashier() {
        return cashier;
    }
    public String getMachine_num() {
        return machine_num;
    }
    public String getMember_num() {
        return member_num;
    }
    public String getOrder_num() {
        return order_num;
    }
    public String getPhone() {
        return phone;
    }
    public void setCashier(String cashier) {
        this.cashier = cashier;
    }
    public void setMachine_num(String machine_num) {
        this.machine_num = machine_num;
    }
    public void setMember_num(String member_num) {
        this.member_num = member_num;
    }
    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
