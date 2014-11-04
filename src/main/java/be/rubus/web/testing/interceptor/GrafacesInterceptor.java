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
package be.rubus.web.testing.interceptor;

import be.rubus.web.testing.GrafacesContext;
import be.rubus.web.testing.InitializingWidget;
import be.rubus.web.testing.annotation.WidgetValidation;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.arquillian.graphene.proxy.Interceptor;
import org.jboss.arquillian.graphene.proxy.InvocationContext;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class GrafacesInterceptor implements Interceptor {

    private Set<String> componentIds = new HashSet<String>();

    private GrafacesContext grafacesContext = GrafacesContext.getInstance();

    @Override
    public Object intercept(InvocationContext context) throws Throwable {

        Object widget = context.getTarget();
        if ("isWidgetValid".equals(context.getMethod().getName())) {
            return widgetValidResult(widget);
        }

        if (widget instanceof InitializingWidget) {
            String widgetId = widget.toString();
            if (!componentIds.contains(widgetId)) {
                componentIds.add(widgetId);
                handleWidget(context.getTarget());
            }
        } else {
            WebElement root = grafacesContext.getInstanceOf(Root.class, widget, WebElement.class);

            if (root != null) {
                try {
                    if (!componentIds.contains(root.toString())) {
                        componentIds.add(root.toString());
                        handleWidget(context.getTarget());
                    }
                } catch (NoSuchElementException e) {
                    // It is possible that root doesn't exist and thus we must protect the toString() method.
                }
            }
        }

        return context.invoke();
    }

    private Object widgetValidResult(Object widget) {
        return grafacesContext.executeIsWidgetValid(widget);
    }

    private void handleWidget(Object widget) {
        grafacesContext.executeMethodsOfType(PostConstruct.class, widget);
        grafacesContext.executeMethodsOfType(WidgetValidation.class, widget);

    }

}
