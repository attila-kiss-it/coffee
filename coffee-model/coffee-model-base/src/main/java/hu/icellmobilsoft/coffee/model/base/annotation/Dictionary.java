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
package hu.icellmobilsoft.coffee.model.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.enterprise.util.Nonbinding;

/**
 * Dictionary marking annotation for entity column
 *
 * @author imre.scheffer
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Dictionary {

    /**
     * default DictionaryType.NAME
     * 
     * @return default DictionaryType.NAME
     */
    @Nonbinding
    DictionaryType type() default DictionaryType.NAME;

    /**
     * Literal class for {@code Dictionary} annotation
     */
    final class Literal extends AnnotationLiteral<Dictionary> implements Dictionary {

        /**
         * Qualifier literal instance
         */
        public static final Literal INSTANCE = new Literal();

        private static final long serialVersionUID = 1L;

        @Nonbinding
        @Override
        public DictionaryType type() {
            return DictionaryType.NAME;
        }
    }
}
