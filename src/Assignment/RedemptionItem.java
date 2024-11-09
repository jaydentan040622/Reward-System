
package Assignment;

public class RedemptionItem {

    private String name;
    private int redemptionValue;

    public RedemptionItem() {
    }

    public RedemptionItem(String name, int redemptionValue) {
        this.name = name;
        this.redemptionValue = redemptionValue;
    }

    public String getName() {
        return name;
    }

    public int getRedemptionValue() {
        return redemptionValue;
    }
}
