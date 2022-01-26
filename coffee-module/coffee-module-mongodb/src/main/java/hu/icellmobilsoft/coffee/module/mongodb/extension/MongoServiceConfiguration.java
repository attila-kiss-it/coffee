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
package hu.icellmobilsoft.coffee.module.mongodb.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Qualifier for @MongoService implementations
 * 
 * @author czenczl
 * @since 1.1.0
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE })
public @interface MongoServiceConfiguration {

    /**
     * Config key of the desired Mongo DB connection. <br>
     * ie. if connection details are defined in the project-*.yml by the keys: {@code coffee.mongodb.exampleDB.*=...} then configKey should be
     * "exampleDB"
     *
     * @return config key
     */
    @Nonbinding
    String configKey();

    /**
     * Returns Mongo DB collection
     * 
     * @return Mongo DB collection
     */
    @Nonbinding
    String collectionKey();

    /**
     * {@code MongoServiceConfiguration} qualifier literal
     */
    final class Literal extends AnnotationLiteral<MongoServiceConfiguration> implements MongoServiceConfiguration {

        private static final long serialVersionUID = 1L;

        /**
         * config key
         */
        private String configKey;
        /**
         * Mongo DB collection
         */
        private String collectionKey;

        /**
         * Instantiates the literal with configKey and collectionKey
         * 
         * @param configKey
         *            config key
         * @param collectionKey
         *            Mongo DB collection
         */
        public Literal(String configKey, String collectionKey) {
            this.configKey = configKey;
            this.collectionKey = collectionKey;
        }

        @Nonbinding
        @Override
        public String configKey() {
            return configKey;
        }

        @Nonbinding
        @Override
        public String collectionKey() {
            return collectionKey;
        }

    }

}
