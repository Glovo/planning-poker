package com.glovoapp.planningpoker.e2e.voting;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

public class AverageTest {

    private final String sessionName = "test1";
    private final String userName1 = "michal"; //User1 name
    private final String userName2 = "pawel"; //User2 name
    private final String oczekiwanyWynik = "Average: 5";
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
        MainPage browserM1 = new MainPage(driver);
        browserM1.openGlovoSite();
        //Znajdz pole SESSION ID, wpisz sessionName i kliknij Join
        browserM1.joinSession(sessionName);

        // ------------------------- WYPELNIANIE FORMULARZA -------------------------

        //Znajdz Your name
        SessionPage browserP1 = new SessionPage(driver);
        browserP1.enterName(userName1);
        browserP1.holdHowLong(2);

        //Kliknie przycisk do glosowania 2
        Optional<WebElement> przycisk2 = browserP1.getVotingButton("2");
        //kliknij przycisk
        przycisk2.get().click();
        przycisk2.get().click();
        przycisk2.get().click();
        przycisk2.get().click();


        // ------------------------- ZALOGOWANIE SIE NA STRONE User2------------------

        // Przejdź do strony
        MainPage browserM2 = new MainPage(driver2);
        browserM2.openGlovoSite();
        //Znajdz pole SESSION ID
        browserM2.joinSession(sessionName);

        // ------------------------- WYPELNIANIE FORMULARZA User2-----------------------

        //poczekaj
        SessionPage browserP2 = new SessionPage(driver2);
        browserP2.holdHowLong(5);
        //Znajdz Your name
        browserP2.enterName(userName2);
        //kliknij przycisk 8
        Optional<WebElement> przycisk8 = browserP2.getVotingButton("8");
        przycisk8.get().click();

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
