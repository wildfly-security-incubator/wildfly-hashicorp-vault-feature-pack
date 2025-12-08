# WildFly HashiCorp Vault Feature Pack

A Galleon feature pack for integrating HashiCorp Vault capabilities into WildFly server installations.

## Overview

This project provides a WildFly subsystem extension that enables integration with HashiCorp Vault for secure credential management. It allows WildFly to retrieve credentials and secrets from HashiCorp Vault instead of storing them directly in configuration files.

## Features

- HashiCorp Vault connection management
- Credential store integration with WildFly Elytron
- Support for Vault KV secrets engine
- CLI commands for managing Vault connections

## Requirements

- Java 17+
- Maven 3.6+
- WildFly 36.0.1.Final or later
- HashiCorp Vault server

## Building

Build the project using Maven:

```bash
mvn clean install
```

## Usage

After building, you can provision a WildFly server with the HashiCorp Vault feature pack using Galleon:

```bash
galleon install org.wildfly.security.hashicorp.vault:hashicorp-vault-feature-pack:1.0.0.Alpha2-SNAPSHOT \
  --layers=hashicorp-vault \
  --dir=wildfly
```

Start WildFly with experimental stability:

```bash
./wildfly/bin/standalone.sh --stability=experimental
```

### CLI Commands

```bash
/subsystem=hashicorp-vault/credential-store=my-vault:add(
    host-address="http://localhost:8200",
    credential-reference={clear-text="myroot"}
)

/subsystem=hashicorp-vault/credential-store=secure-vault:add(
    host-address="https://vault.example.com:8200",
    truststore-path="/path/to/truststore.jks",
    keystore-path="/path/to/keystore.jks",
    truststore-password="keystore-password",
    credential-reference={clear-text="vault-token"}
)

/subsystem=hashicorp-vault/credential-store=my-vault:add-alias(
    alias="secret/myapp.database_password",
    secret-value="supersecret"
)

# Read aliases
/subsystem=hashicorp-vault/credential-store=my-vault:read-aliases()
```

### Using Vault Credentials

Reference Vault credentials using the format `<vault-path>.<key>`:

```xml
<credential-reference store="my-vault" alias="secret/database.password"/>
```

For more detailed examples, see the [examples/README.md](examples/README.md).

## License

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.

## Contributing

This project is part of the WildFly Security Incubator. Contributions are welcome!
