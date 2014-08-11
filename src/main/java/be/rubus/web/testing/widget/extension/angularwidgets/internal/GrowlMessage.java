package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import be.rubus.web.testing.widget.extension.angularwidgets.PuiGrowl.Severity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;

/**
 *
 */
public class GrowlMessage extends AbstractAngularWidgetsWidget {

    @FindBy(className = "pui-growl-icon-close")
    private WebElement closeIcon;

    @FindBy(className = "pui-growl-image")
    private WebElement growlSeverityImage;

    @FindBy(className = "pui-growl-message")
    private WebElement growlMessage;

    @FindBy(className = "pui-growl-title")
    private WebElement growlTitle;

    private String message;

    private Severity severity;

    @PostConstruct
    public void getData() {
        message = growlMessage.findElement(By.tagName("p")).getText();
        severity = GrowlMessage.getSeverity(getStyleClasses(growlSeverityImage));
    }

    public String getTitle() {
        return growlTitle.getText();
    }

    public String getMessage() {
        return message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void close() {
        /*
        Actions builder = new Actions(driver);
        builder.moveToElement(closeIcon).build().perform();
        new WebDriverWait(driver,1).
                until(ExpectedConditions.visibilityOf(closeIcon));

        closeIcon.click();
        */

        /*
        Actions builder = new Actions(driver);
        builder.moveToElement(closeIcon).click().build().perform();
        */

        // Both versions not working, going the JavaScript way
        if (driver instanceof JavascriptExecutor) {

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeIcon);
        } else {
            throw new UnsupportedOperationException("close growl message only works on JavaScript enabled browsers");
        }


    }

    private static Severity getSeverity(String classNames) {
        Severity result = null;
        if (classNames.contains("pui-growl-image-info")) {
            result = Severity.INFO;
        }
        if (classNames.contains("pui-growl-image-error")) {
            result = Severity.ERROR;
        }
        if (classNames.contains("pui-growl-image-warn")) {
            result = Severity.WARN;
        }
        if (classNames.contains("pui-growl-image-fatal")) {
            result = Severity.FATAL;
        }
        return result;
    }
}
