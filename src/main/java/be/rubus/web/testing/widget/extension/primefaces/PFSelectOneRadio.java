package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.primefaces.internal.SelectOneRadioItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PFSelectOneRadio extends AbstractPrimeFacesWidget {

    private List<SelectOneRadioItem> items = new ArrayList<SelectOneRadioItem>();

    @PostConstruct
    public void init() {
        List<WebElement> td = root.findElements(By.xpath(".//td"));
        for (int idx = 0; idx < td.size(); idx = idx + 2) {
            WebElement element = td.get(idx).findElement(By.className("ui-radiobutton"));
            SelectOneRadioItem radioItem = new SelectOneRadioItem();
            grafacesContext.initializePageFragment(radioItem, element, this);
            Boolean valid = (Boolean) grafacesContext.executeIsWidgetValid(radioItem);
            if (valid != null && valid) {
                items.add(radioItem);
            }

        }
    }

    @WidgetValidation
    public boolean isWidgetValid() {
        return isHtmlElement(root, "TABLE") && containsClassName(root, "ui-selectoneradio");
    }


    public int getOptionCount() {
        return items.size();
    }

    public String getLabelText(int idx) {
        return items.get(idx).getLabelText();
    }

    public void selectItem(int idx) {
        items.get(idx).click();
    }

    public void selectItemByLabel(int idx) {
        items.get(idx).clickOnLabel();
    }

    public String getSelectedValue() {
        String result = null;
        for (SelectOneRadioItem item : items) {
            if (item.isSelected()) {
                result = item.getValue();
            }
        }
        return result;
    }

    public boolean isDisabled() {
        return items.get(0).isDisabled();
    }

    public boolean hasHoverClassWhenHovered() {
        return items.get(0).hasHoverClassWhenHovered();
    }
}
