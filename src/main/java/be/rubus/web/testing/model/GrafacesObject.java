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

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.detector.Detector;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.ReflectionHelper;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class GrafacesObject {

    private Class<?> widgetClass;

    private Map<Class<? extends Annotation>, Field> grafacesFields = new HashMap<Class<? extends Annotation>, Field>();
    private Map<Class<? extends Annotation>, List<Method>> grafacesMethods = new HashMap<Class<? extends Annotation>, List<Method>>();

    public GrafacesObject(Class<?> widgetClass) {
        this.widgetClass = widgetClass;

        findGrafacesFields();
        findGrafacesMethods();
    }

    private void findGrafacesFields() {
        findGrafacesField(Root.class, WebElement.class);
        findGrafacesField(Drone.class, WebDriver.class);

    }

    private void findGrafacesField(Class<? extends Annotation> annotationClass, Class<?> fieldType) {
        List<Field> fieldsWithAnnotation = ReflectionHelper.getFieldsWithAnnotation(widgetClass, annotationClass);
        if (fieldsWithAnnotation.size() == 1) {
            if (fieldsWithAnnotation.get(0).getType() == fieldType) {
                storeGrafacesField(annotationClass, fieldsWithAnnotation.get(0));
            }
        } else {
            // FIXME logging
        }
    }

    private void storeGrafacesField(Class<? extends Annotation> annotationClass, Field grafacesField) {
        grafacesFields.put(annotationClass, grafacesField);
    }


    private void findGrafacesMethods() {
        //  ReflectionHelper.getMethodsWithAnnotation() Isn't looking into superclasses

        findGrafacesMethod(PostConstruct.class, void.class);
        findGrafacesMethod(WidgetValidation.class, boolean.class);
        findGrafacesMethod(WidgetValidation.class, Detector.class);
    }


    private void findGrafacesMethod(Class<? extends Annotation> annotationClass, Class<?> returnType) {

        //  ReflectionHelper.getMethodsWithAnnotation() Isn't looking into superclasses

        for (Method method : getMethodsWithAnnotation(widgetClass, annotationClass)) {
            if (method.getParameterTypes().length != 0) {
                // FIXME logging Only method with no parameters allowed
            }
            if (method.getReturnType() == returnType) {
                addGrafacesMethod(annotationClass, method);
            }

        }
    }

    private void addGrafacesMethod(Class<? extends Annotation> annotationClass, Method method) {
        List<Method> methods = grafacesMethods.get(annotationClass);
        if (methods == null) {
            methods = new ArrayList<Method>();
            grafacesMethods.put(annotationClass, methods);
        }
        methods.add(method);
    }

    public Field getFieldFor(Class<? extends Annotation> annotationType) {
        return grafacesFields.get(annotationType);
    }

    public List<Method> getMethodfor(Class<? extends Annotation> annotationType) {
        List<Method> result = grafacesMethods.get(annotationType);
        if (result == null) {
            result = new ArrayList<Method>();
        }
        return result;
    }


    /**
     * Copy from reflectionHelper but also looks into superclass.
     *
     * @param source
     * @param annotationClass
     * @return
     */
    private static List<Method> getMethodsWithAnnotation(final Class<?> source, final Class<? extends Annotation> annotationClass) {
        List<Method> declaredAccessableMethods = AccessController.doPrivileged(new PrivilegedAction<List<Method>>() {
            @Override
            public List<Method> run() {
                List<Method> foundMethods = new ArrayList<Method>();
                Class<?> nextSource = source;
                while (nextSource != Object.class) {
                    for (Method method : nextSource.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(annotationClass)) {
                            if (!method.isAccessible()) {
                                method.setAccessible(true);
                            }
                            foundMethods.add(method);
                        }
                    }
                    nextSource = nextSource.getSuperclass();
                }
                return foundMethods;
            }
        });
        return declaredAccessableMethods;
    }

}
