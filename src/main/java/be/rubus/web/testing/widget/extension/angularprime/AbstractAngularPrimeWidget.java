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

import be.rubus.web.testing.AbstractWidget;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class AbstractAngularPrimeWidget extends AbstractWidget {

    protected static final String NG_INVALID = "ng-invalid";
    protected static final String NG_VALID = "ng-valid";
    protected static final String NG_PRISTINE = "ng-pristine";
    protected static final String NG_DIRTY = "ng-dirty";
    protected static final String PUI_WIDGET = "ui-widget";
    protected static final String PUI_HOVER = "ui-state-hover";
    protected static final String PUI_DISABLED = "ui-state-disabled";

    protected String getIconName(WebElement element) {
        String result = null;
        String[] cssClasses = getStyleClasses(element).split(" ");
        for (String cssClass : cssClasses) {
            if (cssClass.startsWith("ui-icon-")) {
                result = cssClass.substring(8);
                break;
            }
        }
        return result;
    }

    protected void waitForAjax() {
        boolean ajaxIsComplete;
        int loopCount = 50;
        while (loopCount > 0) {
            ajaxIsComplete = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
            if (ajaxIsComplete) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            loopCount--;

        }
        if (loopCount == 0) {
            throw new RuntimeException("Ajax request still running after timeout");
        }
    }
}
