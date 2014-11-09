package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;

/**
 *
 */
public class SelectOneRadioItem extends AbstractPrimeFacesWidget {

    private WebElement hiddenInput;
    private WebElement icon;
    private WebElement label;

    @PostConstruct
    public void init() {
        hiddenInput = root.findElement(By.xpath(".//input"));
        icon = root.findElement(By.className("ui-radiobutton-box"));
        label = root.findElement(By.xpath("../following-sibling::td"));
    }

    public boolean hasHoverClassWhenHovered() {
        boolean noHover = containsClassName(icon, UI_HOVER);
        moveTo(icon);
        boolean hover = containsClassName(icon, UI_HOVER);
        return !noHover && hover;
    }

    public String getLabelText() {
        return label.getText();
    }

    public boolean isSelected() {
        return containsClassName(icon, "ui-state-active");
    }

    public void click() {
        icon.click();
    }

    public void clickOnLabel() {
        label.click();
    }

    public String getValue() {
        return hiddenInput.getAttribute("value");
    }

    public boolean isDisabled() {
        WebElement labelElement = label.findElement(By.tagName("label"));
        boolean labelDisabled = containsClassName(labelElement, UI_DISABLED);
        boolean iconDisabled = containsClassName(icon, UI_DISABLED);
        if (labelDisabled ^ iconDisabled) {
            throw new InconsistentStateException("Icon and label of selectOneRadio not in same enabled state.");
        }
        return iconDisabled;
    }
}
