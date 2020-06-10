package com.glovoapp.planningpoker.e2e.nametest;

import com.glovoapp.planningpoker.e2e.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.glovoapp.planningpoker.e2e.WebDriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SpecialCharacterTest {


    private final String nameText = "SOMETEXT:"; //imie do wpisania
    private final String errorTExt = "Player name must not contain the colon character ':'.";

    private static final By RANDOM_BUTTON_SELECTOR = By.id("join-random-session"); //wskaznik na random sesion
    private static final By CREATE_NAME_TEXTFIELD_SELECTOR = By.id("my-name"); //wskaznik na Your name
    private static final By ERROR_NAME_SELECTOR = By.id("error-message"); //wskaznik na blad ktory powinien sie pojawic

    private final WebDriver driver = getDriver();

    @AfterEach
    void releaseDriver() {
        WebDriverFactory.releaseDriver(driver);
    }

    @Test
    void shouldWork() {
        // Przejdź do strony Pawla
        driver.navigate().to("https://glovo-planning-poker.herokuapp.com/");

        //znajdz Create random session
        final WebElement randomSelector = driver.findElement(RANDOM_BUTTON_SELECTOR);
        //kliknij create random session
        randomSelector.click();

        //znajdz wksaznik Your name;
        final WebElement nameSelector = driver.findElement(CREATE_NAME_TEXTFIELD_SELECTOR);
        //wpisz imie do text boxa
        nameSelector.sendKeys(nameText);
        //poczekaj az pojawi sie blad
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(visibilityOfElementLocated(ERROR_NAME_SELECTOR));

        //znajdz blad
        final WebElement errorAppears = driver.findElement(ERROR_NAME_SELECTOR);
        //porownaj wynik z oczekiwanym
        assertEquals(errorTExt, errorAppears.getText());

    }
}

