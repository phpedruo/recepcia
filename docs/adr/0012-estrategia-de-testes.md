# 0012. Estratégia de testes

- Status: aceita
- Data: 2026-09-30

## Contexto

O projeto é desenvolvido com TDD. As regras de negócio (disponibilidade, conflitos, antecedência, cancelamento, aceite da política) são o centro do produto e o que a IA usa para agir. Erros de isolamento entre empresas ou de concorrência na agenda seriam graves.

## Decisão

- **Regras de negócio:** TDD estrito, com testes de unidade rápidos que não usam banco. As regras ficam separadas do framework e do banco justamente para isso.
- **API e persistência:** testes de integração com um PostgreSQL de verdade rodando em container (Testcontainers). Três casos são obrigatórios:
  - dois clientes pegando o último horário ao mesmo tempo, com só um deles conseguindo;
  - uma empresa tentando ler dados de outra, com o acesso barrado;
  - a mesma mensagem do WhatsApp entregue duas vezes, com só uma sendo processada.
- **Arquitetura:** testes que verificam as fronteiras entre módulos (Spring Modulith e ArchUnit).
- **Fluxos principais do painel:** poucos testes de ponta a ponta no navegador. A ferramenta é decidida quando esses testes começarem.
- **IA (a partir da v0.2):** avaliações com conversas de exemplo inventadas, cada uma com o resultado esperado.
- **Integração contínua:** roda tudo a cada PR, e o merge só acontece com tudo passando.
- **Cobertura:** medida e exibida no README, sem porcentagem mínima obrigatória. O critério é o TDD, não o número.

## Consequências

- O Docker precisa estar disponível localmente e na integração contínua.
- Os testes de integração são mais lentos, então precisam ser poucos e focados.
