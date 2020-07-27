package com.glovoapp.planningpoker.e2e.voting;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class DanceTest {

    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private SessionPage sessionPage = new SessionPage(driver);
    private final String userName = "username";
    private final String ticketName = "asd";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void afterAddingNameAddingTicketAndClickingVoting_PineAppleShouldStartDancing() {

        mainPage.openGlovoSite();
        mainPage.joinRandomSession();
        sessionPage.enterName(userName);
        sessionPage.enterTicket(ticketName);
        //Refreshing site
        sessionPage.specClick();
        sessionPage.specClick();
        Optional<WebElement> button = sessionPage.getVotingButton("2");
        button.get().click();
        //Wait for pineapple
        sessionPage.suspendJavascript(1000);
        //checking if the gif has loaded
        sessionPage.isHeReallyDancing();
    }
}
