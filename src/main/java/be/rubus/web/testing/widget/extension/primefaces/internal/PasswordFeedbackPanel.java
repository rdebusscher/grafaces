package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import be.rubus.web.testing.widget.extension.primefaces.PFPassword;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;

/**
 *
 */
public class PasswordFeedbackPanel extends AbstractPrimeFacesWidget {

    private WebElement meter;
    private WebElement message;

    @PostConstruct
    public void init() {
        meter = root.findElement(By.className("ui-password-meter"));
        message = root.findElement(By.className("ui-password-info"));
    }

    public String geText() {
        return message.getText();
    }

    public PFPassword.FeedbackColor getPanelColor() {
        String[] values = getComputedCssValue(meter, "background-position").split(" ");
        // TODO check if we have the correct number of items in the array
        return PFPassword.FeedbackColor.getInstance(values[1]);
    }

    // TODO make this a more generic method, a couple of times used already.
    public boolean isVisible() {
        return root.isDisplayed();
    }
}
