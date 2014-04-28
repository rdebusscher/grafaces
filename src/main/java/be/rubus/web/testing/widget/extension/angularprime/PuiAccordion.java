package be.rubus.web.testing.widget.extension.angularprime;

import be.rubus.web.testing.widget.extension.angularprime.internal.AccordionPanel;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class PuiAccordion extends AbstractAngularPrimeWidget {

    private List<AccordionPanel> panels;

    @PostConstruct
    private void initPanels() {
        if (panels == null) {
            List<WebElement> children = getAllChildren(root);

            panels = new ArrayList<AccordionPanel>();

            Iterator<WebElement> iterator = children.iterator();
            while (iterator.hasNext()) {
                AccordionPanel panel = assemblePanel(iterator);
                panels.add(panel);
            }
        }
    }

    public void identifyPanels() {
        panels = null;
        initPanels();
    }

    public int getPanelCount() {
        return panels.size();
    }

    public int getActivePanel() {
        int result = -1;
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).isActive()) {
                result = i;
                break;
            }
        }
        return result;
    }

    public Integer[] getActivePanels() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).isActive()) {
                result.add(i);
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    public void clickOnHeaderOf(int index) {
        panels.get(index).click();
    }

    public void clickNoWaitOn(int index) {
        panels.get(index).clickNoWait();
    }

    public boolean hasHoverClassWhenHovered(int idx) {
        return panels.get(idx).hasHoverClassWhenHovered();
    }

    private AccordionPanel assemblePanel(Iterator<WebElement> iterator) {
        WebElement header = null;
        while (iterator.hasNext() && header == null) {
            WebElement element = iterator.next();
            if (checkPanelHeader(element)) {
                header = element;
            }
        }
        WebElement content = null;
        while (iterator.hasNext() && content == null) {
            WebElement element = iterator.next();
            if (checkPanelContent(element)) {
                content = element;
            }
        }
        AccordionPanel panel = new AccordionPanel(content);
        grafacesContext.initializePageFragment(panel, header, this);
        return panel;
    }

    private boolean checkPanelContent(WebElement element) {
        return isHtmlElement(element, "DIV") && containsClassName(element, "pui-accordion-content");
    }

    private boolean checkPanelHeader(WebElement element) {
        return isHtmlElement(element, "H3") && containsClassName(element, "pui-accordion-header");
    }
}
