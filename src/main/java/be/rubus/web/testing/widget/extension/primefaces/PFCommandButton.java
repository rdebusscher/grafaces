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
package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.primefaces.PFButtonDetector;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PFCommandButton extends AbstractPrimeFacesWidget {

    @FindBy(className = "ui-button-text")
    private WebElement textSpan;

    @FindBy(className = "ui-icon")
    private GrapheneElement iconSpan;

    @WidgetValidation
    public Detector getDetector() {
        return new PFButtonDetector();
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

    public void click() {
        root.click();
    }

    public boolean isVisible() {
        boolean result = grafacesContext.isWidgetFound(root);
        if (result) {
            result = root.isDisplayed();
        }
        return result;
    }

    public String getLabel() {
        String result = null;
        if (!containsClassName(root, "pui-button-icon-only")) {
            result = textSpan.getText();
        }
        return result;
    }

    public boolean hasIcon() {
        return iconSpan.isPresent();
    }

    public String getIconName() {
        return iconSpan.isPresent() ? getIconName(iconSpan) : null;
    }

    public String getIconLocation() {
        String result = null;
        if (iconSpan.isPresent()) {
            if (containsClassName(iconSpan, "ui-button-icon-left")) {
                result = "left";
            }
            if (containsClassName(iconSpan, "ui-button-icon-right")) {
                result = "right";
            }
        }
        return result;
    }
}
