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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 *
 */
public class CommonElementCode {

    protected boolean containsClassName(WebElement element, String className) {
        return element.getAttribute("class").contains(className);
    }

    protected boolean isHtmlElement(WebElement element, String elementName) {
        return element.getTagName().toUpperCase().equals(elementName);
    }


    protected WebElement getParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    protected WebElement getNextSibling(WebElement element) {
        String id = getId(element);
        assertFalse("getNextSibling() can only be called for Element with id", id.isEmpty());

        WebElement result = null;
        WebElement parent = getParent(element);
        List<WebElement> children = getAllChildren(parent);
        Iterator<WebElement> iterator = children.iterator();
        while (result == null && iterator.hasNext()) {
            WebElement child = iterator.next();
            if (getId(child).equals(id)) {
                if (iterator.hasNext()) {
                    result = iterator.next();
                }
            }
        }
        return result;

    }

    protected List<WebElement> getAllChildren(WebElement element) {
        return element.findElements(By.xpath("*"));
    }

    protected String getId(WebElement element) {
        return element.getAttribute("id");
    }

    protected String getComputedCssValue(WebElement someElement, String cssName) {
        return someElement.getCssValue(cssName);
    }

    protected String getAttribute(WebElement someElement, String attributeName) {
        return someElement.getAttribute(attributeName);
    }

    protected boolean containsAttribute(WebElement someElement, String attributeName) {
        return getAttribute(someElement, attributeName) != null;
    }

    public void waitForScreenUpdate(int timeInMiliis) {
        try {
            Thread.sleep(timeInMiliis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
