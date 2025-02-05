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
package hu.icellmobilsoft.coffee.rest.cdi;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Kulonbozo applikacioban hasznalt containerek gyujtoje
 *
 * @author imre.scheffer
 * @since 1.0.0
 */
@Named
@Dependent
public class BaseAppContainer {

    @Inject
    private BaseRequestContainer baseRequestContainer;

    /**
     * Getter for the field <code>baseRequestContainer</code>.
     * 
     * @return {@code BaseRequestContainer}
     */
    public BaseRequestContainer getBaseRequestContainer() {
        return baseRequestContainer;
    }

    /**
     * Setter for the field <code>baseRequestContainer</code>.
     * 
     * @param baseRequestContainer
     *            {@code BaseRequestContainer}
     */
    public void setBaseRequestContainer(BaseRequestContainer baseRequestContainer) {
        this.baseRequestContainer = baseRequestContainer;
    }
}
