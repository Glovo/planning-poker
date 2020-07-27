package com.glovoapp.planningpoker.e2e.Clear_ShowVotes_ShareSessionLink;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowVotesButtonTest {
    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private SessionPage sessionPage = new SessionPage(driver);
    final String username = "michal";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenYouClickOnShowVotesButtonWithoutVoting_QuestionMarkInAllVotesShouldAppearNextToYourName() {

        mainPage.openGlovoSite();
        mainPage.joinRandomSession();

        sessionPage.enterName(username);
        //Refresh site using double click on spec mode
        sessionPage.specClick();
        sessionPage.specClick();
        //Click on show votes button
        sessionPage.showClick();
        sessionPage.suspendJavascript(1000);
        //Check if the All votes table contains "?" next to your name
        assertTrue(sessionPage.allVotesInTableFit("michal", "?"));
    }
}
