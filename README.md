# Lanchonete API

![GitHub language count](https://img.shields.io/github/languages/count/zacseriano/lanchonete-api-v2) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zacseriano/lanchonete-api-v2) ![GitHub repo size](https://img.shields.io/github/repo-size/zacseriano/lanchonete-api-v2) ![GitHub last commit](https://img.shields.io/github/last-commit/zacseriano/lanchonete-api-v2)

## Sobre a API

Uma API para lanchonete, feita com Java, Spring Boot e Spring Framework.

## Recursos

A API tem os seguintes HTTP endpoints:

* Cadastrar Gestor: `POST/cadastrarGestor`
* Cadastrar Cliente: `POST/cadastrarCliente`
* Login: `POST/login`
* Consultar Pedido: `GET/cliente/consultarPedido`
* Gerenciar Pedido: `PUT/cliente/gerenciarPedido`
* Gerenciar Pedido: `GET/cliente/gerenciarPedido/{id}`
* Solicitar Pedido: `GET/cliente/solicitarPedido`
* Solicitar Pedido: `POST/cliente/solicitarPedido`
* Solicitar Pedido: `PUT/cliente/solicitarPedido`
* Cancelar Pedido: `PUT/gestor/cancelarPedido`
* Gerenciar Pedido: `GET/gestor/cancelarPedido`
* Gerenciar Pedido: `PUT/gestor/cancelarPedido`
* Gerenciar Produto: `DELETE/gestor/gerenciarProduto`
* Gerenciar Produto: `GET/gestor/gerenciarProduto`
* Gerenciar Produto: `POST/gestor/gerenciarProduto`
* Gerenciar Produto: `PUT/gestor/gerenciarProduto`
* Gerenciar Produto: `GET/gestor/gerenciarProduto/{id}`

### Detalhes

Para detalhes dos recursos, testes, retornos, explicações, enumerações dentre outros favor visitar a documentação presente na API do Swagger que foi configurada neste projeto:

* Swagger (API de documentação): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Tecnologias usadas

O projeto foi desenvolvido utilizando:

* **Java 11 (Java Development Kit - JDK: 11.1.1)**
* **Spring Boot 2.4.5**
* **Maven**
* **PostgreSQL 10**
* **Swagger 2.7.0**
* **JPA**
* **Spring Security**
* **JWT**

### Compilação e Pacote

Baixe o conteúdo do código, ícone presente na aba superior esquerda de nome Code e cor verde, extraia o arquivo lanchoneteapi.zip para a Workspace da sua IDE preferida, neste projeto foi utilizado o Eclipse. No Eclipse, siga os passos: File > Import > Existing Maven Projects (selecione o caminho da pasta que contém o conteúdo do arquivo lanchoneteapi.zip)

### Execução

Você precisa ter **PostgreSQL 10 ou versão superior** instalada na sua máquina para rodar a API. Ao instalar informe a senha desejada para o usuario `postgres` padrão. Depois de instalada, no `pgAdmin` crie um banco de dados `lanchonete`. Se você não tem `pgAdmin` instalado você pode rodar o `SQLShell`, logar no usuário padrão com a senha que você definiu e digitar os seguintes códigos

```
CREATE DATABASE lanchonete;
```

Depois de criar o banco de dados da API, você precisa colocar no **Postgres** os dados do `username` e do `password` padrões no arquivo `application.properties` presente no `src/main/resource`. As modificações devem ser as seguintes:

```properties
spring.datasource.username=
spring.datasource.password=
```
### Rodando a API

Para rodar a sua API você deve ir na classe LanchoneteapiApplication, na pasta raiz, e rodar de acordo com sua IDE favorita. No Eclipse utilize o comando `Run`

Por padrão, a API estará disponível em [http://localhost:8080/](http://localhost:8080/)

### Documentação

* Swagger (API de documentação): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
