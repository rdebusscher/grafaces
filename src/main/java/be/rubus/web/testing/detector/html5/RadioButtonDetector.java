package be.rubus.web.testing.detector.html5;

import org.openqa.selenium.WebElement;

/**
 *
 */
public class RadioButtonDetector extends InputDetector {

    @Override
    public boolean isSupported(WebElement element) {
        return super.isSupported(element) && "radio".equalsIgnoreCase(getAttribute(element, "type"));
    }
}
