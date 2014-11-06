package be.rubus.web.testing.detector.primefaces;

import be.rubus.web.testing.detector.GenericDetector;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class PFDataTableDetector extends GenericDetector {
    @Override
    public boolean isSupported(WebElement element) {

        // TODO other tests like ui-datatable wrapper and see if it has columns.
        return containsClassName(element, "ui-datatable");
    }
}
