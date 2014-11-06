package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class DatatableRow extends AbstractPrimeFacesWidget {
    private List<WebElement> cells;

    @PostConstruct
    public void retrieveElements() {
        cells = root.findElements(By.tagName("td"));
    }

    public String getCellValue(int col) {
        return cells.get(col).getText();
    }

    public void click(Keys[] keys) {
        click(root, keys);
    }

    public boolean isRowSelected() {
        return containsClassName(root, "ui-state-highlight");
    }

    public boolean isRowSelectable() {
        return containsClassName(root, "ui-datatable-selectable");
    }
}

