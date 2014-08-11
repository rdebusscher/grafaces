package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class DatatableRow extends AbstractAngularWidgetsWidget {

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
}
