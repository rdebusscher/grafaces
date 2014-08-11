package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class AutocompleteMultipleItem extends AbstractAngularWidgetsWidget {

    @FindBy(className = "pui-autocomplete-token-icon")
    private WebElement icon;

    @FindBy(className = "pui-autocomplete-token-label")
    private WebElement label;

    public String getLabel() {
        return label.getText();
    }

    public void remove() {
        icon.click();
    }
}
