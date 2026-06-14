#  Configurao de Banco de Dados - AutoManager

Este projeto est configurado para usar **H2 (em memria)** por padro, mas pode ser facilmente migrado para um banco relacional.

---

##  ndice

1. [H2 (Padro)](#h2-padro)
2. [MySQL](#mysql)
3. [PostgreSQL](#postgresql)
4. [SQL Server](#sql-server)
5. [Como Mudar de Banco](#como-mudar-de-banco)

---

## H2 (Padro)

O projeto vem configurado com **H2** - um banco de dados em memria.

### Caractersticas:
-  Sem instalao necessria
-  Ideal para testes e desenvolvimento
-  Dados perdidos ao reiniciar a aplicao
-  Performance rpida

### Configurao Ativa:
```properties
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
```

### Usar H2 (Padro):
- Nenhuma configurao adicional necessria
- Execute o projeto normalmente

---

## MySQL

### Pr-requisitos:
1. MySQL Server instalado
2. Banco de dados criado: `CREATE DATABASE automanager;`

### Passos para Usar MySQL:

#### 1 Descomentar Dependncia (pom.xml)
```xml
<!-- Descomente esta seo -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.35</version>
    <scope>runtime</scope>
</dependency>
```

#### 2 Configurar application.properties
```properties
# Descomente as linhas do MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/automanager?useSSL=false&serverTimezone=UTC
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update

# Comente as linhas do H2:
# spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1
# ... (comentar todas as linhas de H2)
```

#### 3 Ajustar Valores
- `localhost` - Host do MySQL (mude se estiver em outro servidor)
- `3306` - Porta padro do MySQL
- `root` - Usurio do MySQL
- `sua_senha_aqui` - Senha do MySQL

#### 4 Compilar e Executar
```bash
mvn clean install
mvn spring-boot:run
```

---

## PostgreSQL

### Pr-requisitos:
1. PostgreSQL Server instalado
2. Banco de dados criado: `CREATE DATABASE automanager;`

### Passos para Usar PostgreSQL:

#### 1 Descomentar Dependncia (pom.xml)
```xml
<!-- Descomente esta seo -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
    <scope>runtime</scope>
</dependency>
```

#### 2 Configurar application.properties
```properties
# Descomente as linhas do PostgreSQL:
spring.datasource.url=jdbc:postgresql://localhost:5432/automanager
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=sua_senha_aqui
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
spring.jpa.hibernate.ddl-auto=update

# Comente as linhas do H2:
# spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1
# ... (comentar todas as linhas de H2)
```

#### 3 Ajustar Valores
- `localhost` - Host do PostgreSQL
- `5432` - Porta padro do PostgreSQL
- `postgres` - Usurio padro
- `sua_senha_aqui` - Senha do PostgreSQL

#### 4 Compilar e Executar
```bash
mvn clean install
mvn spring-boot:run
```

---

## SQL Server

### Pr-requisitos:
1. SQL Server instalado
2. Banco de dados criado: `CREATE DATABASE automanager;`

### Passos para Usar SQL Server:

#### 1 Descomentar Dependncia (pom.xml)
```xml
<!-- Descomente esta seo -->
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>12.2.0.jre11</version>
    <scope>runtime</scope>
</dependency>
```

#### 2 Configurar application.properties
```properties
# Descomente as linhas do SQL Server:
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=automanager
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=sa
spring.datasource.password=sua_senha_aqui
spring.jpa.database-platform=org.hibernate.dialect.SQLServer2012Dialect
spring.jpa.hibernate.ddl-auto=update

# Comente as linhas do H2:
# spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1
# ... (comentar todas as linhas de H2)
```

#### 3 Ajustar Valores
- `localhost` - Host do SQL Server
- `1433` - Porta padro do SQL Server
- `sa` - Usurio padro (admin)
- `sua_senha_aqui` - Senha do SQL Server

#### 4 Compilar e Executar
```bash
mvn clean install
mvn spring-boot:run
```

---

## Como Mudar de Banco

### Checklist Completo:

1. **No `pom.xml`:**
   -  Descomente a dependncia do novo banco
   -  Comente ou mantenha a do banco anterior

2. **No `application.properties`:**
   -  Descomente todas as linhas do novo banco
   -  Comente todas as linhas do banco anterior
   -  Atualize HOST, PORTA, USURIO, SENHA

3. **Configure o Banco (se necessrio):**
   -  Crie o banco de dados
   -  Configure credenciais

4. **Compile e Teste:**
   ```bash
   # Limpar build anterior
   mvn clean install

   # Executar
   mvn spring-boot:run
   ```

---

##  Configuraes Importantes

### `spring.jpa.hibernate.ddl-auto`

- **H2**: `create-drop` - Cria tabelas na inicializao, deleta ao parar
- **Relacionais**: `update` - Atualiza schema sem perder dados

### Pool de Conexes (Opcional)

Para melhorar performance com bancos relacionais:

```properties
# Adicionar em application.properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

---

##  Troubleshooting

### Erro: "Cannot load driver class: com.mysql.cj.jdbc.Driver"
- Verifique se a dependncia do MySQL est descomentada no pom.xml
- Execute `mvn clean install`

### Erro: "Connection refused"
- Verifique se o banco est rodando
- Verifique HOST e PORTA corretos

### Erro: "Database doesn't exist"
- Crie o banco de dados antes de usar:
  - MySQL: `CREATE DATABASE automanager;`
  - PostgreSQL: `CREATE DATABASE automanager;`
  - SQL Server: Crie via SQL Server Management Studio

---

##  Comparao de Bancos

| Aspecto | H2 | MySQL | PostgreSQL | SQL Server |
|---------|----|----- |------------|------------|
| **Instalao** |  Nenhuma |  Necessria |  Necessria |  Necessria |
| **Desenvolvimento** |  Excelente |  Bom |  Bom |  Complexo |
| **Produo** |  No |  Sim |  Sim |  Sim |
| **Performance** |  Limitada |  Boa |  Excelente |  Excelente |
| **Custo** |  Grtis |  Grtis |  Grtis |  Pago |

---

##  Recomendaes

- **Desenvolvimento**: Use H2 (padro)
- **Testes**: Use H2 ou PostgreSQL
- **Produo**: Use MySQL, PostgreSQL ou SQL Server

---

##  Dvidas?

Todas as configuraes esto comentadas no arquivo `application.properties`. Basta descomentar o banco que deseja usar!
