package be.rubus.web.testing.widget.extension.angularprime.internal;

import be.rubus.web.testing.widget.extension.angularprime.AbstractAngularPrimeWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class TabviewPanels extends AbstractAngularPrimeWidget {

    private List<WebElement> panels;

    @PostConstruct
    public void initPanels() {
        panels = root.findElements(By.className("pui-panel"));
    }

    public int getCount() {
        return panels.size();
    }

    public boolean isVisible(int idx) {
        return panels.get(idx).isDisplayed();
    }

    public int getVisiblePanel() {
        int result = -1;
        for (int i = 0; i < panels.size(); i++) {
            if (isVisible(i)) {
                result = i;
            }
        }
        return result;
    }

    public String getHtmlContent(int idx) {
        return (String)((JavascriptExecutor)driver)
                .executeScript("return arguments[0].innerHTML;", panels.get(idx));
    }

    public String getText(int idx) {
        return panels.get(idx).getText();
    }
}
