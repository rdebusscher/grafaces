package be.rubus.web.testing.widget;

import be.rubus.web.testing.AbstractWidget;
import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.html5.TextareaDetector;

/**
 *
 */
public class TextareaWidget extends AbstractWidget {

    @WidgetValidation
    public Detector getDetector() {
        return new TextareaDetector();
    }

}
