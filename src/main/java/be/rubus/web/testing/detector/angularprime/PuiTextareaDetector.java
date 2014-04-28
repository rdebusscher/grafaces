package be.rubus.web.testing.detector.angularprime;

import be.rubus.web.testing.detector.html5.TextareaDetector;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class PuiTextareaDetector extends TextareaDetector {

    @Override
    public boolean isSupported(WebElement element) {
        return super.isSupported(element) && containsClassName(element, "ui-widget");
    }
}
