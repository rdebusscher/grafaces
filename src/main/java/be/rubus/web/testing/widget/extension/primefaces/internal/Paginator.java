package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import be.rubus.web.testing.widget.extension.primefaces.PFDataTable;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class Paginator extends AbstractPrimeFacesWidget {
    private WebElement firstButton;
    private WebElement previousButton;
    private WebElement nextButton;
    private WebElement lastButton;
    private WebElement pages;

    @PostConstruct
    public void retrieveButtons() {
        List<WebElement> elements = getAllChildren(root);
        for (WebElement element : elements) {
            if (containsClassName(element, "ui-paginator-first")) {
                firstButton = element;
            }
            if (containsClassName(element, "ui-paginator-prev")) {
                previousButton = element;
            }
            if (containsClassName(element, "ui-paginator-pages")) {
                pages = element;
            }
            if (containsClassName(element, "ui-paginator-next")) {
                nextButton = element;
            }
            if (containsClassName(element, "ui-paginator-last")) {
                lastButton = element;
            }
        }
    }

    public boolean isButtonActive(PFDataTable.PaginatorButton paginatorButton) {
        boolean result;
        switch (paginatorButton) {

            case FIRST:
                result = !containsClassName(firstButton, UI_DISABLED);
                break;
            case PREVIOUS:
                result = !containsClassName(previousButton, UI_DISABLED);
                break;
            case NEXT:
                result = !containsClassName(nextButton, UI_DISABLED);
                break;
            case LAST:
                result = !containsClassName(lastButton, UI_DISABLED);
                break;
            default:
                throw new IllegalArgumentException(paginatorButton + " not recognized");
        }
        return result;
    }

    public void clickButton(PFDataTable.PaginatorButton paginatorButton) {
        switch (paginatorButton) {

            case FIRST:
                firstButton.click();
                break;
            case PREVIOUS:
                previousButton.click();
                break;
            case NEXT:
                nextButton.click();
                break;
            case LAST:
                lastButton.click();
                break;
        }
        waitForAjax();
    }

    public String getPageNumber() {
        String result = "";
        List<WebElement> elements = getAllChildren(pages);
        for (WebElement element : elements) {
            if (containsClassName(element, UI_ACTIVE)) {
                result = element.getText();
            }
        }
        return result;
    }
}
