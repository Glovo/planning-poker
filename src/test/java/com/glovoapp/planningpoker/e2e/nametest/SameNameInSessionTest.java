package com.glovoapp.planningpoker.e2e.nametest;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SameNameInSessionTest {

    private final WebDriver driver = getDriver();
    private final WebDriver driver2 = getDriver();
    private MainPage firstUsersMainPage = new MainPage(driver);
    private SessionPage firstUsersSessionPage = new SessionPage(driver);
    private MainPage secondUsersMainPage = new MainPage(driver2);
    private SessionPage secondUsersSessionPage = new SessionPage(driver2);

    private final String sessionName = "test2";
    private final String userName = "michal";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
        WebDriverFactory.releaseDriver(driver2);
    }

    @Test
    void whenTwoUsersJoinTheSameSessionAndHaveSameName_AnErrorShouldAppear() {
        // ------------------------- FistUser ----------------------
        firstUsersMainPage.openGlovoSite();
        firstUsersMainPage.joinSession(sessionName);
        firstUsersSessionPage.enterName(userName);
        //SpecButton is used to refresh data on the page
        firstUsersSessionPage.specClick();
        firstUsersSessionPage.specClick();
        // ------------------------- SecondUser -------------------------
        secondUsersMainPage.openGlovoSite();
        secondUsersMainPage.joinSession(sessionName);
        secondUsersSessionPage.enterName(userName);
        //SpecButton is used to refresh data on the page
        secondUsersSessionPage.specClick();
        secondUsersSessionPage.waitForError();

        //Check the expected result with the real one
        String errorText = "Error: player with name michal already exists.";
        assertEquals(errorText, secondUsersSessionPage.getErrorMessage());
    }
}