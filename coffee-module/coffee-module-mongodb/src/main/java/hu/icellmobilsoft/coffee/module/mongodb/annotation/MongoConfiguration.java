/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.coffee.module.mongodb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.enterprise.util.Nonbinding;
import jakarta.inject.Qualifier;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * <p>
 * MongoConfiguration class.
 * </p>
 *
 * @since 1.0.0
 * 
 * @deprecated Use {@link hu.icellmobilsoft.coffee.module.mongodb.extension.MongoClientConfiguration} instead, forRemoval = true, since = "1.1.0"
 */
@Deprecated(forRemoval = true, since = "1.1.0")
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE })
public @interface MongoConfiguration {

    /**
     * This value is used in {@link ConfigProperty} annotation
     * 
     * @return urlKey
     */
    @Nonbinding
    String urlKey();

    /**
     * This value is used in {@link ConfigProperty} annotation
     * 
     * @return databaseKey
     */
    @Nonbinding
    String databaseKey();

    /**
     * Literal class for {@code MongoConfiguration} qualifier
     */
    final class Literal extends AnnotationLiteral<MongoConfiguration> implements MongoConfiguration {

        /**
         * Qualifier literal instance
         */
        public static final Literal INSTANCE = new Literal();

        private static final long serialVersionUID = 1L;

        @Nonbinding
        @Override
        public String urlKey() {
            return null;
        }

        @Nonbinding
        @Override
        public String databaseKey() {
            return null;
        }
    }
}
