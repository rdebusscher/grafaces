package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *
 */
public class AutocompleteMultipleItemsContainer extends AbstractAngularWidgetsWidget {

    private List<WebElement> getItems() {
        return root.findElements(By.className("pui-autocomplete-token"));
    }

    public int getItemCount() {
        return getItems().size();
    }

    public String getItemLabel(int idx) {
        AutocompleteMultipleItem item = getAutocompleteMultipleItem(idx);
        return item.getLabel();
    }

    private AutocompleteMultipleItem getAutocompleteMultipleItem(int idx) {
        WebElement element = getItems().get(idx);
        AutocompleteMultipleItem item = new AutocompleteMultipleItem();
        grafacesContext.initializePageFragment(item, element, this);
        return item;
    }

    public void removeItem(int idx) {
        AutocompleteMultipleItem item = getAutocompleteMultipleItem(idx);
        item.remove();
    }
}
