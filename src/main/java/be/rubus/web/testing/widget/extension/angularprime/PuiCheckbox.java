package be.rubus.web.testing.widget.extension.angularprime;

import be.rubus.web.testing.annotation.WidgetValidation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PuiCheckbox extends AbstractAngularPrimeWidget {

    @FindBy(xpath = "../..")
    private WebElement container;

    @FindBy(xpath = "../../div[contains(@class, 'pui-chkbox-box')]")
    private WebElement box;

    @FindBy(xpath = "../../div[2]/span[contains(@class, 'pui-chkbox-icon')]")
    private WebElement icon;

    @WidgetValidation
    private boolean isValidWidget() {
        return !root.isDisplayed() && containsClassName(container, PUI_WIDGET);
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(box, PUI_HOVER);
        moveTo(root);
        boolean hover = containsClassName(box, PUI_HOVER);
        return !noHover && hover;
    }

    public void click() {
        box.click();
    }

    public boolean isChecked() {
        return containsClassName(icon, "ui-icon-check");
    }

    public boolean isDisabled() {
        return containsClassName(box, PUI_DISABLED);
    }

    public boolean isVisible() {
        return container.isDisplayed();
    }
}
