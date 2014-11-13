package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.widget.DivSpanWidget;
import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;

/**
 *
 */
public class AccordionPanelTab extends AbstractPrimeFacesWidget {

    private DivSpanWidget panel;

    @PostConstruct
    public void init() {
        WebElement element = root.findElement(By.xpath("following-sibling::div"));

        if (containsClassName(element, "ui-accordion-content")) {
            panel = new DivSpanWidget();
            grafacesContext.initializePageFragment(panel, element, this);

        }
    }



    public String getTitle() {
        return root.getText();
    }

    public boolean isVisible() {
        // TODO we should also check on the root (=title) for the correct icon.
        return panel.isVisible();
    }

    public void toggle() {
        root.click();
    }
}
