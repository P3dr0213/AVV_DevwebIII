# AutoManager - Microserviço de Gestão Automotiva

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8-blue)](https://maven.apache.org/)
[![Security](https://img.shields.io/badge/Security-JWT-red)](https://jwt.io/)

Microserviço REST para gestão de empresas automotivas com autenticação e autorização via **JWT**. Desenvolvido com Spring Boot, Spring Security, JPA/Hibernate e HATEOAS.

---

## Índice

- [Pré-requisitos](#pré-requisitos)
- [Como Rodar](#como-rodar)
- [Segurança e Autenticação](#segurança-e-autenticação)
- [Usuários Pré-carregados](#usuários-pré-carregados)
- [Perfis e Permissões](#perfis-e-permissões)
- [API Endpoints](#api-endpoints)
- [Exemplos de Uso (Postman/Insomnia)](#exemplos-de-uso)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Banco de Dados](#banco-de-dados)

---

## Pré-requisitos

- **Java 17+** — [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+** — [Download](https://maven.apache.org/download.cgi) *(ou use o `mvnw` incluído no projeto)*
- **Postman** ou **Insomnia** para testar os endpoints

---

## Como Rodar

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd automanager
```

### 2. Execute a aplicação

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux / Mac:**
```bash
./mvnw spring-boot:run
```

### 3. Confirme que subiu

A aplicação estará disponível em `http://localhost:8080`.

Ao iniciar, o console exibirá:
```
Started AutomanagerApplication in X.XXX seconds
```

> O banco de dados é **H2 em memória** — os dados são perdidos ao reiniciar. Para usar MySQL ou PostgreSQL, consulte [BANCO_DE_DADOS.md](BANCO_DE_DADOS.md).

---

## Segurança e Autenticação

Todas as rotas (exceto `/login`) exigem um **token JWT** no header da requisição.

### Fluxo de autenticação

**Passo 1 — Fazer login:**

```
POST http://localhost:8080/login
Content-Type: application/json
```
```json
{
  "nomeUsuario": "admin",
  "senha": "admin123"
}
```

**Passo 2 — Copiar o token da resposta:**

O token vem no **header** da resposta (não no body):
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**Passo 3 — Usar o token em todas as requisições seguintes:**

Adicione no header de cada requisição:
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

> No **Postman**: aba `Authorization` → tipo `Bearer Token` → cole apenas o token (sem a palavra "Bearer").

---

## Usuários Pré-carregados

O sistema cria automaticamente 4 usuários ao iniciar:

| ID | Nome de usuário | Senha | Perfil |
|----|----------------|-------|--------|
| 1 | `admin` | `admin123` | ADMINISTRADOR |
| 2 | `gerente` | `gerente123` | GERENTE |
| 3 | `vendedor` | `vendedor123` | VENDEDOR |
| 4 | `cliente` | `cliente123` | CLIENTE |

O usuário 4 (Dom Pedro / CLIENTE) já vem com **1 veículo** vinculado (Mercedes-Benz Classe S, ID=1).

---

## Perfis e Permissões

| Perfil | Permissões |
|--------|-----------|
| **ADMINISTRADOR** | Acesso total a todas as operações de CRUD |
| **GERENTE** | CRUD em Usuários (gerente/vendedor/cliente), Serviços, Vendas, Mercadorias, Veículos |
| **VENDEDOR** | CRUD em Clientes; leitura de Serviços e Mercadorias; criar e ler Vendas |
| **CLIENTE** | Leitura do próprio cadastro e das próprias vendas |

---

## API Endpoints

### Autenticação

| Método | Endpoint | Descrição | Acesso |
|--------|----------|-----------|--------|
| POST | `/login` | Autenticar e obter token JWT | Público |

### Usuários

| Método | Endpoint | Descrição | Perfis permitidos |
|--------|----------|-----------|-------------------|
| GET | `/usuarios` | Listar todos | ADMIN, GERENTE |
| GET | `/usuarios/{id}` | Obter por ID | ADMIN, GERENTE, VENDEDOR, CLIENTE |
| POST | `/usuarios` | Criar usuário | ADMIN, GERENTE, VENDEDOR |
| PUT | `/usuarios/{id}` | Atualizar usuário | ADMIN, GERENTE, VENDEDOR |
| DELETE | `/usuarios/{id}` | Deletar usuário | ADMIN, GERENTE, VENDEDOR |
| PUT | `/usuarios/{id}/mercadorias/{mercadoriaId}` | Vincular mercadoria ao usuário | ADMIN, GERENTE |

### Veículos

| Método | Endpoint | Descrição | Perfis permitidos |
|--------|----------|-----------|-------------------|
| GET | `/veiculos` | Listar todos | ADMIN, GERENTE, VENDEDOR |
| GET | `/veiculos/{id}` | Obter por ID | ADMIN, GERENTE, VENDEDOR, CLIENTE |
| POST | `/veiculos` | Criar veículo | ADMIN, GERENTE |
| PUT | `/veiculos/{id}` | Atualizar veículo | ADMIN, GERENTE |
| DELETE | `/veiculos/{id}` | Deletar veículo | ADMIN, GERENTE |

### Mercadorias

| Método | Endpoint | Descrição | Perfis permitidos |
|--------|----------|-----------|-------------------|
| GET | `/mercadorias` | Listar todas | ADMIN, GERENTE, VENDEDOR |
| GET | `/mercadorias/{id}` | Obter por ID | ADMIN, GERENTE, VENDEDOR |
| POST | `/mercadorias` | Criar mercadoria | ADMIN, GERENTE |
| PUT | `/mercadorias/{id}` | Atualizar mercadoria | ADMIN, GERENTE |
| DELETE | `/mercadorias/{id}` | Deletar mercadoria | ADMIN, GERENTE |

### Serviços

| Método | Endpoint | Descrição | Perfis permitidos |
|--------|----------|-----------|-------------------|
| GET | `/servicos` | Listar todos | ADMIN, GERENTE, VENDEDOR |
| GET | `/servicos/{id}` | Obter por ID | ADMIN, GERENTE, VENDEDOR |
| POST | `/servicos` | Criar serviço | ADMIN, GERENTE |
| PUT | `/servicos/{id}` | Atualizar serviço | ADMIN, GERENTE |
| DELETE | `/servicos/{id}` | Deletar serviço | ADMIN, GERENTE |

### Vendas

| Método | Endpoint | Descrição | Perfis permitidos |
|--------|----------|-----------|-------------------|
| GET | `/vendas` | Listar todas | ADMIN, GERENTE |
| GET | `/vendas/{id}` | Obter por ID | ADMIN, GERENTE, VENDEDOR, CLIENTE |
| POST | `/vendas` | Criar venda | ADMIN, GERENTE, VENDEDOR |
| PUT | `/vendas/{id}` | Atualizar venda | ADMIN, GERENTE |
| DELETE | `/vendas/{id}` | Deletar venda | ADMIN, GERENTE |

### Empresas

| Método | Endpoint | Descrição | Perfis permitidos |
|--------|----------|-----------|-------------------|
| GET | `/empresas` | Listar todas | ADMIN |
| GET | `/empresas/{id}` | Obter por ID | ADMIN |
| POST | `/empresas` | Criar empresa | ADMIN |
| PUT | `/empresas/{id}` | Atualizar empresa | ADMIN |
| DELETE | `/empresas/{id}` | Deletar empresa | ADMIN |

### Outros endpoints (ADMIN / GERENTE)

| Endpoint base | Descrição |
|---------------|-----------|
| `/clientes` | Gestão de clientes |
| `/documentos` | Gestão de documentos |
| `/emails` | Gestão de emails |
| `/telefones` | Gestão de telefones |
| `/enderecos` | Gestão de endereços |
| `/credenciais-usuario` | Gestão de credenciais (ADMIN) |
| `/credenciais-codigo-barra` | Gestão de credenciais QR (ADMIN) |

---

## Exemplos de Uso

### Roteiro completo de teste (execute em ordem)

#### 1. Login como admin

```
POST http://localhost:8080/login
```
```json
{ "nomeUsuario": "admin", "senha": "admin123" }
```
> Copie o token do header `Authorization` da resposta.

---

#### 2. Criar Mercadoria (POST /mercadorias)

```json
{
  "nome": "Oleo Motor 5W30",
  "descricao": "Oleo sintetico para motor",
  "quantidade": 50,
  "valor": 45.90,
  "validade": "2027-12-31T00:00:00.000+00:00",
  "fabricao": "2024-01-15T00:00:00.000+00:00"
}
```
> Retorna a mercadoria com **ID=1**.

---

#### 3. Criar Serviço (POST /servicos)

```json
{
  "nome": "Troca de Oleo",
  "descricao": "Troca de oleo e filtro completo",
  "valor": 150.00
}
```
> Retorna o serviço com **ID=1**.

---

#### 4. Criar Veículo vinculado ao usuário 4 (POST /veiculos)

```json
{
  "tipo": "SUV",
  "modelo": "Toyota Corolla Cross",
  "placa": "ABC1D23",
  "proprietarioId": 4
}
```
> Tipos disponíveis: `HATCH`, `SEDA`, `SUV`, `PICKUP`, `SW`
>
> Retorna o veículo com **ID=2** já vinculado ao usuário 4.

---

#### 5. Verificar usuário com veículos (GET /usuarios/4)

```
GET http://localhost:8080/usuarios/4
```
> O campo `"veiculos"` deve mostrar os dois carros (ID=1 e ID=2).

---

#### 6. Criar Venda completa (POST /vendas)

```json
{
  "identificacao": "VENDA-2024-001",
  "clienteId": 4,
  "funcionarioId": 3,
  "veiculoId": 1,
  "mercadoriasIds": [1],
  "servicosIds": [1]
}
```
> Vincula cliente + funcionário + veículo + mercadoria + serviço em uma venda.

---

#### 7. Vincular mercadoria ao usuário (PUT /usuarios/4/mercadorias/1)

```
PUT http://localhost:8080/usuarios/4/mercadorias/1
```
> Sem body. O usuário 4 agora terá a mercadoria no campo `"mercadorias"`.

---

#### 8. Criar novo usuário com perfil VENDEDOR (POST /usuarios)

```json
{
  "nome": "Joao Santos",
  "nomeSocial": "Joao",
  "perfis": ["VENDEDOR"],
  "telefones": [{ "ddd": "11", "numero": "999887766" }],
  "documentos": [{ "tipo": "CPF", "numero": "11122233344" }],
  "emails": [{ "endereco": "joao@autobots.com" }]
}
```

---

### Testes de autorização (403 esperado)

| Requisição | Token usado | Esperado |
|-----------|------------|----------|
| `POST /mercadorias` | vendedor | **403** Forbidden |
| `GET /usuarios` | cliente | **403** Forbidden |
| `DELETE /servicos/1` | vendedor | **403** Forbidden |
| `GET /usuarios/4` | cliente | **200** OK |
| `GET /mercadorias` | vendedor | **200** OK |
| `GET /vendas/1` | vendedor | **200** OK |

---

## Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── adaptadores/          # UserDetailsService (Spring Security)
├── config/               # Configuração de segurança (Seguranca.java)
├── controles/            # Controllers REST
├── dto/                  # Data Transfer Objects (entrada/saída)
├── entidades/            # Entidades JPA
├── enumeracoes/          # Enums (PerfilUsuario, TipoVeiculo, etc.)
├── excecoes/             # Exception handlers globais
├── filtros/              # Filtros JWT (Autenticador, Autorizador)
├── jwt/                  # Geração e validação de token JWT
├── modelo/               # Modeladores HATEOAS
├── modeladores/          # Conversores DTO ↔ Model
├── repositorios/         # Repositórios Spring Data JPA
├── servicos/             # Regras de negócio
└── AutomanagerApplication.java
```

---

## Banco de Dados

### H2 (padrão — sem instalação)

Configurado automaticamente. Os dados são **perdidos ao reiniciar** o servidor.

```properties
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=create-drop
```

### JWT — Configuração

```properties
jwt.secret=autobots-automanager-chave-secreta-jwt-2024
jwt.expiration=86400000
```

> `jwt.expiration` está em milissegundos. `86400000` = 24 horas.

### Migrar para MySQL / PostgreSQL

Consulte [BANCO_DE_DADOS.md](BANCO_DE_DADOS.md) para instruções detalhadas.

---

## Tecnologias

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 17 | Linguagem |
| Spring Boot | 2.7.18 | Framework web |
| Spring Security | 5.7 | Autenticação e autorização |
| JJWT | 0.9.1 | Geração de tokens JWT |
| Spring Data JPA | 2.7 | Persistência |
| Hibernate | 5.6 | ORM |
| Spring HATEOAS | 1.5 | Hipermídia |
| H2 Database | 2.2 | Banco em memória |
| Lombok | 1.18 | Redução de boilerplate |
| Bean Validation | 2.0 | Validação de entrada |

---

**Desenvolvido para a disciplina Desenvolvimento Web III — ATVIV**
