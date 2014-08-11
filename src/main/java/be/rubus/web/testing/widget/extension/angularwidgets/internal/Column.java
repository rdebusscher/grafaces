package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

import static be.rubus.web.testing.widget.extension.angularwidgets.PuiDatatable.ColumnSortDirection;

/**
 *
 */
public class Column extends AbstractAngularWidgetsWidget {
    private WebElement sortIcon;

    @PostConstruct
    public void retrieveElements() {
        List<WebElement> elements = root.findElements(By.className("pui-sortable-column-icon"));
        if (elements.size() == 1) {
            sortIcon = elements.get(0);
        }
    }

    public String getHeaderText() {
        return root.getText();
    }

    public boolean isSortable() {
        return containsClassName(root, "pui-sortable-column");
    }

    public ColumnSortDirection getSortDirection() {
        ColumnSortDirection result = null;
        if (isSortable()) {
            String iconName = getIconName(sortIcon);
            if ("carat-2-n-s".equals(iconName)) {
                result = ColumnSortDirection.NONE;
            }
            if ("triangle-1-n".equals(iconName)) {
                result = ColumnSortDirection.UP;
            }
            if ("triangle-1-s".equals(iconName)) {
                result = ColumnSortDirection.DOWN;
            }

            if (result != ColumnSortDirection.NONE && !root.getAttribute("class").contains("ui-state-active")) {
                Assert.fail("Sort is active but column header isn't marked active");
            }
        }
        return result;
    }

    public void clickHeader() {
        root.click();
    }

}
