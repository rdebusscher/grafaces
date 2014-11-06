package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.primefaces.PFDataTableDetector;
import be.rubus.web.testing.widget.extension.primefaces.internal.Column;
import be.rubus.web.testing.widget.extension.primefaces.internal.DatatableRow;
import be.rubus.web.testing.widget.extension.primefaces.internal.Paginator;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PFDataTable extends AbstractPrimeFacesWidget {

    private List<Column> columns = new ArrayList<Column>();
    private List<DatatableRow> rows;
    private Paginator paginator;
    @FindBy(className="ui-datatable-header")
    private GrapheneElement header;
    @FindBy(className="ui-datatable-footer")
    private GrapheneElement footer;

    @WidgetValidation
    public Detector getDetector() {
        return new PFDataTableDetector();
    }

    @PostConstruct
    public void initOptionItems() {
        List<WebElement> headers = root.findElements(By.tagName("th"));
        for (WebElement columnElement : headers) {
            Column column = new Column();
            grafacesContext.initializePageFragment(column, columnElement, this);
            columns.add(column);
        }

        identifyRows();

        List<WebElement> elements = root.findElements(By.className("ui-paginator"));
        if (!elements.isEmpty()) {
            paginator = new Paginator();
            grafacesContext.initializePageFragment(paginator, elements.get(0), this);
        }

    }

    public void identifyRows() {
        rows = new ArrayList<DatatableRow>();
        WebElement tbody = root.findElement(By.tagName("tbody"));
        List<WebElement> rowElements = tbody.findElements(By.tagName("tr"));
        for (WebElement rowElement : rowElements) {
            DatatableRow row = new DatatableRow();
            grafacesContext.initializePageFragment(row, rowElement, this);
            rows.add(row);
        }
    }

    public int getColumnHeaderCount() {
        return columns.size();
    }

    public String getColumnHeader(int idx) {
        return columns.get(idx).getHeaderText();
    }

    public int getNumberOfRows() {
        return rows.size();
    }

    public String getCellValue(int row, int col) {
        return rows.get(row).getCellValue(col);
    }

    public ColumnSortDirection columnSortDirection(int idx) {
        return columns.get(idx).getSortDirection();
    }

    public boolean isRowSelectable() {
        boolean result = !rows.isEmpty();
        if (result) {
            result = rows.get(0).isRowSelectable();
        }
        return result;
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

    public String getHeader() {
        String result = null;
        if (header.isPresent()) {
            result = header.getText();
        }
        return result;
    }

    public String getFooter() {
        String result = null;
        if (footer.isPresent()) {
            result = footer.getText();
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
