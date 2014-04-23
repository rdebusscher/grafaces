/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package be.rubus.web.testing.widget.extension.angularprime;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.angularprime.internal.PuiRadiobutton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PuiRadiobuttonGroup extends AbstractAngularPrimeWidget {


    @FindBy(xpath = "../..")
    private WebElement container;

    private List<PuiRadiobutton> buttons;

    @PostConstruct
    private void identifyButtons() {
        if (buttons == null) {
            List<WebElement> elements = driver.findElements(By.name(root.getAttribute("name")));

            buttons = new ArrayList<PuiRadiobutton>();
            for (WebElement element : elements) {
                PuiRadiobutton radiobutton = new PuiRadiobutton();
                grafacesContext.initializePageFragment(radiobutton, element, this);
                buttons.add(radiobutton);
            }
        }
    }

    @WidgetValidation
    private boolean isValidWidget() {
        // TODO check if root is actually a radiobutton
        return !root.isDisplayed() && containsClassName(container, PUI_WIDGET);
    }

    public int getNumberOfButtons() {
        return buttons.size();
    }

    public boolean hasHoverClassWhenHovered() {
        boolean result = true;
        for (PuiRadiobutton button : buttons) {
            result = result && button.hasHoverClassWhenHovered();
        }
        return result;
    }

    public void clickButton(int idx) {
        buttons.get(idx).click();
    }

    public String getSelectedValue() {
        String result = null;
        for (PuiRadiobutton button : buttons) {
            if (button.isSelected()) {
                result = button.getValue();
            }
        }
        return result;
    }

    public boolean isButtonDisabled(int idx) {
        return buttons.get(idx).isDisabled();
    }

    public boolean isButtonVisible(int idx) {
        return buttons.get(idx).isVisible();

    }
}
