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
package be.rubus.web.testing.widget.extension.angularwidgets;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.detector.angularwidgets.PuiInputDetector;
import org.openqa.selenium.Keys;

/**
 *
 */
public class PuiInputText extends AbstractAngularWidgetsWidget {

    @WidgetValidation
    public Detector getDetector() {
        return new PuiInputDetector();
    }

    public void type(String value) {
        root.clear();
        root.sendKeys(value);
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

    public boolean isAngularJSInvalid() {
        return containsClassName(root, NG_INVALID);
    }

    public boolean isAngularJSValid() {
        return containsClassName(root, NG_VALID);
    }

    public void sendUpArrowFromKeyboard() {
        root.sendKeys(Keys.ARROW_UP);

    }

    public void sendDownArrowFromKeyboard() {
        root.sendKeys(Keys.ARROW_DOWN);
    }

    public void click() {
        root.click();
    }

    public boolean isVisible() {
        return root.isDisplayed();
    }

    public String getValue() {
        return root.getAttribute(VALUE);
    }

    public boolean isPristine() {
        return containsClassName(root, NG_PRISTINE);
    }

    public boolean isDirty() {
        return containsClassName(root, NG_DIRTY);
    }

    public String getType() {
        return getAttribute(root, "type");
    }
}
