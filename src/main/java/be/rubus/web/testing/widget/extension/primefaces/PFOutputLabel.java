package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;

/**
 *
 */
public class PFOutputLabel extends AbstractPrimeFacesWidget {

    @WidgetValidation
    public boolean isValid() {
        return "label".equals(root.getTagName()) && containsClassName(root, "ui-outputlabel");
    }

    public String getText() {
        return root.getText();
    }

    public boolean hasErrorIndication() {
        return containsClassName(root, STATE_ERROR);
    }

}
