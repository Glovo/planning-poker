package com.glovoapp.planningpoker.e2e.spectatorbox;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.*;

public class SpecSelectedTest {

    private final String userName = "michal"; //User1 amd User2 name to enter

    private static final By RANDOM_BUTTON_SELECTOR = By.id("join-random-session"); //wskaznik na random sesion
    private static final By CREATE_NAME_SELECTOR = By.id("my-name"); //wskaznik na your name pole
    private static final By SPECTATOR_SELECTOR = By.id("is-spectator"); //wskaznik na spectator check box
    private static final By BUTTON2_SELECTOR = By.cssSelector("#vote-buttons > button:nth-child(3)"); // wskaznik przycisk 2
    private static final By VOTES_TABLE_SELECTOR = By.id("median-votes-text"); //wskaznik na votes table

    private final WebDriver driver = getDriver();

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void shouldWork() {
        // Przejdź do strony
        driver.navigate().to("https://glovo-planning-poker.herokuapp.com/");

        //znajdz Create random session
        final WebElement randomSelector = driver.findElement(RANDOM_BUTTON_SELECTOR);
        //kliknij create random session
        randomSelector.click();
        //Znajdz Your name
        final WebElement nameSelector1 = driver.findElement(CREATE_NAME_SELECTOR);
        //Wpisz imie
        nameSelector1.sendKeys(userName);
        //znajdz Selector checkbox
        final WebElement specSelector = driver.findElement(SPECTATOR_SELECTOR);
        //zaznacz selektor
        specSelector.click();
        //Znajdz przycisk 2
        final WebElement button = driver.findElement(BUTTON2_SELECTOR);
        //kliknij przycisk
        button.click();

        final WebElement median = driver.findElement(VOTES_TABLE_SELECTOR);
        assertFalse(median.isDisplayed());

        final WebElement namesAndVotesArray = driver.findElement(By.id("votes-table"));
        final List<WebElement> rows = namesAndVotesArray.findElements(By.tagName("tr"));
        rows.forEach(row -> {
            final List<WebElement> columns = row.findElements(By.tagName("td"));
            assertEquals(2, columns.size());
            columns.forEach(column -> assertTrue(column.getText().isEmpty()));
        });

        //sprawdz czy pojawila sie tablica z wynikami
        //assertFalse(driver.findElement(By.xpath("//*[@id=\"votes-table\"]"))!= null); // z xpatha jest wynik boolean ktory mozna uzyc do assertFalse
}
    }
//*[@id="median-votes-text"]    xpath do mediany , podwojny backslash przeszkadza jak z tego uciec