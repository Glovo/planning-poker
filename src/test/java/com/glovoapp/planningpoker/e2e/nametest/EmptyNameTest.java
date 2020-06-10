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

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

class EmptyNameTest {

    private static final By ERROR_NAME_SELECTOR = By.id("error-message"); //wskaznik na blad ktory powinien sie pojawic
    private static final By RANDOM_BUTTON_SELECTOR = By.id("join-random-session"); //wskaznik na random sesion
    private static final By SPECTATOR_SELECTOR = By.id("is-spectator"); //wskaznik na Spectator? check box


    private final String errorTExt = "Player name must not be empty."; //tekt bledu do porownania

    private final WebDriver driver = getDriver();

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void shouldWork() {

        MainPage browser = new MainPage (driver);
        browser.open();

        //dołącz do random session
        browser.joinRandomSession();

        PlanningPoker secondSite = new PlanningPoker(driver);
        //zaznacz spectatora
        secondSite.specClick();

        //poczekaj az pojawi sie blad
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(visibilityOfElementLocated(ERROR_NAME_SELECTOR));

        //Sprawdz wynik oczekiwany z rzeczywistym - czy blad sie zgadza
        assertEquals(errorTExt,secondSite.getErrorMessage() );

    }

}
