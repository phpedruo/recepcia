# 0003. Java com Spring Boot no backend e Angular no painel

- Status: aceita
- Data: 2026-09-30

## Contexto

O atendimento aos clientes das empresas acontece só pelo WhatsApp, então não há código de atendimento rodando no navegador. O único frontend é o painel usado pelos donos e funcionários, com agenda, clientes, conversas e indicadores.

A escolha da stack seguiu os requisitos do projeto, e não a demanda do mercado.

## Decisão

**Backend:** Java 25, a versão de suporte longo mais recente, com Spring Boot e Maven (pelo Maven Wrapper, que vem no repositório). O ecossistema Spring cobre o que o projeto precisa:

- **Spring Modulith:** organiza e verifica os módulos (ADR 0002).
- **Spring Security:** login e sessões (ADR 0005).
- **Hibernate:** separação dos dados de cada empresa no mesmo banco (ADR 0004).
- **Spring AI:** conexão com os modelos de IA com chamada de funções, trocando de provedor sem reescrever o código (ADR 0007).

**Painel:** Angular com TypeScript, servido pelo próprio Spring Boot, no mesmo endereço da API.

As versões do Spring Boot e do Angular são as estáveis mais recentes que funcionem com o Spring AI e o Spring Modulith.

## Consequências

- São duas linguagens e dois frameworks grandes, o que deixa apertada a meta de 8 semanas para a v0.1.
- Java usa mais memória e demora mais para iniciar do que plataformas mais leves. Por isso a hospedagem precisa manter o servidor sempre ligado (ADR 0008).
- Servir o painel pelo mesmo endereço da API simplifica o cookie de login.
