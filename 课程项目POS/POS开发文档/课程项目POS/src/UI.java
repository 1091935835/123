
import java.util.Scanner;

public class UI {
    public static void show(String s) {
        System.out.print(s);
    }
    public static void show(String s, float f) {
        System.out.println(s + ' ' + f);
    }
    public static String getReturn() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        return s;
    }
    public static String getID() {
        Scanner in = new Scanner(System.in);
        String ID = in.nextLine();
        return ID;
    }
    public static int getqty() {
        Scanner in = new Scanner(System.in);
        int qty = in.nextInt();
        return qty;
    }
    public static float getCash() {
        Scanner in = new Scanner(System.in);
        float cash = in.nextFloat();
        return cash;
    }
}
