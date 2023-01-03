/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 - 2022 i-Cell Mobilsoft Zrt.
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
package org.apache.deltaspike.jpa.impl.transaction;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.api.provider.DependentProvider;
//import org.apache.deltaspike.core.impl.util.JndiUtils;
import org.apache.deltaspike.jpa.api.config.base.JpaBaseConfig;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;

import javax.naming.InitialContext;

//the separated logic allows a lazy lookup of this bean and
//avoids that the injection of UserTransaction fails in an unmanaged thread (see DELTASPIKE-917).
@Dependent
public class UserTransactionResolver implements Serializable
{
    private static final long serialVersionUID = -1432802805095533499L;

    @Inject
    private BeanManager beanManager;

    public UserTransaction resolveUserTransaction()
    {
        UserTransaction userTransaction;

        try
        {
            DependentProvider<ManagedUserTransactionResolver> provider =
                BeanProvider.getDependent(this.beanManager, ManagedUserTransactionResolver.class);

            userTransaction = provider.get().resolveUserTransaction();

            provider.destroy();
        }
        catch (Throwable t)
        {
            //it was just a try
            userTransaction = null;
        }

        if (userTransaction != null)
        {
            return userTransaction;
        }

        String jndiName = JpaBaseConfig.UserTransaction.JNDI_NAME;

        String[] jndiNames = jndiName.split(",");

        for (String currentJndiName : jndiNames)
        {
            try
            {
                userTransaction = (UserTransaction) InitialContext.doLookup(currentJndiName);
//                userTransaction = JndiUtils.lookup(currentJndiName, UserTransaction.class);

                if (userTransaction != null)
                {
                    break;
                }
            }
            catch (Exception e)
            {
                userTransaction = null;
            }
        }

        return userTransaction;
    }
}
