import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ConnectionsPage;
import pages.HomePage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdosTest {
    protected static WebDriver driver;


    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up WebDriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void testIdos() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.M.");

        driver.get("https://idos.idnes.cz/");
        assertEquals(homePage.title(), driver.getTitle(), "Title of page should match");

        homePage.typeAddressFrom("Praha");
        homePage.typeAddressTo("Brno");
        homePage.setNextDay();
        homePage.typeTime("8:00");

        System.out.println("Submitting form");
        ConnectionsPage connectionsPage = homePage.submit();

        assertEquals(connectionsPage.getExpectedTitle(), driver.getTitle(), "Title of page should match");
        assertEquals(date.format(dateFormatter), connectionsPage.getDate(), "Date should be one day upfront");
        assertTrue(connectionsPage.getTime().isAfter(LocalTime.of(8, 0)), "Connection time should be after 8:00");
        assertTrue(connectionsPage.getTime().isBefore(LocalTime.of(23, 59)), "Connection time should be before 23:59");

        System.out.println("Price: " + connectionsPage.getPrice());
        System.out.println("Departure stop: " + connectionsPage.getDepartureStop());
        System.out.println("Arrival stop: " + connectionsPage.getArrivalStop());
        System.out.println("Travel time: " + connectionsPage.getTravelTime());
        System.out.println("Arrival time: " + connectionsPage.getArrivalTime());

        System.out.println("Total stops count: " + connectionsPage.countStops());
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
    }
}
