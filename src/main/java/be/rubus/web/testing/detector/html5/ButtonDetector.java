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
package be.rubus.web.testing.detector.html5;

import be.rubus.web.testing.detector.GenericDetector;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class ButtonDetector extends GenericDetector {
    @Override
    public boolean isSupported(WebElement element) {
        boolean result = false;
        String type = getAttribute(element, "type").toLowerCase();
        if (isHtmlElement(element, "INPUT")) {
            result = type.equals("submit");
        }
        if (isHtmlElement(element, "BUTTON")) {
            result = type.equals("submit") || type.equals("button");
        }
        return result;
    }
}
