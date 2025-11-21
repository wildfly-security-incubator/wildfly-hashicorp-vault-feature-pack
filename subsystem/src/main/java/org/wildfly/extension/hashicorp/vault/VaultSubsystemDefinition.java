/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.hashicorp.vault;

import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

/**
 * Root resource definition for the HashiCorp Vault subsystem.
 *
 */
public class VaultSubsystemDefinition extends SimpleResourceDefinition {

    protected VaultSubsystemDefinition() {
        super(VaultExtension.SUBSYSTEM_PATH,
                VaultSubsystemRegistrar.RESOLVER,
                VaultSubsystemAdd.INSTANCE,
                VaultSubsystemRemove.INSTANCE);
    }

    @Override
    public void registerOperations(ManagementResourceRegistration registration) {
        super.registerOperations(registration);
        registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);

    }

    @Override
    public void registerChildren(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerSubModel(new CredentialStoreDefinition());
    }
}
