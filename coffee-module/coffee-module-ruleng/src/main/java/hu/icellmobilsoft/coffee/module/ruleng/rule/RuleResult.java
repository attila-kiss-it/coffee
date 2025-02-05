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
package hu.icellmobilsoft.coffee.module.ruleng.rule;

import jakarta.enterprise.inject.Vetoed;

/**
 * Általános kiértékelés eredménye (valid és invalid esetén is)
 *
 * @author imre.scheffer
 * @since 1.0.0
 */
@Vetoed
public class RuleResult {

    private Long index;

    /**
     * Getter for the field {@code description}. When evaluating in a list, {@code index} connects the evaluation with the result data.
     *
     * @return index
     */
    public Long getIndex() {
        return index;
    }

    /**
     * Setter for the field {@code index}.
     *
     * @param index
     *            index
     */
    public void setIndex(Long index) {
        this.index = index;
    }
}
