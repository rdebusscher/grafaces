package be.rubus.web.testing.widget;

import be.rubus.web.testing.AbstractWidget;
import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.html5.RadioButtonDetector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RadioButtonGroupWidget extends AbstractWidget {

    private List<RadioButtonWidget> buttons;

    @PostConstruct
    public void init() {
        if (buttons == null) {
            List<WebElement> elements = driver.findElements(By.name(root.getAttribute("name")));

            buttons = new ArrayList<RadioButtonWidget>();
            for (WebElement element : elements) {
                RadioButtonWidget radiobutton = new RadioButtonWidget();
                grafacesContext.initializePageFragment(radiobutton, element, this);
                Boolean valid = (Boolean) grafacesContext.executeIsWidgetValid(radiobutton);
                if (valid != null && valid) {
                    buttons.add(radiobutton);
                }
            }

        }
    }

    @WidgetValidation
    public Detector getDetector() {
        // The root must be a valid radio button before the group can be valid
        return new RadioButtonDetector();
    }

    public int getNumberOfButtons() {
        return buttons.size();
    }


    public void clickButton(int idx) {
        buttons.get(idx).click();
    }

    public String getSelectedValue() {
        String result = null;
        for (RadioButtonWidget button : buttons) {
            if (button.isSelected()) {
                result = button.getValue();
            }
        }
        return result;
    }

}
