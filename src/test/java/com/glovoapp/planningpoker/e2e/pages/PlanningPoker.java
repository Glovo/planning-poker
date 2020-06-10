package com.glovoapp.planningpoker.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public final class PlanningPoker {

    private final WebDriver driver;

    public PlanningPoker(WebDriver driver) {
        this.driver = driver;
    }

    private static final By YOUR_NAME_SELECTOR = By.id("my-name");
    private static final By SPECTATOR_SELECTOR = By.id("is-spectator");
    private static final By TICKET_SELECTOR = By.id("ticket-name");
    private static final By ERROR_MESSAGE_SELECTOR = By.id("error-message");
    private static final By AUTO_RELOAD_MESSAGE_SELECTOR = By.id("auto-reload-message");

    private static final By CLEAR_BUTTON_SELECTOR = By.id("clear-all");
    private static final By SHOW_VOTES_SELECTOR = By.id("show-votes");
    private static final By SHARE_SESSION_LINK_SELECTOR = By.id("copy-session-link-to-clipboard");

    private static final By YOUR_VOTE_SELECTOR = By.id("current-vote");
    private static final By VOTE_BUTTONS_SELECTOR = By.id("vote-buttons");
    private static final By MEDIAN_VOTES_SELECTOR = By.id("median-votes-text");
    private static final By AVERAGE_VOTES_SELECTOR = By.id("average-votes-text");
    private static final By VOTES_TABLE_SELECTOR = By.id("votes-table");
    private static final By GLOPI_DANCE_SELECTOR = By.id("glopi-dance");

    private List<WebElement> getVotingButtons() {
        final WebElement votingButtons = driver.findElement(VOTE_BUTTONS_SELECTOR);
        return votingButtons.findElements(By.tagName("button"));
    }

    public Optional<WebElement> getVotingButton(final String voteValue) {
        return getVotingButtons().stream()
                .filter(element -> voteValue.equals(element.getText()))
                .findFirst();
    }

    private WebElement getYourNameIDField() {
        return driver.findElement(YOUR_NAME_SELECTOR);
    }

    private WebElement getErrorMessageIdField() {
        return driver.findElement(ERROR_MESSAGE_SELECTOR);
    }

    public String getErrorMessage() {
        return getErrorMessageIdField().getText();
    }

    public void enterName(final String userName) {
        getYourNameIDField().sendKeys(userName);
    }

    private WebElement getTicketIDField() {
        return driver.findElement(TICKET_SELECTOR);
    }

    public void enterTicket(final String ticketName) {
        getYourNameIDField().sendKeys(ticketName);
    }

    private WebElement getSpectatorIdField() {
        return driver.findElement(SPECTATOR_SELECTOR);
    }

    public void specClick() {
        getSpectatorIdField().click();
    }

    private WebElement getClearIdField() {
        return driver.findElement(CLEAR_BUTTON_SELECTOR);
    }

    public void clearClick() {
        getClearIdField().click();
    }

    private WebElement getShowVotesdField() {
        return driver.findElement(SHOW_VOTES_SELECTOR);
    }

    public void showClick() {
        getShowVotesdField().click();
    }

    private WebElement getShareIddField() {
        return driver.findElement(SHARE_SESSION_LINK_SELECTOR);
    }

    public void shareClick() {
        getShareIddField().click();
    }

    public String getClipboard() throws IOException, UnsupportedFlavorException {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }


}



