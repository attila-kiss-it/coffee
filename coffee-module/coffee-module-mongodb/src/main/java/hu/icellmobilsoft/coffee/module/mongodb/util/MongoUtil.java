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
package hu.icellmobilsoft.coffee.module.mongodb.util;

import jakarta.enterprise.inject.Vetoed;

import com.mongodb.BasicDBObject;

/**
 * Mongo util.
 *
 * @author karoly.tamas
 * @since 1.0.0
 */
@Vetoed
public class MongoUtil {

    /**
     * Converts JSON {@link String} to {@link BasicDBObject}.
     *
     * @param json
     *            JSON to convert
     * @return converted {@code BasicDBObject}
     */
    public static BasicDBObject jsonToBasicDbObject(String json) {
        return BasicDBObject.parse(json);
    }
}
