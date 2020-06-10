package com.glovoapp.planningpoker.e2e.voting;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.PlanningPoker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class DanceTest {

    private final String userName = "michal";
    private final String ticketName = "asd";

    private static final By RANDOM_BUTTON_SELECTOR = By.id("join-random-session"); //wskaznik na random session
    private static final By CREATE_NAME_SELECTOR = By.id("my-name"); //wskaznik na your name
    private static final By TICKET_SELECTOR = By.id("ticket-name"); //wskaznik na ticket
    private static final By BUTTON2_SELECTOR = By.cssSelector("#vote-buttons > button:nth-child(3)"); // wskaznik przycisk 2
    private static final By IMAGE_SELECTOR = By.cssSelector("#glopi-dance"); // wskaznik ananasa

    private final WebDriver driver = getDriver();

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void shouldWork() throws IOException, UnsupportedFlavorException {
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
        //Znajdz Ticket
        final WebElement ticketSelector = driver.findElement(TICKET_SELECTOR);
        //Wpisz imie
        ticketSelector.sendKeys(ticketName);
        //Znajdz przycisk 2
        final WebElement button = driver.findElement(BUTTON2_SELECTOR);
        //kliknij przycisk
        button.click();

        //poczekaj na obrazek
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(visibilityOfElementLocated(IMAGE_SELECTOR));

        //wskaznik na ananasa
        final WebElement pineapple = driver.findElement(IMAGE_SELECTOR);

        //sprawdz czy ananas się pojawwil
        assertTrue(pineapple.isDisplayed());

        PlanningPoker poker = new PlanningPoker(driver);
        poker.shareClick();
        System.out.println(poker.getClipboard());
    }
}
