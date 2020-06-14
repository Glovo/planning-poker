package com.glovoapp.planningpoker.e2e.spectatorbox;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

class SpecSelectedTest {

    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private SessionPage sessionPage = new SessionPage(driver);
    private final String userName = "username";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenAUserWithTheNameSelectsSpectator_HisVoteShouldNotAppear() {

        mainPage.openGlovoSite();
        mainPage.joinRandomSession();
        sessionPage.enterName(userName);
        sessionPage.specClick();

        //A break is required so that the website can update
        try {
            Thread.sleep(1_000);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        Optional<WebElement> button2 = sessionPage.getVotingButton("2");
        button2.get().click();
        //Checking if the scoreboard is empty
        sessionPage.emptyTableCheck();


    }
}