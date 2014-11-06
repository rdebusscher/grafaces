package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import be.rubus.web.testing.widget.extension.primefaces.PFDataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 *
 */
public class Column extends AbstractPrimeFacesWidget {
    private WebElement sortIcon;

    @PostConstruct
    public void retrieveElements() {
        List<WebElement> elements = root.findElements(By.className("ui-sortable-column-icon"));
        if (elements.size() == 1) {
            sortIcon = elements.get(0);
        }
    }

    public String getHeaderText() {
        return root.getText();
    }

    public boolean isSortable() {
        return containsClassName(root, "ui-sortable-column");
    }

    public PFDataTable.ColumnSortDirection getSortDirection() {
        PFDataTable.ColumnSortDirection result = null;
        if (isSortable()) {
            String iconName = getIconName(sortIcon);
            if ("carat-2-n-s".equals(iconName)) {
                result = PFDataTable.ColumnSortDirection.NONE;
            }
            if ("triangle-1-n".equals(iconName)) {
                result = PFDataTable.ColumnSortDirection.UP;
            }
            if ("triangle-1-s".equals(iconName)) {
                result = PFDataTable.ColumnSortDirection.DOWN;
            }

            if (result != PFDataTable.ColumnSortDirection.NONE && !root.getAttribute("class").contains("ui-state-active")) {
                Assert.fail("Sort is active but column header isn't marked active");
            }
        }
        return result;
    }

    public void clickHeader() {
        root.click();
    }

}
