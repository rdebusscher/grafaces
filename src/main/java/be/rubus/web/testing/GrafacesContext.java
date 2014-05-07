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

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.model.*;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.ReflectionHelper;
import org.jboss.arquillian.graphene.findby.FindByUtilities;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 *
 */
public class GrafacesContext {

    private static GrafacesContext instance;

    private Map<Class<?>, GrafacesObject> grafacesObjects = new HashMap<Class<?>, GrafacesObject>();
    private Map<Class<? extends Annotation>, GrafacesMethodHandler> grafacesMethodHandlers = new HashMap<Class<? extends Annotation>, GrafacesMethodHandler>();
    private GrafacesMethodHandler widgetValidtionHandler;

    private GrafacesContext() {
    }

    private void init() {
        grafacesMethodHandlers.put(PostConstruct.class, new PostContructHandler());
        grafacesMethodHandlers.put(WidgetValidation.class, new WidgetValidationHandler());
        widgetValidtionHandler = new WidgetValidHandler();
    }

    public static GrafacesContext getInstance() {
        if (instance == null) {
            instance = new GrafacesContext();
            instance.init();
        }
        return instance;
    }

    public <T> T getInstanceOf(Class<? extends Annotation> annotationType, Object widget, Class<T> classType) {
        GrafacesObject grafacesObject = getGrafacesObject(widget.getClass());
        Field field = grafacesObject.getFieldFor(annotationType);
        return ReflectionUtil.getFieldValue(field, widget, classType);
    }

    private GrafacesObject getGrafacesObject(Class<? extends Object> widgetClass) {
        GrafacesObject result = grafacesObjects.get(widgetClass);
        if (result == null) {
            result = new GrafacesObject(widgetClass);
            grafacesObjects.put(widgetClass, result);
        }
        return result;
    }

    public Object executeMethodsOfType(Class<? extends Annotation> annotationType, Object widget) {
        GrafacesObject grafacesObject = getGrafacesObject(widget.getClass());
        List<Method> methods = grafacesObject.getMethodfor(annotationType);
        GrafacesMethodHandler methodHandler = grafacesMethodHandlers.get(annotationType);
        if (methodHandler == null) {
            throw new IllegalArgumentException("No known method handler for type " + annotationType);
        }
        return methodHandler.executeMethods(methods, widget);
    }

    public boolean hasPropertyFor(Class<? extends Annotation> annotationType, Object widget) {
        GrafacesObject grafacesObject = getGrafacesObject(widget.getClass());
        return grafacesObject.getFieldFor(annotationType) != null;
    }

    public void setInstanceOf(Class<? extends Annotation> annotationType, Object widget, Object value) {
        GrafacesObject grafacesObject = getGrafacesObject(widget.getClass());
        Field field = grafacesObject.getFieldFor(annotationType);
        ReflectionUtil.setValue(field, widget, value);
    }

    public void initializePageFragment(Object childObject, WebElement childRoot, Object parentObject) {
        if (!hasPropertyFor(Root.class, childObject)) {
            fail("No property annotated with @Root in class " + childObject.getClass());
        }
        setInstanceOf(Root.class, childObject, childRoot);

        if (hasPropertyFor(Drone.class, childObject)) {
            WebDriver driver = getInstanceOf(Drone.class, parentObject, WebDriver.class);
            // FIXME when driver == null
                    /*
                    if (grapheneContext == null) {
                grapheneContext = GrapheneContext.getContextFor(ReflectionHelper.getQualifier(field.getAnnotations()));
            }
            grapheneContext.getWebDriver(xx.class) where xx is the class type of the field where annotation was placed on
           */
            setInstanceOf(Drone.class, childObject, driver);
        }


        try {
            List<Field> fields = ReflectionHelper.getFieldsWithAnnotation(childObject.getClass(), FindBy.class);
            for (Field field : fields) {
                By by = FindByUtilities.getCorrectBy(field, How.ID_OR_NAME);
                // WebElement
                if (field.getType().isAssignableFrom(WebElement.class)) {

                    WebElement element = childRoot.findElement(by);
                    ReflectionUtil.setValue(field, childObject, element);
                    // List<WebElement>
                } else if (field.getType().isAssignableFrom(List.class) && getListType(field)
                        .isAssignableFrom(WebElement.class)) {
                    List<WebElement> elements = childRoot.findElements(by);
                    ReflectionUtil.setValue(field, childObject, elements);
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getListType(Field listField) throws ClassNotFoundException {
        return Class.forName(listField.getGenericType().toString().split("<")[1].split(">")[0].split("<")[0]);
    }

    public Object executeIsWidgetValid(Object widget) {
        GrafacesObject grafacesObject = getGrafacesObject(widget.getClass());
        List<Method> methods = grafacesObject.getMethodfor(WidgetValidation.class);
        return widgetValidtionHandler.executeMethods(methods, widget);
    }
}
