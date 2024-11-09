
package Assignment;


public class Product extends RedemptionItem{
   
    private String brand;
    
    public Product() {
    }

    
    public Product(String name, int redemptionValue, String brand) {
        super(name,redemptionValue);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    
    
}
