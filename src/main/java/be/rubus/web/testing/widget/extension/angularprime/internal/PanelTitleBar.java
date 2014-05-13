package be.rubus.web.testing.widget.extension.angularprime.internal;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.angularprime.AbstractAngularPrimeWidget;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class PanelTitleBar extends AbstractAngularPrimeWidget {

    @FindBy(className = "ui-panel-title")
    private GrapheneElement title;


    private WebElement collapseExpandButton;

    private WebElement closeButton;

    @PostConstruct
    private void retrieveButtons() {
        List<WebElement> buttons = root.findElements(By.className("pui-panel-titlebar-icon"));
        for (WebElement button : buttons) {
            String iconName = defineIcon(button);

            if ("closethick".equals(iconName)) {
                closeButton = button;
            }
            if ("plusthick".equals(iconName)) {
                collapseExpandButton = button;
            }
            if ("minusthick".equals(iconName)) {
                collapseExpandButton = button;
            }
        }
    }

    private String defineIcon(WebElement button) {
        String iconName = null;
        List<WebElement> children = getAllChildren(button);
        if (children.size() == 1) {
            // Should only be one child, do we need to test for it?
            iconName = getIconName(children.get(0));
        }
        return iconName;
    }

    @WidgetValidation
    public boolean isValidWidget() {
        return true;  // In the title less situation, there is no check we can do.
    }

    public boolean isDisplayed() {
        try {
            return root.isDisplayed();
        } catch (NoSuchElementException e) {
            // For the title less case.  GrapheneElement on @Root isn't working.
            return false;
        }
    }

    public boolean isTitleDisplayed() {
        return title.isDisplayed();
    }

    public String getTitle() {
        return title.getText();
    }

    public boolean isCollapseable(){
        return collapseExpandButton != null;
    }

    public boolean isCloseable() {
        return closeButton != null;
    }

    public boolean  isButtonStateExpanded() {
        return collapseExpandButton.isDisplayed() && "minusthick".equals(defineIcon(collapseExpandButton));
    }

    public boolean  isButtonStateCollapsed() {
        return collapseExpandButton.isDisplayed() && "plusthick".equals(defineIcon(collapseExpandButton));
    }

    public void clickCollapseExpandButton() {
        collapseExpandButton.click();
    }

    public void clickCloseButton() {
        closeButton.click();
    }


}