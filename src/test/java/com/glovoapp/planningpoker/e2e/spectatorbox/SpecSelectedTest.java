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

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void shouldWork() {
        // Przejdź do strony
        MainPage browserM = new MainPage(driver);
        browserM.openGlovoSite();
        //znajdz Create random session i dolacz
        browserM.joinRandomSession();
        //Znajdz Your name i wprowadz userName
        SessionPage browserP = new SessionPage(driver);
        //User1 amd User2 name to enter
        String userName = "michal";
        browserP.enterName(userName);
        //znajdz Selector checkbox i go kliknij
        browserP.specClick();
        try {
            Thread.sleep(1_000);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        //przycisnij 2
        Optional<WebElement> przycisk2 = browserP.getVotingButton("2");
        przycisk2.get().click();

        browserP.emptyTableCheck();


    }
}