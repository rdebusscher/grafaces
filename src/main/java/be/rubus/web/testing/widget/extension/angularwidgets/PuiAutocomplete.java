package be.rubus.web.testing.widget.extension.angularwidgets;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.angularprime.PuiInput;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.AutocompleteMultipleItemsContainer;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.AutocompletePanel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class PuiAutocomplete extends AbstractAngularWidgetsWidget {

    @FindBy(tagName = "INPUT")
    private PuiInput inputElement;


    @FindBy(className = "pui-autocomplete-panel")
    private AutocompletePanel autocompletePanel;

    private AutocompleteMultipleItemsContainer multipleContainer;

    private PuiButton dropdownButton;

    @PostConstruct
    public void retrieveElements() {
        List<WebElement> elements = root.findElements(By.xpath("following-sibling::button"));
        for (WebElement element : elements) {
            if (containsClassName(element, "pui-button") && !element.findElements(By.className("pui-autocomplete-dropdown")).isEmpty()) {
                dropdownButton = new PuiButton();
                grafacesContext.initializePageFragment(dropdownButton, element, this);
            }
        }
        WebElement parent = getParent(root);
        if (containsClassName(parent, "pui-autocomplete-multiple")) {
            multipleContainer = new AutocompleteMultipleItemsContainer();
            grafacesContext.initializePageFragment(multipleContainer, parent, this);
        }
    }

    @WidgetValidation
    public boolean checkWidget() {
        return containsClassName(root, "pui-autocomplete-container") && inputElement.isWidgetValid();
    }

    public boolean hasFieldHoverClassWhenHovered() {
        return inputElement.hasHoverClassWhenHovered();
    }

    public boolean isPanelVisible() {
        return autocompletePanel.isVisible();
    }

    public void type(String value) {
        inputElement.type(value);
    }

    public void sendKeys(Keys... keys) {
        Actions builder = new Actions(driver);
        for (Keys key : keys) {
            builder.sendKeys(key);
        }
        builder.build().perform();
    }

    public String getValue() {
        return inputElement.getValue();
    }

    public int getPanelItemCount() {
        return autocompletePanel.getItemCount();
    }

    public String getPanelItemText(int idx) {
        return autocompletePanel.getItemText(idx);
    }

    public String getPanelHighlightedItemText() {
        return autocompletePanel.getHighlightedItem();

    }

    public void selectPanelItem(int idx) {
        autocompletePanel.selectItem(idx);
    }

    public boolean hasDropdownButton() {
        return dropdownButton != null;
    }

    public void clickDropdownButton() {
        if (dropdownButton != null) {
            dropdownButton.click();
        }
    }

    public boolean isDisabled() {
        boolean result = inputElement.isDisabled();
        if (dropdownButton != null) {
            if (result != dropdownButton.isDisabled()) {
                throw new InconsistentStateException("Disabled state of autocomplete wifget (input field versus button) is not consistent");
            }
        }
        return result;
    }

    public boolean isMultipleSelection() {
        return multipleContainer != null;
    }

    public int getMultipleSelectedCount() {
        return multipleContainer.getItemCount();
    }

    public String getMultipleSelectionItemLabel(int idx) {
        return multipleContainer.getItemLabel(idx);
    }

    public void removeMultipleSelectionItem(int idx) {
        multipleContainer.removeItem(idx);
    }
}
