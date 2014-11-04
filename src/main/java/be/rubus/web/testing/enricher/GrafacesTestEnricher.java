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
package be.rubus.web.testing.enricher;

import be.rubus.web.testing.GrafacesContext;
import be.rubus.web.testing.ReflectionUtil;
import be.rubus.web.testing.annotation.Grafaces;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.enricher.AbstractSearchContextEnricher;
import org.jboss.arquillian.graphene.enricher.ReflectionHelper;
import org.jboss.arquillian.graphene.proxy.GrapheneProxy;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 *
 */
public class GrafacesTestEnricher extends AbstractSearchContextEnricher {

    private GrafacesContext grafacesContext = GrafacesContext.getInstance();


    @Override
    public void enrich(SearchContext searchContext, Object target) {
        Collection<Field> fields = ReflectionHelper.getFieldsWithAnnotation(target.getClass(), Grafaces.class);
        for (Field field : fields) {
            if (field.getType() == GrafacesContext.class) {
                ReflectionUtil.setValue(field, target, grafacesContext);
            } else {
                try {
                    Object futureTarget = createProxyAndEnrich(searchContext, field);
                    ReflectionUtil.setValue(field, target, futureTarget);

                } catch (InstantiationException e) {
                    // FIXME logging
                    e.printStackTrace();

                } catch (IllegalAccessException e) {
                    // FIXME logging
                    e.printStackTrace();
                }
            }
        }
    }

    private Object createProxyAndEnrich(SearchContext searchContext, Field field) throws InstantiationException, IllegalAccessException {
        final Object fieldValue = field.getType().newInstance();

        GrapheneContext grapheneContext = GrapheneContext.getContextFor(ReflectionHelper.getQualifier(field.getAnnotations()));
        SearchContext localSearchContext = grapheneContext.getWebDriver(SearchContext.class);

        GrapheneProxy.FutureTarget targetToFieldValue = new GrapheneProxy.FutureTarget() {
            @Override
            public Object getTarget() {
                return fieldValue;
            }
        };
        Object futureTarget = GrapheneProxy.getProxyForFutureTarget(getContext(localSearchContext), targetToFieldValue, fieldValue.getClass());
        // This is not very clear to me but it works
        enrichRecursively(searchContext, futureTarget);
        enrichRecursively(searchContext, fieldValue);
        return futureTarget;
    }

    protected static GrapheneContext getContext(Object object) {
        if (!GrapheneProxy.isProxyInstance(object)) {
            throw new IllegalArgumentException("The parameter [object] has to be instance of " + GrapheneProxyInstance.class.getName() + ", but it is not. The given object is " + object + ".");
        }
        return ((GrapheneProxyInstance) object).getContext();
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}
