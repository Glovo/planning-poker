package com.glovoapp.planningpoker.e2e.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public final class SessionPage {

    private final WebDriver driver;

    public SessionPage(WebDriver driver) {
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
    private static final By DANCE_SELECTOR = By.id("glopi-dance");

    public void waitForError(){
        final WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(visibilityOfElementLocated(ERROR_MESSAGE_SELECTOR));
    }

    public final WebElement danceSection() {
        return driver.findElement(DANCE_SELECTOR);
    }

    private List<WebElement> getVotingButtons() {
        final WebElement votingButtons = driver.findElement(VOTE_BUTTONS_SELECTOR);
        return votingButtons.findElements(By.tagName("button"));
    }

    public Optional<WebElement> getVotingButton(final String voteValue) {
        return getVotingButtons().stream()
                .filter(element -> voteValue.equals(element.getText()))
                .findFirst();
    }


    public boolean isHeReallyDancing() {

        return danceSection().findElement(By.tagName("img")).isDisplayed();

    }


    private WebElement getYourNameIDField() {
        return driver.findElement(YOUR_NAME_SELECTOR);
    }

    public void clickNameField() {
        driver.findElement(YOUR_NAME_SELECTOR).click();
    }

    public void clearNameField() {
        driver.findElement(YOUR_NAME_SELECTOR).clear();
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
        getTicketIDField().sendKeys(ticketName);
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

    public void clearButtonClick() {
        getClearIdField().click();
    }

    private WebElement getShowVotesIdField() {
        return driver.findElement(SHOW_VOTES_SELECTOR);
    }

    public void showClick() {
        getShowVotesIdField().click();
    }

    private WebElement getShareIddField() {
        return driver.findElement(SHARE_SESSION_LINK_SELECTOR);
    }

    public void shareClick() {
        getShareIddField().click();
    }

    // TODO: delete
    public String getClipboardWithThrows() throws IOException, UnsupportedFlavorException {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    public String getClipboard() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (Exception checkedException) {
            throw new RuntimeException(checkedException);
        }
    }

    public void emptyTableCheck() {
        final List<WebElement> rows = driver.findElement(By.id("votes-table")).findElements(By.tagName("tr"));

        rows.forEach(row -> {
            final List<WebElement> columns = row.findElements(By.tagName("td"));
            assertEquals(2, columns.size());
            columns.forEach(column -> assertTrue(column.getText().isEmpty()));
        });
    }

    private WebElement getVotesTable() {
        return driver.findElement(By.id("votes-table"));
    }

    private List<WebElement> getTableRows() {
        return getVotesTable().findElements(By.tagName("tr"));
    }

    public final boolean allVotesAreNumeric() {
        return allVotesTableElementsMatch(
                userName -> true,
                vote -> vote.getText().matches("^[0-9.]+$")
        );
    }

    public final boolean allVotesTableElementsAreEmpty() {
        return allVotesTableElementsMatch(
                userName -> userName.getText().isEmpty(),
                vote -> vote.getText().isEmpty()
        );
    }

    public final boolean anyUsernameInVotesTableMatches(String value) {
        return anyVotesTableElementsMatch(
                userName -> userName.getText().equals(value),
                vote -> true
        );
    }
//Zwraca logiczny wynik z funkcji z nawiasów czyli (a, b, (a funkcja/zaleznosc b))
    public final boolean allVotesTableElementsMatch(final Predicate<WebElement> userNameColumnPredicate,
                                                    final Predicate<WebElement> voteColumnPredicate) {
        return votesTableElementsMatch(userNameColumnPredicate, voteColumnPredicate, Boolean::logicalAnd);
    }

    public final boolean anyVotesTableElementsMatch(final Predicate<WebElement> userNameColumnPredicate,
                                                    final Predicate<WebElement> voteColumnPredicate) {
        return votesTableElementsMatch(userNameColumnPredicate, voteColumnPredicate, Boolean::logicalOr);
    }

    public final boolean votesTableElementsMatch(final Predicate<WebElement> userNameColumnPredicate,
                                                 final Predicate<WebElement> voteColumnPredicate,
                                                 final BinaryOperator<Boolean> reductionOperator) {
        return getTableRows().stream()
                .map(row -> {
                    final List<WebElement> columns = row.findElements(By.tagName("td"));

                    final WebElement userNameColumn = columns.get(0);
                    final boolean userNameColumnValid = userNameColumnPredicate.test(userNameColumn);

                    final WebElement voteColumn = columns.get(1);
                    final boolean voteColumnValid = voteColumnPredicate.test(voteColumn);

                    return userNameColumnValid && voteColumnValid;
                }).reduce(reductionOperator)
                .orElse(true);
    }

    public void suspendJavascript(final long millis) {
        new Thread(() -> ((JavascriptExecutor) driver).executeScript(
                "const date = new Date();" +
                        "var currentDate = null;" +
                        "do { currentDate = new Date(); }" +
                        "while (currentDate - date < " + millis + ");"
        )).start();
    }

    public void holdHowLong(long l) {
        driver.manage().timeouts().implicitlyWait(l, TimeUnit.SECONDS);
    }

}
