package com.glovoapp.planningpoker.e2e.voting;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

class AllVotesNumericalTest {

    private final WebDriver driver = getDriver();
    private final WebDriver driver2 = getDriver();
    MainPage firstUserMainPage = new MainPage(driver);
    SessionPage firstUserSessionPage = new SessionPage(driver);
    MainPage secondUserMainPage = new MainPage(driver2);
    SessionPage secondUserSessionPage = new SessionPage(driver2);

    private final String sessionName = "TEST29";
    private final String userName1 = "michal";
    private final String userName2 = "pawel";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
        WebDriverFactory.releaseDriver(driver2);
    }

    @Test
    void afterVoting2And8_InAllVotesTableShouldNotBeNonNumericalVotes() {

// ------------------------- FirstUser ----------------------
        firstUserMainPage.openGlovoSite();
        firstUserMainPage.joinSession(sessionName);
        firstUserSessionPage.enterName(userName1);
        //Refreshing site
        firstUserSessionPage.specClick();
        firstUserSessionPage.specClick();
        Optional<WebElement> firstButton = firstUserSessionPage.getVotingButton("2");
        firstButton.get().click();
// ------------------------- SecondUser ----------------------
        secondUserMainPage.openGlovoSite();
        secondUserMainPage.joinSession(sessionName);
        secondUserSessionPage.enterName(userName2);
        //Refreshing site
        secondUserSessionPage.specClick();
        secondUserSessionPage.specClick();
        Optional<WebElement> secondButton = secondUserSessionPage.getVotingButton("8");
        secondButton.get().click();
        //Check the expected result with the real one
        secondUserSessionPage.suspendJavascript(1000);
        Assertions.assertTrue(secondUserSessionPage.allVotesAreNumeric());
    }
}


