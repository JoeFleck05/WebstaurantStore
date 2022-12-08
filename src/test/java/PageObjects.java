import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PageObjects {
    private static WebElement element = null;
    private static List<WebElement> listElement = null;

    // Search Field
    public static WebElement search_field(WebDriver driver) {
        element = driver.findElement(By.name("searchval"));
        return element;
    }

    // Search Button
    public static WebElement search_button(WebDriver driver) {
        element = driver.findElement(By.xpath("//button[text()='Search']"));
        return element;
    }

    // Search Results
    public static List<WebElement> search_results(WebDriver driver){
        listElement = driver.findElements(By.xpath("//*[@id='details']/a"));
        return listElement;
    }

    // Pages Buttons
    public static List<WebElement> page_buttons(WebDriver driver){
        listElement = driver.findElements(By.xpath("//div[@id='paging']//ul//child::li"));
        return listElement;
    }

    // Item Description Buttons
    public static List<WebElement> item_descriptions(WebDriver driver){
        listElement = driver.findElements(By.xpath("//div[@id='product_listing']//following::a[@data-testid='itemDescription']"));
        return listElement;
    }

    // Add to Cart
    public static List<WebElement> add_to_cart(WebDriver driver){
        listElement = driver.findElements(By.xpath("//input[@value='Add to Cart']"));
        return listElement;
    }

    // Add to Cart Pop-up
    public static WebElement add_to_cart_popup(WebDriver driver) {
        element = driver.findElement(By.xpath("//*[@id='td']/div[11]/div/div/footer/button[2]"));
        return element;
    }

    // View Cart
    public static WebElement view_cart(WebDriver driver) {
        element = driver.findElement(By.xpath("//*[@id=\"watnotif-wrapper\"]/div/p/div[2]/div[2]/a[1]"));
        return element;
    }

    // Delete Item
    public static WebElement delete(WebDriver driver) {
        element = driver.findElement(By.xpath("//button[@class='deleteCartItemButton itemDelete__link itemDelete--positioning']"));
        return element;
    }

    // Cart Table
    public static WebElement empty_cart (WebDriver driver) {
        element = driver.findElement(By.xpath("//div[@class='empty-cart__inner']"));
        return element;
    }
}
