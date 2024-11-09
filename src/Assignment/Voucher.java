/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author quinton
 */
public class Voucher extends RedemptionItem{

    private int voucherValue;
    public Voucher(String name, int redemptionValue, int voucherValue) {
        super(name, redemptionValue);
        this.voucherValue = voucherValue;
    }

    public int getVoucherValue() {
        return voucherValue;
    }
    
}
