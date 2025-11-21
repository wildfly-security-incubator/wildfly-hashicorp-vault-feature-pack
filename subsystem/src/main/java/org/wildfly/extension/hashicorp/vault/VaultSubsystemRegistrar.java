/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.hashicorp.vault;

import static org.wildfly.extension.hashicorp.vault.VaultExtension.SUBSYSTEM_NAME;
import static org.wildfly.extension.hashicorp.vault.VaultExtension.SUBSYSTEM_PATH;

import org.jboss.as.controller.ResourceDefinition;
import org.jboss.as.controller.ResourceRegistration;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.ParentResourceDescriptionResolver;
import org.jboss.as.controller.descriptions.SubsystemResourceDescriptionResolver;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.wildfly.subsystem.resource.ManagementResourceRegistrar;
import org.wildfly.subsystem.resource.ManagementResourceRegistrationContext;
import org.wildfly.subsystem.resource.ResourceDescriptor;
import org.wildfly.subsystem.resource.SubsystemResourceDefinitionRegistrar;

/**
 * Resource definition for the subsystem root resource.
 */
public final class VaultSubsystemRegistrar implements SubsystemResourceDefinitionRegistrar {

    public static final ParentResourceDescriptionResolver RESOLVER = new SubsystemResourceDescriptionResolver(SUBSYSTEM_NAME, VaultSubsystemRegistrar.class);
    static final ResourceRegistration REGISTRATION = ResourceRegistration.of(SUBSYSTEM_PATH);

    @Override
    public ManagementResourceRegistration register(SubsystemRegistration parent, ManagementResourceRegistrationContext managementResourceRegistrationContext) {
        ResourceDefinition definition = ResourceDefinition.builder(REGISTRATION, RESOLVER).build();
        ManagementResourceRegistration registration = parent.registerSubsystemModel(definition);
        ResourceDescriptor descriptor = ResourceDescriptor.builder(RESOLVER)
                .build();
        ManagementResourceRegistrar.of(descriptor).register(registration);

        registration.registerSubModel(new CredentialStoreDefinition());
        
        return registration;
    }

}