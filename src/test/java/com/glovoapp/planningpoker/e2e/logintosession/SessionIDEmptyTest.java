package com.glovoapp.planningpoker.e2e.logintosession;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

class SessionIDEmptyTest {

    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private String expecterResult = "Session ID must not be empty.";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenJoiningSessionWithoutName_ErrorAppears() {

        mainPage.openGlovoSite();
        mainPage.joinSession("");
        mainPage.suspendJavascript(1000);
        //check if the message appeared and is the same as expected
        mainPage.errorMessageCheck(expecterResult);

    }
}
