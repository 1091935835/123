
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class ProductCatalog {
    private Map<String, Product> descriptions = new HashMap <String, Product>();

    public Product getProductDesc(String id) {
        return descriptions.get(id);
    }

    public ProductCatalog() {
        loadCatalog();
    }
    public void showCatalog() {
        for (Entry<String, Product> entry : descriptions.entrySet()) {
            UI.show(entry.getKey() + " ");
            Product description = entry.getValue();
            UI.show(description.getName() + " " + description.getPrice() + "\n");
        }
    }
    private void loadCatalog() {
        //加载商品信息项
        String id1 = new String("1");
        Product desc1
                = new Product(id1, "辣条", 1.5f);
        descriptions.put(id1, desc1);

        String id2 = new String("2");
        Product desc2
                = new Product(id2, "面包", 3.0f);
        descriptions.put(id2, desc2);

        String id3 = new String("3");
        Product desc3
                = new Product(id3, "雪碧", 3.0f);
        descriptions.put(id3, desc3);

        String id4 = new String("4");
        Product desc4
                = new Product(id4, "可乐", 2.5f);
        descriptions.put(id4, desc4);

        String id5 = new String("5");
        Product desc5
                = new Product(id5, "牛奶", 3.0f);
        descriptions.put(id5, desc5);

        String id6 = new String("6");
        Product desc6
                = new Product(id6, "花生", 2.0f);
        descriptions.put(id6, desc6);
    }

}
