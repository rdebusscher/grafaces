package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.primefaces.PFCheckboxDetector;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PFSelectBooleanCheckbox extends AbstractPrimeFacesWidget {

    @FindBy(className = "ui-chkbox-box")
    private WebElement checkboxBox;

    @FindBy(className = "ui-chkbox-icon")
    private WebElement iconSpan;


    @WidgetValidation
    public Detector getDetector() {
        return new PFCheckboxDetector();
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(checkboxBox, UI_HOVER);
        moveTo(checkboxBox);
        boolean hover = containsClassName(checkboxBox, UI_HOVER);
        return !noHover && hover;
    }

    public void click() {
        checkboxBox.click();
    }

    public boolean isChecked() {
        return containsClassName(iconSpan, "ui-icon-check");
    }

    public boolean isDisabled() {
        return containsClassName(checkboxBox, UI_DISABLED);
    }

}
