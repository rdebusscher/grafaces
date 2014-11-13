package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *
 */
public class AutocompletePanel extends AbstractAngularWidgetsWidget {

    private List<WebElement> getPanelItems() {
        return root.findElements(By.className("pui-autocomplete-item"));
    }

    public int getItemCount() {
        return getPanelItems().size();
    }

    public String getItemText(int idx) {
        return getPanelItems().get(idx).getText();
    }

    public void selectItem(int idx) {
        getPanelItems().get(idx).click();
    }

    public String getHighlightedItem() {
        String result = null;
        for (WebElement element : getPanelItems()) {
            if (containsClassName(element, UI_HIGHLIGHT)) {
                result = element.getText();
            }
        }
        return result;
    }
}
