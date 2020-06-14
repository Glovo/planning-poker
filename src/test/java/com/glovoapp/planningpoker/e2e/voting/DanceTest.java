package com.glovoapp.planningpoker.e2e.voting;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class DanceTest {

    private final String userName = "michal";
    private final String ticketName = "asd";

    private final WebDriver driver = getDriver();

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void shouldWork() throws IOException, UnsupportedFlavorException {
        // Przejdź do strony
        MainPage browserM = new MainPage(driver);
        browserM.openGlovoSite();
        //znajdz Create random session i kliknij
        browserM.joinRandomSession();
        //Znajdz Your name i wpisz userName
        SessionPage browserP = new SessionPage(driver);
        browserP.enterName(userName);
        browserP.clickNameField();
        browserP.clearNameField();
        browserP.enterName(userName);
        //Znajdz Ticket i wpisz ticketName
        browserP.enterTicket(ticketName);
        //Znajdz przycisk 2
        Optional<WebElement> przycisk2 = browserP.getVotingButton("2");
        //kliknij przycisk
        przycisk2.get().click();

        final WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(visibilityOfElementLocated(By.cssSelector("#glopi-dance > img")));

    }
}
