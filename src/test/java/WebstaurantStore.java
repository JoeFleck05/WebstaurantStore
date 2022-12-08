import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


class ChromeTest {

    WebDriver driver;
    PageObjects pageObjects = new PageObjects();

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
        driver.get("https://www.webstaurantstore.com");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void searchStainlessSteelTable() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(PageObjects.search_button(driver)));

        PageObjects.search_field(driver).sendKeys("stainless work table");
        PageObjects.search_button(driver).click();
        int numberOfResults = PageObjects.search_results(driver).size() - 1;
        boolean isFound = false;
        for(int i = 0 ; i <= numberOfResults ; i++){
            if(PageObjects.search_results(driver).get(i).getText().contains("Stainless Steel")){
                isFound = true;
                break;
            }
        }

        // Verify stain steel in description
        assert(isFound);
    }

    @Test
    void VerifyWordTableDisplays() throws InterruptedException {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(PageObjects.search_button(driver)));

        // Search Stainless work table
        PageObjects.search_field(driver).sendKeys("stainless work table");
        PageObjects.search_button(driver).click();

        // Set assertion boolean
        boolean containsWordTable = true;

        // Number of pages from search
        int numberOfPages = PageObjects.page_buttons(driver).size();
        for(int i = 0 ; i <= numberOfPages ; i++){
            int textNum = i + 1;
            if(i != 0){
                String linkTest = String.format("//a[text()='%s']", textNum);
                WebElement pageLink = driver.findElement(By.xpath(linkTest));
                pageLink.click();
            }

            int numberOfItems = PageObjects.item_descriptions(driver).size();
            for(int h = 0 ; h <= numberOfItems ; h++){
                if(!PageObjects.item_descriptions(driver).get(i).getText().contains("Table")){
                    containsWordTable = false;
                }
            }
        }

        assert(containsWordTable);
    }

    @Test
    void AddRemoveItemFromCart(){
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Search']")));

        PageObjects.search_field(driver).sendKeys("stainless work table");
        PageObjects.search_button(driver).click();

        // Number of pages from search
        int lastPageNumber = PageObjects.page_buttons(driver).size() + 1;

        String linkTest = String.format("//a[text()='%s']", lastPageNumber);
        WebElement pageLink = driver.findElement(By.xpath(linkTest));
        pageLink.click();

        int numberOfAddToCarts = PageObjects.add_to_cart(driver).size() - 1;
        PageObjects.add_to_cart(driver).get(numberOfAddToCarts).click();
        // Wait
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='accessoriesList form']")));
        // Counter top finish
        Select counterTopFinish = new Select(driver.findElement(By.xpath("//select[@title='Countertop Edge']")));
        counterTopFinish.selectByVisibleText("Advance Tabco TA-12 Countertop Edge");
        // Finish Upgrade
        Select finishUpgrade = new Select(driver.findElement(By.xpath("//select[@title='Finish Upgrade']")));
        finishUpgrade.selectByVisibleText("Advance Tabco K-350 Upgraded Finish");
        // Sink Bowl
        Select sinkBowl = new Select(driver.findElement(By.xpath("//select[@title='Sink Bowl']")));
        sinkBowl.selectByVisibleText("Advance Tabco TA-11B 16\" x 20\" Sink Bowl with Faucet, Welded into Tabletop");
        // Add to cart
        PageObjects.add_to_cart_popup(driver).click();
        // View cart
        PageObjects.view_cart(driver).click();
        // Wait
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='deleteCartItemButton itemDelete__link itemDelete--positioning']")));
        // Delete item
        PageObjects.delete(driver).click();
        // Wait
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='empty-cart__inner']")));
        // Assert cart is empty
        assert(PageObjects.empty_cart(driver).isDisplayed());
    }
}