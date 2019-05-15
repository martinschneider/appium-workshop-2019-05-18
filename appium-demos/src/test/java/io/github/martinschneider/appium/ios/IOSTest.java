package io.github.martinschneider.appium.ios;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.martinschneider.appium.android.swipe.AppiumHelper;
import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class IOSTest {
    AppiumDriver<MobileElement> driver;
    @BeforeClass
    public void setUp() throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformVersion", "11.4");
        caps.setCapability("deviceName", "iPhone 8");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/apps/aws.app");
        caps.setCapability("noReset", Boolean.TRUE);
        driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void enterStringOnTextField() throws InterruptedException {

        MobileElement el2 = (MobileElement) driver.findElementByAccessibilityId("Inputs");
        AppiumHelper.waitForElementVisible(driver, el2);
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeTextView");
        AppiumHelper.waitForElementVisible(driver, el3);
        AppiumHelper.clearTextField(driver, el3);
        String myText = "I love Appium";
        el3.sendKeys(myText);
        assertThat(el3.getAttribute("value"))
                .as("Check the text has displayed or not")
                .isEqualTo(myText);
    }
}