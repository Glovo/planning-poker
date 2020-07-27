package com.glovoapp.planningpoker.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MainPage {

    private static final String url = "https://glovo-planning-poker.herokuapp.com/";
    private static final By SESSION_ID_FIELD_SELECTOR = By.id("session-id");
    private static final By JOIN_SESSION_BUTTON_SELECTOR = By.id("join-session");
    private static final By JOIN_RANDOM_SESSION_BUTTON_SELECTOR = By.id("join-random-session");
    private static final By JOIN_SESSION_ERROR_MESSAGE = By.id("error-message");

    private final WebDriver driver;

    public MainPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void openGlovoSite() {
        driver.navigate().to(url);
    }

    public void joinSession(final String sessionName) {
        getSessionIdField().sendKeys(sessionName);
        getJoinSessionButton().click();
    }

    public void joinRandomSession() {
        getJoinRandomSessionButton().click();
    }

    private WebElement getSessionIdField() {
        return driver.findElement(SESSION_ID_FIELD_SELECTOR);
    }

    private WebElement getJoinSessionButton() {
        return driver.findElement(JOIN_SESSION_BUTTON_SELECTOR);
    }

    private WebElement getJoinRandomSessionButton() {
        return driver.findElement(JOIN_RANDOM_SESSION_BUTTON_SELECTOR);
    }

    public void errorMessageCheck (final String expectedResult) {
        assertEquals(expectedResult, driver.findElement(JOIN_SESSION_ERROR_MESSAGE).getText());
    }

    public void suspendJavascript(final long millis) {
        new Thread(() -> ((JavascriptExecutor) driver).executeScript(
                "const date = new Date();" +
                        "var currentDate = null;" +
                        "do { currentDate = new Date(); }" +
                        "while (currentDate - date < " + millis + ");"
        )).start();
    }

}
