package WebObjects;


import java.util.List;

public class Receipt {
    private List<ReceiptLine> receiptLines;
    private double totalPrice;

    public Receipt(List<ReceiptLine> receiptLines, double totalPrice) {
        this.receiptLines = receiptLines;
        this.totalPrice = totalPrice;
    }

    public List<ReceiptLine> getReceiptLines() {
        return receiptLines;
    }

    public void setReceiptLines(List<ReceiptLine> receiptLines) {
        this.receiptLines = receiptLines;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
