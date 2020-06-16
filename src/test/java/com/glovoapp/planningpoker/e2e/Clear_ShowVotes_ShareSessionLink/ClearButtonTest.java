package com.glovoapp.planningpoker.e2e.Clear_ShowVotes_ShareSessionLink;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import com.glovoapp.planningpoker.e2e.pages.MainPage;
import com.glovoapp.planningpoker.e2e.pages.SessionPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;

class ClearButtonTest {
    private final WebDriver driver = getDriver();
    private MainPage mainPage = new MainPage(driver);
    private SessionPage sessionPage = new SessionPage(driver);
    final String username = "michal";
    final String ticketname = "ticket";

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void whenYouClickOnClearButton_TicketNameShouldDisappear() {

        mainPage.openGlovoSite();
        mainPage.joinRandomSession();

        sessionPage.enterName(username);
        sessionPage.enterTicket(ticketname);
        sessionPage.suspendJavascript(1000);
        //Check value in ticket text field
        sessionPage.compareTicket(ticketname);
        //Click clear button
        sessionPage.clearButtonClick();
        sessionPage.suspendJavascript(1000);
        //Check if value in Ticket is now empty
        sessionPage.compareTicket("");
    }
}
