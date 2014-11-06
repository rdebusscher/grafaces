package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.primefaces.PFSelectOneMenuDetector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class PFSelectOneMenu extends AbstractPrimeFacesWidget {

    @FindBy(className = "ui-selectonemenu-label")
    private WebElement selectedOptionLabel;

    @FindBy(className = "ui-selectonemenu-trigger")
    private WebElement button;

    private WebElement panel;

    @WidgetValidation
    public Detector getDetector() {
        // TODO check also for the button
        return new PFSelectOneMenuDetector();
    }

    @PostConstruct
    public void initOptionItems() {
        String panelId = getId(root) + "_panel";
        List<WebElement> elements = driver.findElements(By.id(panelId));
        if (elements.size() == 1) {
            panel = elements.get(0);
        }
        // TODO exception when panel not found
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(root, UI_HOVER);
        moveTo(root);
        boolean hover = containsClassName(root, UI_HOVER);
        return !noHover && hover;
    }

    public String getSelectedOptionLabel() {
        return selectedOptionLabel.getText();
    }

    public void openOptions() {
        button.click();
    }

    public int getOptionCount() {
        return panel.findElements(By.className("ui-selectonemenu-item")).size();
    }

    public String getOptionLabel(int idx) {
        String result = null;
        List<WebElement> elements = panel.findElements(By.className("ui-selectonemenu-item"));
        if (idx <= elements.size()) {
            result = elements.get(idx).getText();
        }
        return result;
    }

    public void clickOnOptionLabel(int idx) {
        List<WebElement> elements = panel.findElements(By.className("ui-selectonemenu-item"));
        if (idx <= elements.size()) {
            elements.get(idx).click();
        }
    }

    public boolean isDisabled() {
        return containsClassName(root, UI_DISABLED);
    }
}
