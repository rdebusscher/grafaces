package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class FieldsetLegend extends AbstractAngularWidgetsWidget {

    private WebElement legendText;

    private WebElement toggleSpan;

    @PostConstruct
    private void retrievElement() {
        List<WebElement> children = getAllChildren(root);
        for (WebElement child : children) {
            if ("pui-fieldset-legend-text".equals(getStyleClasses(child))) {
                legendText = child;
            }
            if (getStyleClasses(child).contains("pui-fieldset-toggler")) {
                toggleSpan = child;
            }
        }
    }

    public String getText() {
        return legendText.getText();
    }

    public boolean hasToggleSpan() {
        return toggleSpan != null;
    }

    public boolean isToggleSpanExpand() {
        return "plusthick".equals(getIconName(toggleSpan));
    }

    public boolean isToggleSpanCollapse() {
        return "minusthick".equals(getIconName(toggleSpan));
    }

    public void clickToggle() {
        toggleSpan.click();
    }
}