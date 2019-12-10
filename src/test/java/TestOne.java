import WebObjects.PageObject;
import WebObjects.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestOne {

    private WebDriver driver;
    private String url;
    private PageObject pageObject;
    private SoftAssert softAssert;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        url = "https://hoff.app/store";
        driver = new ChromeDriver();
        pageObject = new PageObject(driver);
        driver.navigate().to(url);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

    @DataProvider(name = "productList")
    public Object[] productList() {
        return pageObject.getAllProducts().toArray();
    }

    @Test(dataProvider = "productList")
    public void buyProductTest(Product product) {
        driver.navigate().refresh();

        double initialMoney = Double.valueOf(pageObject.getRemainingMoney());
        int nrOfProductsBought = 1;

        buyNrOfProduct(product, nrOfProductsBought);

        double remainingMoney = Double.valueOf(pageObject.getRemainingMoney());
        double VAT = pageObject.getStoreVat();

        softAssert = new SoftAssert();
        softAssert.assertEquals(remainingMoney, (initialMoney - (nrOfProductsBought * product.getPrice())),
                " - Incorrect remaining money \n");
        softAssert.assertEquals(pageObject.getMessage(), getMessageBought(product, nrOfProductsBought),
                " - Incorrect message after buying \n");
        softAssert.assertEquals(pageObject.getReceipt().getReceiptLines().get(0).getProductName(), product.getName(),
                " - Incorrect productname in receipt \n");
        softAssert.assertEquals(pageObject.getReceipt().getReceiptLines().get(0).getNrOfBoughtProducts(), nrOfProductsBought,
                " - Incorrect nr of products bought in receipt \n");
        softAssert.assertEquals(pageObject.getReceipt().getReceiptLines().get(0).getPrice(), product.getPrice() * nrOfProductsBought,
                " - Incorrect price in receiptLine \n");
        softAssert.assertEquals(pageObject.getTotalPriceOfPurchase(), product.getPrice() * nrOfProductsBought,
                " - Incorrect total price in receipt \n");
        softAssert.assertEquals(pageObject.getTotalVATOfPurchase(), (product.getPrice() * nrOfProductsBought) * VAT,
                "incorrect VAT in receipt");
        softAssert.assertAll("Buying " + nrOfProductsBought + " nr of " + product.getName());
    }


    @Test(dataProvider = "productList")
    public void SellProductTest(Product product) {
        driver.navigate().refresh();

        double initialMoney = pageObject.getRemainingMoney();
        int nrOfProductsBought = 1;

        buyNrOfProduct(product, nrOfProductsBought);
        pageObject.sellFirtProduct();

        softAssert = new SoftAssert();
        softAssert.assertEquals(pageObject.getRemainingMoney(), initialMoney,
                " - Incorrect remaining money \n");
        softAssert.assertEquals(pageObject.getMessage(), getMessageSold(product, nrOfProductsBought),
                " - Incorrect message after selling \n");
        softAssert.assertEquals(pageObject.getReceipt().getReceiptLines().size(), 0,
                " - Incorrect receiptlines in receipt \n");
        softAssert.assertEquals((int) pageObject.getTotalPriceOfPurchase(), 0,
                " - Incorrect total price in receipt \n");
        softAssert.assertEquals(pageObject.getTotalVATOfPurchase(), 0.0,
                "incorrect VAT in receipt");
        softAssert.assertAll("Selling " + nrOfProductsBought + " x " + product.getName());
    }



    private void buyNrOfProduct(Product product, int nrOfProductsBought) {
        pageObject.selectProductInDropdown(product);
        pageObject.setAmountOfProductsToBuy(nrOfProductsBought);
        pageObject.buyPruduct();
    }


    private String getMessageBought(Product product, int nrOfProductsBought) {
        return "You bought " + nrOfProductsBought + " x " + product.getName() + " for a total of " + (int) (nrOfProductsBought * product.getPrice());
    }

    private String getMessageSold(Product product, int nrOfProductsSold) {
        return "You sold " + nrOfProductsSold + " x " + product.getName() + " for a total of " + (int) (nrOfProductsSold * product.getPrice());
    }

    private String getInsufficientFundsMessage() {
        return "Insufficient funds!";
    }
}