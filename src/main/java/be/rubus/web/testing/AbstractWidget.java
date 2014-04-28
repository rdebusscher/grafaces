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
package be.rubus.web.testing;

import be.rubus.web.testing.annotation.Grafaces;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 */
public abstract class AbstractWidget extends CommonElementCode {


    protected static final String VALUE = "value";

    @Drone
    protected WebDriver driver;

    @Root
    protected WebElement root;

    @Grafaces
    protected GrafacesContext grafacesContext;

    public String getContent() {
        return root.getText();
    }

    protected void moveTo(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    protected void blur(WebElement someElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", someElement);

    }

    protected void waitUntilVisibilityOf(WebElement element) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(element));
    }


    protected void waitUntilVisibilityOf(By byId) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(driver.findElement(byId)));
    }


    protected void waitUntilHiddenOf(WebElement checkElement) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(checkElement)));

    }

    public String getAttribute(String attributeName) {
        return root.getAttribute(attributeName);
    }
}
