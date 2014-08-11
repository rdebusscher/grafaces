package be.rubus.web.testing.widget.extension.angularprime;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.angularprime.internal.PanelTitleBar;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PuiPanel extends AbstractAngularPrimeWidget {

    @FindBy(className = "pui-panel-titlebar")
    private PanelTitleBar titleBar;

    @FindBy(className = "pui-panel-content")
    private WebElement content;

    @WidgetValidation
    public boolean isValidWidget() {
        return containsClassName(root, "ui-widget");
    }

    public String getTitle() {
        return titleBar.getTitle();
    }

    public boolean isContentDisplayed() {
        if (titleBar.isCollapseable()) {
            if (content.isDisplayed() && titleBar.isButtonStateCollapsed()) {
                throw new InconsistentStateException("Panel content is displayed but button indicates that it should be collapsed");
            }
            if (!content.isDisplayed() && titleBar.isButtonStateExpanded()) {
                throw new InconsistentStateException("Panel content is not displayed but button indicates that it should be expanded");
            }
        }
        return content.isDisplayed();
    }

    public boolean isTitlebarDisplayed() {
        return titleBar.isDisplayed();
    }

    public boolean isTitleDisplayed() {
        return titleBar.isTitleDisplayed();
    }

    public boolean isCollapsable() {
        return titleBar.isCollapseable();
    }

    public boolean isButtonForExpandVisible() {
        return titleBar.isButtonStateCollapsed();
    }

    public boolean isButtonForCollapseVisible() {
        return titleBar.isButtonStateExpanded();
    }

    public boolean isCloseable() {
        return titleBar.isCloseable();
    }

    public void toggleCollapse() {
        titleBar.clickCollapseExpandButton();
    }

    public void closePanel() {
        titleBar.clickCloseButton();
    }
}
