package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.primefaces.PFInputDetector;

/**
 *
 */
public class PFInputText extends AbstractPrimeFacesWidget {

    @WidgetValidation
    public Detector getDetector() {
        return new PFInputDetector();
    }

    public void type(String value) {
        root.clear();
        root.sendKeys(value);
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(root, UI_HOVER);
        moveTo(root);
        boolean hover = containsClassName(root, UI_HOVER);
        return !noHover && hover;
    }

    public boolean isDisabled() {
        return containsClassName(root, UI_DISABLED);
    }

    public String getValue() {
        return root.getAttribute(VALUE);
    }

    public String getType() {
        return getAttribute(root, "type");
    }

    public boolean hasErrorIndication() {
        return containsClassName(root, STATE_ERROR);
    }
}
