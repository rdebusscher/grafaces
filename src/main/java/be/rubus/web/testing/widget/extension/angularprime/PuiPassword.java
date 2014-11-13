package be.rubus.web.testing.widget.extension.angularprime;

import be.rubus.web.testing.annotation.WidgetValidation;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class PuiPassword extends AbstractAngularPrimeWidget {


    private WebElement passwordPanel;

    @WidgetValidation
    private boolean isValidWidget() {
        return containsClassName(root, PUI_WIDGET);
    }

    @PostConstruct
    private void getReferenceToPanel() {
        if (passwordPanel == null) {
            List<WebElement> elements = root.findElements(By.xpath("following-sibling::div"));
            for (WebElement element : elements) {
                if (containsClassName(element, "pui-password-panel")) {
                    passwordPanel = element;
                }
            }
        }
    }

    public boolean hasPasswordPanel() {
        return passwordPanel != null;
    }

    public boolean isPanelVisible() {
        checkPasswordPanel();
        return passwordPanel.isDisplayed();
    }

    private void checkPasswordPanel() {
        if (passwordPanel == null) {
            throw new NoSuchElementException("Panel not available");
        }
    }

    public boolean isPanelPopupType() {
        checkPasswordPanel();
        return containsClassName(passwordPanel, "pui-password-panel-overlay");
    }

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

    public String getValue() {
        return root.getAttribute("value");
    }

    public void blur() {
        blur(root);
    }

    public String getPanelText() {
        checkPasswordPanel();
        return passwordPanel.findElement(By.className("pui-password-info")).getText();
    }

    public FeedbackColor getPanelColor() {
        checkPasswordPanel();
        WebElement colorElement = passwordPanel.findElement(By.className("pui-password-meter"));
        String[] values = getComputedCssValue(colorElement, "background-position").split(" ");
        // TODO check if we have the correct number of items in the array
        return FeedbackColor.getInstance(values[1]);
    }

    public boolean isAngularJSInvalid() {
        return containsClassName(root, NG_INVALID);
    }

    public boolean isAngularJSValid() {
        return containsClassName(root, NG_VALID);
    }

    public static enum FeedbackColor {
        NONE("0px"), RED("-10px"), ORANGE("-20px"), GREEN("-30px");

        private String position;

        FeedbackColor(String somePosition) {
            position = somePosition;
        }

        static FeedbackColor getInstance(String currentPosition) {
            FeedbackColor result = null;
            for (FeedbackColor item : FeedbackColor.values()) {
                if (item.position.equals(currentPosition)) {
                    result = item;
                }
            }
            return result;
        }
    }
}
