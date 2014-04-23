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
package be.rubus.web.testing.model;

import be.rubus.web.testing.GrafacesContext;
import be.rubus.web.testing.ReflectionUtil;
import be.rubus.web.testing.detector.Detector;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.fail;

/**
 *
 */
public class WidgetValidationHandler implements GrafacesMethodHandler {

    private GrafacesContext context = GrafacesContext.getInstance();

    @Override
    public void executeMethods(List<Method> methods, Object target) {

        for (Method method : methods) {
            if (!performValidation(method, target)) {
                failValidation();
            }
        }
    }

    private void failValidation() {
        // FIXME : more information which widget failed validation
        fail("Widget validation failed");
    }

    private boolean performValidation(Method method, Object target) {
        if (method.getReturnType() == boolean.class) {
            return (Boolean) ReflectionUtil.invokeMethod(method, target);

        }
        if (method.getReturnType() == Detector.class) {
            Detector detector = (Detector) ReflectionUtil.invokeMethod(method, target);
            if (detector != null) {
                WebElement root = context.getInstanceOf(Root.class, target, WebElement.class);
                return detector.isSupported(root);
            } else {
                // FXIME logging
                return true;
            }
        }

        throw new IllegalArgumentException("Hmmm  Don't recognize the return type of the method. " + method);
    }

}
