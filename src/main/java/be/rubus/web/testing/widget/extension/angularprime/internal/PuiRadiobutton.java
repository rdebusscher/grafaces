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
package be.rubus.web.testing.widget.extension.angularprime.internal;

import be.rubus.web.testing.widget.extension.angularprime.AbstractAngularPrimeWidget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.fail;

/**
 *
 */
public class PuiRadiobutton extends AbstractAngularPrimeWidget {

    @FindBy(xpath = "../..")
    private WebElement container;

    @FindBy(xpath = "../../..")
    private WebElement directive;

    @FindBy(xpath = "../../div[contains(@class, 'pui-radiobutton-box')]")
    private WebElement box;

    @FindBy(xpath = "../../div[2]/span[contains(@class, 'pui-radiobutton-icon')]")
    private WebElement icon;

    public boolean hasHoverClassWhenHovered() {
        WebElement box = getRadiobuttonBox();
        boolean noHover = containsClassName(box, PUI_HOVER);
        moveTo(container);
        boolean hover = containsClassName(box, PUI_HOVER);
        return !noHover && hover;
    }


    private WebElement getRadiobuttonBox() {
        return box;
    }

    public void click() {
        container.click();
    }

    public boolean isSelected() {
        return containsClassName(icon, "ui-icon-bullet");
    }

    public String getValue() {
        return root.getAttribute(VALUE);
    }

    public boolean isDisabled() {
        return containsClassName(getRadiobuttonBox(), PUI_DISABLED);
    }

    public boolean isVisible() {
        if (!containsAttribute(directive, "pui-radiobutton")) {
            fail("Visibility check can only performed on directive form of widget");
        }
        return directive.isDisplayed();
    }
}
