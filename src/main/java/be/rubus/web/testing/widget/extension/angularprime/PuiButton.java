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
import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 */
public class PuiButton extends AbstractAngularPrimeWidget {

    @FindBy(className = "pui-button-text")
    private WebElement textSpan;

    @FindBy(className = "ui-icon")
    private GrapheneElement iconSpan;

    @WidgetValidation
    private boolean isValidWidget() {
        boolean result = isHtmlElement(root, "BUTTON");
        if (result) {
            result = getAttribute(root, "type").toLowerCase().equals("button");
        }
        if (result) {
            result = getAttribute(root, "pui-button") != null;
        }
        return result;
    }

    public String getLabel() {
        // TODO Check it is not icon only.
        return textSpan.getText();
    }

    public boolean hasIcon() {
        return iconSpan.isPresent();
    }

    public String getIconName() {
        return iconSpan.isPresent() ? getIconName(iconSpan) : null;
    }

    public void click() {
        root.click();
    }
}
