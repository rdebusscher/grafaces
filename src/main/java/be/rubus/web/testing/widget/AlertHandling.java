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
package be.rubus.web.testing.widget;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.fail;

/**
 *
 */
public class AlertHandling {

    @Drone
    private WebDriver driver;

    private Alert alert;

    public AlertHandling checkForAlert() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        if (wait.until(ExpectedConditions.alertIsPresent()) == null) {

            fail("Alert not showing as feedback for user");
        }
        return this;
    }

    public void checkForNoAlert() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            fail("Alert is showing and not expected to popup");
        } catch (TimeoutException te) {

            ; // OK we don't want to see the alert
        }

    }

    public AlertHandling switchToAlert() {
        alert = driver.switchTo().alert();
        return this;
    }

    public void releaseAlert() {
        alert = null;
    }


    public String getAlertText() {
        if (alert == null) {
            fail("Please use the method switchToAlert() prior to this method.");
        }
        return alert.getText();

    }

    public void alertAccept() {
        if (alert == null) {
            fail("Please use the method switchToAlert() prior to this method.");
        }
        alert.accept();
    }


}
