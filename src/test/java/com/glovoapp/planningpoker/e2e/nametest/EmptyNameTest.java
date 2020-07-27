package com.glovoapp.planningpoker.e2e.nametest;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmptyNameTest {
    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private SessionPage sessionPage = new SessionPage(driver);

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenYouClickSpectatorWithoutEnteringAName_AnErrorShouldPopUp() {

        mainPage.openGlovoSite();
        mainPage.joinRandomSession();

        sessionPage.specClick();

        //wait for the error to appear (2 seconds)
        sessionPage.waitForError();

        //Check the expected result with the real one
        String errorTExt = "Player name must not be empty.";
        assertEquals(errorTExt, sessionPage.getErrorMessage());

    }
}
