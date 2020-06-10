package com.glovoapp.planningpoker.e2e.voting;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

public class AverageTest {

    private final String sessionName = "test";
    private final String userName1 = "michal"; //User1 name
    private final String userName2 = "pawel"; //User2 name
    private final String oczekiwanyWynik = "Average: 5";

    private static final By SESSION_ID_SELECTOR = By.id("session-id"); //wskaznik na sesje pole
    private static final By JOIN_BUTTON_SELECTOR = By.id("join-session"); //wskaznik na join
    private static final By CREATE_NAME_SELECTOR = By.id("my-name"); //wskaznik na your name pole
    private static final By BUTTON2_SELECTOR = By.cssSelector("#vote-buttons > button:nth-child(3)"); // wskaznik przycisk 2
    private static final By BUTTON8_SELECTOR = By.cssSelector("#vote-buttons > button:nth-child(6)"); // wskaznik przycisk 8
    private static final By AVERAGE_SELECTOR = By.cssSelector("#average-votes-text");

    private final WebDriver driver = getDriver();
    private final WebDriver driver2 = getDriver();

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
        WebDriverFactory.releaseDriver(driver2);
    }

    @Test
    void shouldWork() {

        // ------------------------- ZALOGOWANIE SIE NA STRONE ----------------------

        // Przejdź do strony
        driver.navigate().to("https://glovo-planning-poker.herokuapp.com/");
        //Znajdz pole SESSION ID
        final WebElement sessionSelector1 = driver.findElement(SESSION_ID_SELECTOR);
        //Wpisz nazwe Sesji
        sessionSelector1.sendKeys(sessionName);
        //znajdz przycisk Join
        final WebElement joinSelector1 = driver.findElement(JOIN_BUTTON_SELECTOR);
        //kliknij przycisk Join
        joinSelector1.click();

        // ------------------------- WYPELNIANIE FORMULARZA -------------------------

        //Znajdz Your name
        final WebElement nameSelector1 = driver.findElement(CREATE_NAME_SELECTOR);
        //Wpisz imie
        nameSelector1.sendKeys(userName1);
        //Kliknie przycisk do glosowania 2
        final WebElement button = driver.findElement(BUTTON2_SELECTOR);
        //kliknij przycisk
        button.click();

        // ------------------------- ZALOGOWANIE SIE NA STRONE User2------------------

        // Przejdź do strony
        driver2.navigate().to("https://glovo-planning-poker.herokuapp.com/");
        //Znajdz pole SESSION ID
        final WebElement sessionSelector2 = driver2.findElement(SESSION_ID_SELECTOR);
        //Wpisz nazwe Sesji
        sessionSelector2.sendKeys(sessionName);
        //znajdz przycisk Join
        final WebElement joinSelector2 = driver2.findElement(JOIN_BUTTON_SELECTOR);
        //kliknij przycisk Join
        joinSelector2.click();

        // ------------------------- WYPELNIANIE FORMULARZA User2-----------------------

        //poczekaj
        final WebDriverWait wait = new WebDriverWait(driver2, 10);
        WebElement ave = driver2.findElement(AVERAGE_SELECTOR);

        //Znajdz Your name
        final WebElement nameSelector2 = driver2.findElement(CREATE_NAME_SELECTOR);
        //Wpisz imie
        nameSelector2.sendKeys(userName2);
        //znajdz przycisk 8
        final WebElement button2 = driver2.findElement(BUTTON8_SELECTOR);
        //kliknij przycisk
        button2.click();

        // -------------------------------- SPRAWDZENIE ---------------------------------

        //znajdz srednia
//        new WebDriverWait(driver2, 2).until(driver ->
//                oczekiwanyWynik.equals(
//                        driver.findElement(AVERAGE_SELECTOR).getText()
//                )
//        ); // albo referencja do metody
        new WebDriverWait(driver2, 2).until(this::averageEqualsExpectedResult);
    }

    private boolean averageEqualsExpectedResult(final WebDriver driver) {
        System.out.println("sprawdzamy!");
        return oczekiwanyWynik.equals(
                driver.findElement(AVERAGE_SELECTOR).getText()
        );
    }
}
