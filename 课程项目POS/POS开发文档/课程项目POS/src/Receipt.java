

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    private String str;
    private Date date = new Date(System.currentTimeMillis());
    private SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    public void print(Sale sale,ReceiptElement RE){
        transToString(sale,RE);
        writeToTxt();
        System.out.println(str);
    }
    private void transToString(Sale sale,ReceiptElement RE) {
        List<SaleItem> lineItems = sale.getSaleLineItems();
        str =  "永丰超市欢迎您的光顾\n\n";
        str += "收银员:" + RE.getCashier() + "\n";
        str += "机号:" + RE.getMachine_num() + "\n";
        str += "订单号:" + RE.getOrder_num() + "\n";
        str += "交易时间:";
        str += formatter.format(date) + "\n";
        str += "\nID  名称 单价 数量 价格\n";
        for(SaleItem sli:lineItems)   {
            str += sli.getPd().getID() + "  " + sli.getPd().getName() + "  "
                    + sli.getPd().getPrice() + "  " + sli.getqty() + "  " +
                    sli.getPd().getPrice() * sli.getqty() + "\n";
        }
        str += "总计\t" + sale.getTotal() + "\n";
        str += "\n应付款: " + sale.getTotal() + "\n";
        str += "实付款: " + sale.getPay().getCash() + "\n";
        str += "找零:" + sale.getBalance() + "\n\n";
        str += "会员卡号:" + RE.getMember_num() + "\n";
        str += "联系电话:" + RE.getPhone();
    }
    private void writeToTxt() {
        String filePath = "D:\\大学\\大二下\\软件工程导论\\课程项目POS\\课程项目POS\\" + formatter.format(date) +"record.txt";
        FileWriter fileWriter = null;
        try {
        fileWriter  = new FileWriter(filePath);
        fileWriter.write(str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
