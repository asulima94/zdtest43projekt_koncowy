package zdtest.cucumbertests;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepsDefinitions {

    WebDriver driver;
    WebDriverWait wait;
    String podcastTitleFromList;
    String firstPodcastFromListLink;
    String firstVideoUrl;


    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("DevTo main page is open")
    public void devto_main_page_is_open() {
        driver.get("https://dev.to");
    }

    @When("User click on podcasts")
    public void user_click_on_podcasts() {
        WebElement podcasts = driver.findElement(By.partialLinkText("Podcasts"));
        podcasts.click();
    }
    @When("User select first podcast")
    public void user_select_first_podcast() {
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        WebElement firstPodcast = driver.findElement(By.cssSelector(".content > h3:first-child"));
        podcastTitleFromList = firstPodcast.getText();
        firstPodcastFromListLink = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");
        firstPodcast.click();
    }
    @Then("User can see its title")
    public void user_can_see_its_title() {
        wait.until(ExpectedConditions.urlToBe(firstPodcastFromListLink));
        WebElement podcastTitle = driver.findElement(By.cssSelector(".title > h1:nth-child(2)"));
        String podcastTitleText = podcastTitle.getText();
        assertTrue(podcastTitleFromList.contains(podcastTitleText));

    }
    @Then("User can play it")
    public void user_can_play_it() {
        WebElement record = driver.findElement(By.cssSelector(".record"));
        record.click();
        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        WebElement recordWrapper = driver.findElement(By.className("record-wrapper"));
        String classAttribute = recordWrapper.getAttribute("class");
        Boolean isPodcastPlayed = classAttribute.contains("playing");
        assertTrue(isPodcastPlayed);

    }

    @When("User click on videos")
    public void user_click_on_videos() {
        driver.get("https://dev.to");
        WebElement sideBarVideo = driver.findElement(By.partialLinkText("Videos"));
        sideBarVideo.click();
    }

    @When("User select first video")
    public void user_select_first_video() {
        wait.until(ExpectedConditions.urlToBe("https://dev.to/videos"));
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        String firstVideoUrl = driver.findElement(By.cssSelector("#video-colection >a:first-child")).getAttribute("href");
        firstVideo.click();
    }
    @Then("User can see first video page")
    public void user_can_see_first_video_page() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("crayons-article__video"))));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(firstVideoUrl,currentUrl);
    }
}
