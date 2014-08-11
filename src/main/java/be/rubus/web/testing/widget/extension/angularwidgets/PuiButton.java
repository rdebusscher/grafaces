package be.rubus.web.testing.widget.extension.angularwidgets;

import be.rubus.web.testing.annotation.WidgetValidation;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PuiButton extends AbstractAngularWidgetsWidget {
    @FindBy(className = "pui-button-text")
    private WebElement textSpan;

    @FindBy(className = "ui-icon")
    private GrapheneElement iconSpan;

    @WidgetValidation
    private boolean isValidWidget() {
        boolean result = isHtmlElement(root, "BUTTON");
        if (result) {
            result = getAttribute(root, "type").toLowerCase().equals("button");
        }
        if (result) {
            result = containsClassName(root, "pui-button");
        }
        return result;
    }

    public String getLabel() {
        String result = null;
        if (!containsClassName(root, "pui-button-icon-only")) {
            result = textSpan.getText();
        }
        return result;
    }

    public boolean hasIcon() {
        return iconSpan.isPresent();
    }

    public String getIconName() {
        return iconSpan.isPresent() ? getIconName(iconSpan) : null;
    }

    public void click() {
        root.click();
    }

    public boolean isDisabled() {
        return containsClassName(root, "ui-state-disabled");
    }

    public String getIconPosition() {
        String result = null;
        if (iconSpan.isPresent()) {
            if (containsClassName(iconSpan, "pui-button-icon-right")) {
                result = "right";
            }
            if (containsClassName(iconSpan, "pui-button-icon-left")) {
                result = "left";
            }
        }
        return result;
    }

    public boolean isVisible() {
        return root.isDisplayed();
    }
}
