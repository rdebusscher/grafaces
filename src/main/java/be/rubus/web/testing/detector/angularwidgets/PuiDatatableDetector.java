package be.rubus.web.testing.detector.angularwidgets;

import be.rubus.web.testing.detector.GenericDetector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class PuiDatatableDetector extends GenericDetector {
    @Override
    public boolean isSupported(WebElement element) {
        return containsClassName(element, "pui-datatable") && element.findElement(By.tagName("table")) != null;
    }
}
