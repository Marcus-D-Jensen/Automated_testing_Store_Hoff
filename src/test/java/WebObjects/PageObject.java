package WebObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class PageObject {

    private WebDriver driver;


    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    public double getRemainingMoney() {
        return Double.valueOf(driver.findElement(By.id("money")).getText());
    }

    public String getMessage() {
        return driver.findElement(By.id("message")).getText();
    }

    public void selectProductInDropdown(Product product) {
        Select select = new Select(driver.findElement(By.id("select-product")));
        select.selectByVisibleText(product.getName());
    }

    public void setAmountOfProductsToBuy(int amount) {
        driver.findElement(By.id("buyAmount")).sendKeys(String.valueOf(amount));
    }

    public void buyPruduct() {
        driver.findElement(By.id("button-buy-product")).click();
    }

    public Receipt getReceipt() {
        List<ReceiptLine> receiptLines = driver.findElements(By.xpath("//tr[@product-name]")).stream()
                .map(l -> new ReceiptLine(
                        l.findElements(By.tagName("th")).get(0).getText(),
                        Integer.valueOf(l.findElements(By.tagName("th")).get(1).getText()),
                        Long.valueOf(l.findElements(By.tagName("th")).get(2).getText())))
                .collect(Collectors.toList());
        double totalAmount = 0L;
        for (ReceiptLine line: receiptLines) {
            totalAmount += line.getPrice();
        }

        return new Receipt(receiptLines, totalAmount);
    }

    public double getTotalPriceOfPurchase() {
        return Double.valueOf(driver.findElement(By.id("totalPrice")).getText());
    }

    public double getStoreVat() {
        String s = driver.findElement(By.className("exeHeader")).findElement(By.tagName("p")).getText();
        int i = s.indexOf("%");

        return Double.valueOf("0." + s.substring(i-2, i));
    }

    public double getTotalVATOfPurchase() {
        return Double.valueOf(driver.findElement(By.id("totalVAT")).getText());
    }


    public List<Product> getAllProducts() {
        return driver.findElement(By.id("productList")).findElements(By.tagName("tr"))
                .stream()
                .map(tableRow ->
                        new Product(tableRow.findElement(By.id("prod-name")).getText(),
                                Long.valueOf(tableRow.findElement(By.id("prod-price")).getText())))
                .collect(Collectors.toList());
    }

    public void sellFirtProduct() {
        driver.findElement(By.id("bought")).findElement(By.tagName("button")).click();
    }

}
