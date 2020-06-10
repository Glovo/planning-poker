package com.glovoapp.planningpoker.e2e.nametest;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.PlanningPoker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;
import java.util.stream.IntStream;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

class SameNameInSessionTest {

    private final WebDriver driver = getDriver();
    private final WebDriver driver2 = getDriver();
    private static final By ERROR_NAME_SELECTOR = By.id("error-message");
    private static final By USER1NAME_SELECTOR = By.cssSelector("#votes-table > tr > td:nth-child(1)");


    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
        WebDriverFactory.releaseDriver(driver2);
    }

    @Test
    void shouldWork() {
        // ------------------------- ZALOGOWANIE SIE NA STRONE ----------------------
        MainPage browser = new MainPage(driver);
        browser.open();
        String sessionName = "test2";
        browser.joinSession(sessionName);

        // ------------------------- WYPELNIANIE FORMULARZA -------------------------
        //wprowadz imie
        PlanningPoker browser1 = new PlanningPoker(driver);
        //User1 amd User2 name to enter
        String userName = "michal";
        browser1.enterName(userName);
        //Znajdz przycisk 2
        Optional<WebElement> przycisk2 = browser1.getVotingButton("2");
        //Kliknie przycisk do glosowania 2
        przycisk2.get().click();

        // ------------------------- ZALOGOWANIE SIE NA STRONE User2------------------

        MainPage browser2 = new MainPage(driver2);
        browser2.open();
        browser2.joinSession(sessionName);

        // ------------------------- WYPELNIANIE FORMULARZA User2-----------------------

        //poczekaj az pojawi sie 1 gracz
        final WebDriverWait wait = new WebDriverWait(driver2, 2);
        wait.until(visibilityOfElementLocated(USER1NAME_SELECTOR));
        IntStream.range(0, 100).forEach(it -> {
        });

        PlanningPoker browser22 = new PlanningPoker(driver2);
        browser22.enterName(userName);

        // -------------------------------- SPRAWDZENIE ---------------------------------

        //poczekaj az pojawi sie blad
        final WebDriverWait wait2 = new WebDriverWait(driver2, 2);
        wait.until(visibilityOfElementLocated(ERROR_NAME_SELECTOR));

        //Sprawdz czy blad sie zgadza
        //tekt bledu do porownania
        String errorTExt = "Error: player with name michal already exists.";
        assertEquals(errorTExt, browser22.getErrorMessage());
    }
}