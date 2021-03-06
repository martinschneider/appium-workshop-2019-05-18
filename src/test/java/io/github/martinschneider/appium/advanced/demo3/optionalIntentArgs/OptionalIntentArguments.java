package io.github.martinschneider.appium.advanced.demo3.optionalIntentArgs;

import static io.github.martinschneider.appium.advanced.secret.Secret.AUTH_PASS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 *
 * <h2>Demo Optional Intent Arguments in Appium</h2>
 *
 * Test: Login with Optional Intent Arguments
 *
 * <p>Learning points:
 *
 * <ul>
 *   <li>The execution time saving with Optional Intent Arguments
 * </ul>
 *
 * @author Syam Sasi, syamsasi99@gmail.com
 */
public class OptionalIntentArguments {

  private String APP_IOS = "/Users/carousell/Desktop/Carousell.app";
  private String AUTH_USER = "tvm.appium.demo";

  @Test
  public void testLoginSlowIOS() throws IOException, InterruptedException {
    IOSModel model = new IOSModel();
    IOSDriver driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), model.caps);
    runStepByStepTest(driver, model);
  }

  @Test
  public void testIntentAgs() throws IOException, InterruptedException {
    IOSModel model = new IOSModel();
    IOSDriver driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), model.procesArCaps);
    runIntentArgTest(driver, model);
  }

  private void runStepByStepTest(AppiumDriver driver, Model model) throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, 10);

    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(model.loginScreen)).click();
      wait.until(ExpectedConditions.presenceOfElementLocated(model.username)).sendKeys(AUTH_USER);
      wait.until(ExpectedConditions.presenceOfElementLocated(model.password)).sendKeys(AUTH_PASS);
      wait.until(ExpectedConditions.presenceOfElementLocated(model.loginBtn)).click();

      Thread.sleep(5000);
    } finally {
      driver.quit();
    }
  }

  private void runIntentArgTest(AppiumDriver driver, Model model) throws InterruptedException {
    try {
      Thread.sleep(5000);
    } finally {
      driver.quit();
    }
  }

  public abstract class Model {
    public By loginScreen = MobileBy.AccessibilityId("welcome_page_login_button");
    public By loginBtn = MobileBy.AccessibilityId("login_page_login_button");
    public By username;
    public By password;

    public DesiredCapabilities caps;
    public DesiredCapabilities procesArCaps;
  }

  public class IOSModel extends Model {
    IOSModel() {
      username = MobileBy.AccessibilityId("login_page_username_text_field");
      password = MobileBy.AccessibilityId("login_page_password_text_field");

      caps = new DesiredCapabilities();
      caps.setCapability("platformName", "iOS");
      caps.setCapability("deviceName", "iPhone 8");
      caps.setCapability("platformVersion", "11.4");
      caps.setCapability("app", APP_IOS);

      procesArCaps = new DesiredCapabilities();
      procesArCaps.setCapability("platformName", "iOS");
      procesArCaps.setCapability("deviceName", "iPhone 8");
      procesArCaps.setCapability("platformVersion", "11.4");
      procesArCaps.setCapability("app", APP_IOS);
      procesArCaps.setCapability(
          "processArguments", String.format("{\"args\": [\"%s\",\"%s\"]}", AUTH_USER, AUTH_PASS));
    }
  }
}
