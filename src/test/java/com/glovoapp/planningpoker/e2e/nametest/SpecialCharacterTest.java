package com.glovoapp.planningpoker.e2e.nametest;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpecialCharacterTest {

    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private SessionPage sessionPage = new SessionPage(driver);

    private final String userName = "SOMETEXT:";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenTheUserTypesToTheNameColon_AnErrorShouldAppear() {

        mainPage.openGlovoSite();
        mainPage.joinRandomSession();
        sessionPage.enterName(userName);
        //SpecButton is used to refresh data on the page
        sessionPage.specClick();

        //Check the expected result with the real one
        String errorText = "Player name must not contain the colon character ':'.";
        assertEquals(errorText, sessionPage.getErrorMessage());

    }
}

