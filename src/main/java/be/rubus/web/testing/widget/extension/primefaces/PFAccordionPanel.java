package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.primefaces.internal.AccordionPanelTab;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PFAccordionPanel extends AbstractPrimeFacesWidget {

    private List<AccordionPanelTab> tabs = new ArrayList<AccordionPanelTab>();

    @PostConstruct
    public void init() {
        List<WebElement> titleElements = root.findElements(By.tagName("h3"));
        for (WebElement titleElement : titleElements) {
            AccordionPanelTab tab = new AccordionPanelTab();
            grafacesContext.initializePageFragment(tab, titleElement, this);
            tabs.add(tab);
        }
    }

    @WidgetValidation
    public boolean isValidWidget() {
        return isHtmlElement(root, "DIV") && containsClassName(root, "ui-accordion");
    }


    public int getPanelCount() {
        return tabs.size();
    }

    public String getTitle(int idx) {
        return tabs.get(idx).getTitle();
    }

    public boolean isVisible(int idx) {
        return tabs.get(idx).isVisible();
    }

    public void toggle(int idx) {
        tabs.get(idx).toggle();
        waitForScreenUpdate(500);
    }
}
