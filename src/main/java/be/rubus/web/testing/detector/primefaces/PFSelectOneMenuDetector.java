package be.rubus.web.testing.detector.primefaces;

import be.rubus.web.testing.detector.GenericDetector;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class PFSelectOneMenuDetector extends GenericDetector {
    @Override
    public boolean isSupported(WebElement element) {
        return isHtmlElement(element, "DIV") && containsClassName(element, "ui-widget")
                && containsClassName(element, "ui-selectonemenu");
    }
}
