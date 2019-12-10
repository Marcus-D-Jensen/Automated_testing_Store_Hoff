package WebObjects;


public class ReceiptLine {
    private String productName;
    private int nrOfBoughtProducts;
    private double price;

    public ReceiptLine(String productName, int nrOfBoughtProducts, Long price) {
        this.productName = productName;
        this.nrOfBoughtProducts = nrOfBoughtProducts;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNrOfBoughtProducts() {
        return nrOfBoughtProducts;
    }

    public void setNrOfBoughtProducts(int nrOfBoughtProducts) {
        this.nrOfBoughtProducts = nrOfBoughtProducts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
