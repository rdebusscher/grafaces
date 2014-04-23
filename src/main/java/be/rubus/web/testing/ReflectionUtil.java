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

import org.jboss.arquillian.graphene.enricher.exception.GrapheneTestEnricherException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 */
public final class ReflectionUtil {

    private static final String NEW_LINE = System.getProperty("line.separator");

    private ReflectionUtil() {
    }

    public static void setValue(Field field, Object target, Object value) {
        if (target == null) {
            throw new IllegalArgumentException("Parameter 'target' can't be null");
        }
        if (field == null) {
            throw new IllegalArgumentException("Parameter 'field' can't be null");
        }
        boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }
        try {
            field.set(target, value);
        } catch (Exception ex) {
            throw new GrapheneTestEnricherException("During enriching of " + NEW_LINE + target.getClass() + NEW_LINE
                    + " the field " + NEW_LINE + field + " was not able to be set! Check the cause!", ex);
        }
        if (!accessible) {
            field.setAccessible(false);
        }
    }

    public static <T> T getFieldValue(Field field, Object target, Class<T> classType) {
        if (target == null) {
            throw new IllegalArgumentException("Parameter 'target' can't be null");
        }
        if (field == null) {
            throw new IllegalArgumentException("Parameter 'field' can't be null");
        }
        field.setAccessible(true);
        try {
            return (T) field.get(target);
        } catch (IllegalAccessException ex) {
            throw new GrapheneTestEnricherException("During enriching of " + NEW_LINE + target.getClass() + NEW_LINE
                    + " the field " + NEW_LINE + field + " was not able to be read! Check the cause!", ex);

        }
    }

    public static Object invokeMethod(Method method, Object target) {
        method.setAccessible(true);
        try {
            return method.invoke(target);
        } catch (Exception ex) {
            throw new GrapheneTestEnricherException("During enriching of " + NEW_LINE + target.getClass() + NEW_LINE
                    + " the method " + NEW_LINE + method + " was not able to be executed! Check the cause!", ex);

        }
    }
}
