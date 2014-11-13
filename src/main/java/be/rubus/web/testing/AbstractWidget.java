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
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 */
public abstract class AbstractWidget extends CommonElementCode {

    protected static final String UI_HOVER = "ui-state-hover";
    protected static final String UI_DISABLED = "ui-state-disabled";
    protected static final String UI_ACTIVE = "ui-state-active";
    protected static final String UI_HIGHLIGHT = "ui-state-highlight";

    protected static final String VALUE = "value";

    @Drone
    protected WebDriver driver;

    @Root
    protected WebElement root;

    @Grafaces
    protected GrafacesContext grafacesContext;

    /**
     * You can  call this method to verify if the DOM structure complies with the widget as defined by the method(s)
     * annotated by {@link be.rubus.web.testing.annotation.WidgetValidation}. Don't override this method.
     *
     * @return true when DOM element complies.
     */
    // Can't be final due to proxy usage.
    public boolean isWidgetValid() {
        // This must always be executed by the interceptor which doesn't propagate to this method.
        throw new IllegalAccessError("This should never be called but handled by the GrafacesInterceptor");
    }

    public String getContent() {
        return root.getText();
    }

    public String getContentInAnyCase(WebElement element) {
        String result = element.getText();
        if (result == null || result.length() == 0) {
            result = element.getAttribute("textContent");
            if (result == null || result.length() == 0) {
                result = element.getAttribute("innerText");
            }
        }
        return result;
    }

    public boolean isVisible() {
        boolean result = grafacesContext.isWidgetFound(root);
        if (result) {
            result = root.isDisplayed();
        }
        return result;
    }

    public void click() {
        root.click();
    }

    public String getStyleClasses() {
        return root.getAttribute("class");
    }

    public boolean containsClassName(String className) {
        return getStyleClasses(root).contains(className);
    }

    public String getComputedCssValue(String cssName) {
        return root.getCssValue(cssName);
    }

    public String getAttribute(String attributeName) {
        return root.getAttribute(attributeName);
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

    protected void presenceOfElementLocated(By byId) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(byId));
    }

    protected void waitUntilHiddenOf(WebElement checkElement) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(checkElement)));

    }

    protected void click(WebElement element, Keys... keys) {
        Actions builder = new Actions(driver);

        for (Keys key : keys) {
            builder.keyDown(key);
        }
        builder.click(element);
        for (Keys key : keys) {
            builder.keyUp(key);
        }

        Action action = builder.build();

        action.perform();
    }
}
