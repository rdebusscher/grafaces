package be.rubus.web.testing.widget.extension.angularprime.internal;

import be.rubus.web.testing.widget.extension.angularprime.AbstractAngularPrimeWidget;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class AccordionPanel extends AbstractAngularPrimeWidget {

    // root contains the header

    @FindBy(tagName = "span")
    private WebElement headerIcon;

    private WebElement content;

    public AccordionPanel(WebElement content) {
        this.content = content;
    }

    public boolean isActive() {
        Boolean result = null;
        boolean colored = containsClassName(root, "ui-state-active");
        boolean arrowDown = containsClassName(headerIcon, "ui-icon-triangle-1-s");
        boolean arrowLeft = containsClassName(headerIcon, "ui-icon-triangle-1-e");
        boolean contentVisible = content.isDisplayed();
        if (colored && arrowDown && !arrowLeft && contentVisible) {
            result = true;
        }
        if (!colored && !arrowDown && arrowLeft && !contentVisible) {
            result = false;
        }
        if (result == null) {
            Assert.fail("Inconsistent state of the active property of accordion (colored, arrowDown, arrowLeft, " +
                    "contentVisible) " + colored + "-" + arrowDown + "-" + arrowLeft + "-" + contentVisible);
        }
        return result;
    }

    public void click() {
        boolean nowActive = isActive();
        super.click();
        if (!nowActive) {
            waitUntilVisibilityOf(content);
        } else {
            waitUntilHiddenOf(content);
        }

        // Otherwise checks of active are in an inconsistent state
        waitForScreenUpdate(500);
    }

    public void clickNoWait() {
        root.click();
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(root, PUI_HOVER);
        moveTo(root);
        boolean hover = containsClassName(root, PUI_HOVER);
        return !noHover && hover;
    }
}
