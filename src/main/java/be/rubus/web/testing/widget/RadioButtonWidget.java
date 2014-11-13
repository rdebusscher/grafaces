package be.rubus.web.testing.widget;

import be.rubus.web.testing.AbstractWidget;
import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.html5.RadioButtonDetector;

/**
 *
 */
public class RadioButtonWidget extends AbstractWidget {

    @WidgetValidation
    public Detector getDetector() {
        return new RadioButtonDetector();
    }

    public String getValue() {
        return root.getAttribute("value");
    }

    public boolean isSelected() {
        return "true".equalsIgnoreCase(root.getAttribute("checked"));
    }
}
