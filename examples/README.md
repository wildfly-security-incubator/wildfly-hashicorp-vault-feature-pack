# HashiCorp Vault Subsystem Examples

## Prerequisites

1. **HashiCorp Vault Server**: You need a running Vault server, default port is 8200
2. **Vault Token**: A valid token with appropriate permissions, see the HashiCorp documentation for details: https://developer.hashicorp.com/vault/docs
3. **WildFly**: WildFly 36+ with the HashiCorp Vault subsystem feature pack installed

## Using the Feature Pack:

#### 1. Build and install the feature pack with maven
```bash
mvn clean install
```
#### 2. Provision a WildFly server with the feature pack. Download the latest version of the galleon tools from https://github.com/wildfly/galleon/releases . The example below assumes you have unzipped galleon in a ~/Downloads/galleon directory:
```bash
~Downloads/galleon/bin/galleon.sh install org.wildfly:wildfly-hashicorp-vault-feature-pack:1.0.0.Alpha1-SNAPSHOT --layers=hashicorp-vault  --dir=wildfly
```

#### 3. Start WildFly
```bash
./wildfly/bin/standalone.sh --stability=experimental
```

## HashiCorp Vault Quick Start

### 1. Start HashiCorp Vault (Development Mode)
```bash
vault server -dev -dev-root-token-id="myroot"
```
2. Set Environment Variables
```bash
export VAULT_TOKEN="myroot"
export VAULT_ADDR="http://localhost:8200"
```
3. Store Some Secrets
bash# Database credentials
vault kv put secret/database/prod username=dbuser password=secretpass

# JMS credentials  
vault kv put secret/jms/prod username=jmsuser password=jmspass

# API keys
vault kv put secret/api/external apikey=abc123xyz

4. Configure WildFly
Add to your standalone.xml:
xml<subsystem xmlns="urn:wildfly:hashicorp-vault:1.0">
    <connection name="default"
                url="http://localhost:8200"
                token="${VAULT_TOKEN}"
                ssl-verify="false"/>
</subsystem>

5. Use Vault Credentials in Datasources
xml<datasource jndi-name="java:jboss/datasources/MyDS" pool-name="MyDS">
    <connection-url>jdbc:postgresql://localhost:5432/mydb</connection-url>
    <driver>postgresql</driver>
    <security>
        <credential-reference store="vault" alias="database/prod"/>
    </security>
</datasource>

CLI Commands
Connection Management
bash# Add a new Vault connection
/subsystem=vault/connection=my-vault:add(url="http://vault.example.com:8200", token="hvs.xxxxx")

# Test connection
/subsystem=vault/connection=my-vault:test-connection

# Remove connection
/subsystem=vault/connection=my-vault:remove

# Clear cache
/subsystem=vault/connection=my-vault:clear-cache
