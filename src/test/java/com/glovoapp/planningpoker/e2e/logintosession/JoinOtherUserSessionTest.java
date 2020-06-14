package com.glovoapp.planningpoker.e2e.logintosession;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

class JoinOtherUserSessionTest {
    private static final String sessionName = "test1";

    private final WebDriver driver = getDriver();
    private final WebDriver driver2 = getDriver();
    MainPage firstUsersMainPage = new MainPage(driver);
    SessionPage firstUsersSessionPage = new SessionPage(driver);
    MainPage secondUsersMainPage = new MainPage(driver2);
    SessionPage secondUsersSessionPage = new SessionPage(driver2);

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenTwoUsersJoinTheSameSession_theyShouldSeeEachOtherNames() {

        firstUsersMainPage.openGlovoSite();
        firstUsersMainPage.joinSession(sessionName);
        firstUsersSessionPage.enterName("michal");
        // ---------------------- SECOND USER ----------------
        secondUsersMainPage.openGlovoSite();
        secondUsersMainPage.joinSession(sessionName);
        //check if any cell in the scoreboard contains the name michal
        Assertions.assertTrue(secondUsersSessionPage.anyUsernameInVotesTableMatches("michal"));
    }
}
