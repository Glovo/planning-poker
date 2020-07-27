package com.glovoapp.planningpoker.e2e;


import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public final class WebDriverFactory implements TestExecutionListener {

    private final static LinkedBlockingQueue<WebDriver> WEB_DRIVERS_IN_USE = new LinkedBlockingQueue<>();
    private final static LinkedBlockingQueue<WebDriver> AVAILABLE_WEB_DRIVERS = new LinkedBlockingQueue<>();

    public static WebDriver getDriver() {
        final WebDriver driver = Optional.of(AVAILABLE_WEB_DRIVERS)
                .map(LinkedBlockingQueue::poll)
                .orElseGet(WebDriverFactory::createNewDriver);

        try {
            WEB_DRIVERS_IN_USE.put(driver);
        } catch (final InterruptedException puttingException) {
            throw new RuntimeException("cannot put WebDriver to in use queue", puttingException);
        }

        return driver;
    }

    public static void releaseDriver(final WebDriver driver) {
        driver.navigate().to("about:blank");
        driver.manage().deleteAllCookies();

        if (WEB_DRIVERS_IN_USE.remove(driver)) {
            try {
                AVAILABLE_WEB_DRIVERS.put(driver);
            } catch (final InterruptedException releaseException) {
                throw new RuntimeException("failed to release driver", releaseException);
            }
        } else {
            throw new RuntimeException("cannot release driver " + driver + ", not in use");
        }

    }

    private static WebDriver createNewDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/bin/webdriver/chromedriver.exe");
        return new ChromeDriver();
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        Stream.concat(
                WEB_DRIVERS_IN_USE.stream(),
                AVAILABLE_WEB_DRIVERS.stream()
        ).peek(WEB_DRIVERS_IN_USE::remove)
                .peek(AVAILABLE_WEB_DRIVERS::remove)
                .forEach(WebDriver::quit);
    }

}