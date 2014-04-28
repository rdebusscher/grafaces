package be.rubus.web.testing.detector.html5;

import be.rubus.web.testing.detector.GenericDetector;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class TextareaDetector extends GenericDetector {

    @Override
    public boolean isSupported(WebElement element) {
        return isHtmlElement(element, "TEXTAREA");
    }
}
