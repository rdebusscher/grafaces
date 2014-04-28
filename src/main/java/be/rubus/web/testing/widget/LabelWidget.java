package be.rubus.web.testing.widget;

import be.rubus.web.testing.AbstractWidget;
import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.html5.LabelDetector;

/**
 *
 */
public class LabelWidget extends AbstractWidget {

    @WidgetValidation
    public Detector getDetector() {
        return new LabelDetector();
    }

}
