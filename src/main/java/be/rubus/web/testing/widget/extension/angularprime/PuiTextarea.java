package be.rubus.web.testing.widget.extension.angularprime;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PuiTextarea extends AbstractAngularPrimeWidget {

    private WebElement popupPanel;

    public void type(String value) {
        root.clear();
        root.sendKeys(value);
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(root, PUI_HOVER);
        moveTo(root);
        boolean hover = containsClassName(root, PUI_HOVER);
        return !noHover && hover;
    }

    public boolean isDisabled() {
        return containsClassName(root, PUI_DISABLED);
    }

    public String getHeight() {
        return getComputedCssValue(root, "height");
    }

    public boolean isAngularJSInvalid() {
        return containsClassName(root, NG_INVALID);
    }

    public boolean isAngularJSValid() {
        return containsClassName(root, NG_VALID);
    }

    public String getValue() {
        return root.getAttribute(VALUE);
    }

    public boolean hasAutoComplete() {
        popupPanel = getNextSibling(root);
        if (popupPanel != null && !containsClassName(popupPanel, "pui-autocomplete-panel")) {
            popupPanel = null;
        }
        return popupPanel != null;
    }

    public void waitForPopupPanel() {
        waitUntilVisibilityOf(popupPanel);
    }

    public List<String> getAutocompleteSuggestions() {
        List<String> result = new ArrayList<String>();
        for (WebElement item : getSuggestionElements()) {
            result.add(item.getText());
        }

        return result;
    }

    private List<WebElement> getSuggestionElements() {
        return popupPanel.findElements(By.className("pui-autocomplete-item"));
    }

    public void selectSuggestion(int idx) {
        List<WebElement> items = getSuggestionElements();
        items.get(idx).click();

    }

    public boolean isSuggestionPanelVisible() {
        return popupPanel.isDisplayed();
    }
}
