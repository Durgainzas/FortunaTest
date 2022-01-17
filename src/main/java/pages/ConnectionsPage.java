package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConnectionsPage extends PageObject{
    public static final String title = "IDOS • Praha ➞ Brno • Vlaky + Autobusy + MHD (všechna) • Vyhledání spojení";
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

    @FindBy(xpath = "(//ul[@class='reset stations first last']/ancestor::div[@class='box connection detail-box ca-collapsed'])[1]")
    private WebElement firstDirectConnection;

    private WebElement departureStop =
            firstDirectConnection.findElement(By.xpath("(.//strong[@class='name'])[1]"));
    private WebElement arrivalStop =
            firstDirectConnection.findElement(By.xpath("(.//strong[@class='name'])[2]"));
    private WebElement arrivalTime =
            firstDirectConnection.findElement(By.xpath("(.//p[@class='reset time'])[2]"));
    private WebElement time =
            firstDirectConnection.findElement(By.xpath(".//h2[@class='reset date']"));
    private WebElement date =
            firstDirectConnection.findElement(By.xpath(".//h2[@class='reset date']/span"));
    private WebElement travelTime =
            firstDirectConnection.findElement(By.xpath("(.//p[@class='reset total']/strong)[1]"));
    private WebElement priceValue =
            firstDirectConnection.findElement(By.xpath(".//span[@class='price-value']"));
    private WebElement expandDetails =
            firstDirectConnection.findElement(By.xpath(".//li[@class='expand']/a"));
    private WebElement collapse;

    private List<WebElement> intermediateStops;

    public ConnectionsPage(WebDriver driver) {
        super(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", firstDirectConnection);
    }

    public String getExpectedTitle() {
        return title;
    }

    public String getDate() {
        return date.getText().split(" ")[0];
    }
    public LocalTime getTime() {
        return LocalTime.parse(time.getText().split(getDate())[0], timeFormatter);
    }

    public int getPrice() {
        int price = 0;
        try {
            price = Integer.parseInt(priceValue.getText());
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return price;
    }

    public String getDepartureStop() {
        return departureStop.getText();
    }

    public String getArrivalStop() {
        return arrivalStop.getText();
    }
    public String getTravelTime() {
        return travelTime.getText();
    }
    public String getArrivalTime(){
        return arrivalTime.getText();
    }

    public int countStops() {
        int stops = 2; //first and last stop
        expandDetails.click();
        intermediateStops = firstDirectConnection.findElements(By.xpath(".//li[contains(@class, 'itemConnDetail intermediate')]"));
        stops += intermediateStops.size();
        collapse = firstDirectConnection.findElement(By.xpath("//a[@class='ext-expand ico-up']"));
        collapse.click();
        return stops;
    }
}
