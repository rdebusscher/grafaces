package be.rubus.web.testing.widget.extension.angularwidgets;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.FieldsetLegend;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PuiFieldset extends AbstractAngularWidgetsWidget {

    @FindBy(className = "pui-fieldset-legend")
    private FieldsetLegend legend;

    @FindBy(className = "pui-fieldset-content")
    private WebElement content;

    @WidgetValidation
    public boolean isValidWidget() {
        return root.getTagName().equals("fieldset") && containsClassName(root, "ui-widget");
    }

    public String getLegendText() {
        return legend.getText();
    }

    public boolean isToggleable() {
        return legend.hasToggleSpan();
    }

    public boolean isContentDisplayed() {
        boolean result = content.isDisplayed();
        if (isToggleable()) {
            if (legend.isToggleSpanCollapse()) {
                if (!result) {
                    throw new InconsistentStateException("Fieldset content is displayed but button indicates otherwise");
                }
            } else {
                if (result) {
                    throw new InconsistentStateException("Fieldset content is not displayed but button indicates otherwise");
                }
            }
        }
        return result;
    }

    public void clickToggle() {
        legend.clickToggle();
    }
}
