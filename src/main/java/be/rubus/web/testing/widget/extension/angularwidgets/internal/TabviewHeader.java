package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class TabviewHeader extends AbstractAngularWidgetsWidget {

    @FindBy(tagName = "a")
    private WebElement link;

    private WebElement icon;

    @PostConstruct
    private void checkIcons() {
        List<WebElement> icons = root.findElements(By.className("ui-icon"));
        if (icons.size() > 1) {
            throw new RuntimeException("At maximum one icon supported on Tabview tab"); // FIXME
        }
        if (icons.size() > 0) {
            icon = icons.get(0);
        }
    }

    public String getLinkText() {
        return link.getText();
    }

    public void click() {
        link.click();
    }

    public boolean isSelected() {
        boolean isSelected = containsClassName(root, "pui-tabview-selected");
        boolean isActive = containsClassName(root, "ui-state-active");
        if (isSelected && !isActive) {
            throw new InconsistentStateException("TabView header should have both pui-tabview-selected and ui-state-active set or none of them");
        }
        if (!isSelected && isActive) {
            throw new InconsistentStateException("TabView header should have both pui-tabview-selected and ui-state-active set or none of them");
        }
        return isSelected;
    }

    public boolean hasCloseIcon() {
        boolean result = icon != null;
        if (result) {
            String iconName = getIconName(icon);
            if (!"close".equals(iconName)) {
                throw new InconsistentStateException("IconName for TabviewTab should be close but it is " + iconName);
            }
        }
        return result;

    }

    public void clickOnCloseIcon() {
        icon.click();
    }

    protected Point getLocation() {
        return root.getLocation();
    }
}
