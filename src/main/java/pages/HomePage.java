package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject{

    public static final String title = "IDOS • Vlaky + Autobusy + MHD (všechna) • Vyhledání spojení";
    @FindBy(id = "From")
    private WebElement fromField;

    @FindBy(id = "To")
    private WebElement toField;

    @FindBy(xpath = "//a[@class='inc ico-right pikaday-up btn btn-blue']")
    private WebElement nextDayButton;

    @FindBy(id = "Time")
    private WebElement timeField;

    @FindBy(xpath = "//label[@for='OnlyDirect']")
    private WebElement directConnectionsBox;

    @FindBy(xpath = "//div[@class='submit']/button")
    private WebElement searchButton;

    @FindBy(linkText = "obec, okres Praha, vlaky, autobusy, MHD")
    private WebElement whispererPrague;
    @FindBy(linkText = "obec, okres Brno-město, vlaky, autobusy, MHD")
    private WebElement whispererBrno;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String title() {
        return title;
    }

    public void typeAddressFrom(String from) {
        fromField.sendKeys(from);
        whispererPrague.click();
    }
    public void typeAddressTo(String to) {
        toField.sendKeys(to);
        whispererBrno.click();
    }
    public void setNextDay() {
        nextDayButton.click();
    }
    public void typeTime(String time){
        timeField.sendKeys(time);
    }

    public void checkDirectConnectionsBox() {
        directConnectionsBox.click();
    }

    public ConnectionsPage submit() {
        searchButton.click();
        return new ConnectionsPage(driver);
    }

}
