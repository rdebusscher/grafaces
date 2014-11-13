package be.rubus.web.testing.widget.extension.angularwidgets;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.angularwidgets.PuiDatatableDetector;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.Column;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.DatatableRow;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.Paginator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PuiDatatable extends AbstractAngularWidgetsWidget {

    private List<Column> columns = new ArrayList<Column>();
    private List<DatatableRow> rows;
    private Paginator paginator;
    private WebElement caption;

    @PostConstruct
    public void retrieveElements() {
        waitForAjax();
        List<WebElement> headers = root.findElements(By.tagName("th"));
        for (WebElement columnElement : headers) {
            Column column = new Column();
            grafacesContext.initializePageFragment(column, columnElement, this);
            columns.add(column);
        }

        identifyRows();
        List<WebElement> elements = root.findElements(By.xpath("following-sibling::div"));
        for (WebElement element : elements) {

            if (containsClassName(element, "pui-paginator")) {
                paginator = new Paginator();
                grafacesContext.initializePageFragment(paginator, element, this);
            }
        }

        List<WebElement> captionElements = root.findElements(By.tagName("caption"));
        if (!captionElements.isEmpty()) {
            caption = captionElements.get(0);
        }

    }

    public void identifyRows() {
        rows = new ArrayList<DatatableRow>();
        List<WebElement> rowElements = root.findElements(By.tagName("tr"));
        for (WebElement rowElement : rowElements) {
            DatatableRow row = new DatatableRow();
            grafacesContext.initializePageFragment(row, rowElement, this);
            rows.add(row);
        }
    }

    @WidgetValidation
    public Detector getDetector() {
        return new PuiDatatableDetector();
    }

    public int getColumnCount() {
        return columns.size();
    }

    public String getColumnHeader(int idx) {
        return columns.get(idx).getHeaderText();
    }

    public boolean isSortable(int idx) {
        return columns.get(idx).isSortable();
    }

    public int getRowCount() {
        return rows.size();
    }

    public String getCellValue(int row, int col) {
        return rows.get(row).getCellValue(col);
    }

    public ColumnSortDirection columnSortDirection(int idx) {
        return columns.get(idx).getSortDirection();
    }

    public void clickHeader(int idx) {
        columns.get(idx).clickHeader();
        // sorting throws away the tbody DOM elements and thus we need to reattach them.
        identifyRows();
    }

    public void clickRow(int idx, Keys... keys) {
        rows.get(idx).click(keys);
    }

    public List<Integer> getSelectedRowNumber() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).isRowSelected()) {
                result.add(i);
            }
        }
        return result;
    }

    public boolean hasPaginator() {
        return paginator != null;
    }

    public boolean isPaginatorButtonActive(PaginatorButton paginatorButton) {
        boolean result = false;
        if (hasPaginator()) {
            result = paginator.isButtonActive(paginatorButton);
        }
        return result;
    }

    public void clickPaginatorButton(PaginatorButton paginatorButton) {
        if (hasPaginator()) {
            paginator.clickButton(paginatorButton);
            identifyRows();
        }

    }

    public String getPaginatorPageNumber() {
        if (hasPaginator()) {
            return paginator.getPageNumber();
        }
        return null;
    }

    public String getCaption() {
        String result = null;
        if (caption != null) {
            result = caption.getText();
        }
        return result;
    }

    public static enum ColumnSortDirection {
        NONE, UP, DOWN
    }

    public static enum PaginatorButton {
        FIRST, PREVIOUS, NEXT, LAST
    }

}
