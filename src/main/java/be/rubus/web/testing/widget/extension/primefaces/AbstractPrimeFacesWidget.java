package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.AbstractWidget;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class AbstractPrimeFacesWidget extends AbstractWidget {

    protected final String STATE_ERROR = "ui-state-error";

    protected String getIconName(WebElement element) {
        String result = null;
        String[] cssClasses = getStyleClasses(element).split(" ");
        for (String cssClass : cssClasses) {
            if (cssClass.startsWith("ui-icon-")) {
                result = cssClass.substring(8);
               // We can't break.  Datatable column header has more then one icon
            }
        }
        return result;
    }

    protected void waitForAjax() {
        long ajaxCount;
        int loopCount = 50;
        while (loopCount > 0) {
            ajaxCount = (Long) ((JavascriptExecutor) driver).executeScript("return PrimeFaces.ajax.Queue.requests.length");
            if (ajaxCount == 0) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            loopCount--;

        }
        if (loopCount == 0) {
            throw new RuntimeException("Ajax request still running after timeout");
        }
    }
}
