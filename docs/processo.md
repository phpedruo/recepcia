# Processo de desenvolvimento

O projeto é feito por uma pessoa só, com cerca de 10 horas por semana. O processo é Kanban, sem cerimônias de Scrum, e tudo fica registrado no GitHub.

## Quadro

O quadro fica no GitHub Projects, com estas colunas:

**Backlog → A fazer → Em andamento → Em revisão → Concluído**

No máximo 2 itens ficam em "Em andamento" ao mesmo tempo.

## Issues

Cada funcionalidade vira uma issue com:

- uma história de usuário: "Como [papel], quero [ação], para [benefício]";
- critérios de aceite no formato Dado/Quando/Então. Cada critério vira pelo menos um teste;
- o marco (milestone) da versão a que pertence.

## Branches e PRs

- Uma branch curta por issue, criada a partir da `main`, com nome como `feat/42-cancelar-agendamento`.
- PRs pequenos, ligados à issue.
- Revisão: o autor revisa com o checklist do modelo de PR, e o Claude revisa também.
- O merge na `main` só acontece com a integração contínua passando.
- Cada merge na `main` vai para produção automaticamente (ADR 0008).

## Commits

[Conventional Commits](https://www.conventionalcommits.org/pt-br/), com mensagem em português. Exemplos:

- `feat: permite cancelar agendamento pelo painel`
- `fix: impede agendamento fora do horário de funcionamento`
- `test: cobre dois clientes disputando o último horário`
- `docs: registra ADR de estratégia de testes`

## Versões e releases

- Versionamento semântico: `v0.1.0`, `v0.2.0`...
- Cada release tem a lista de mudanças e um resumo curto, pronto para virar post no LinkedIn. Publicar é opcional.

## Definição de pronto

Um item está pronto quando:

1. os critérios de aceite estão cobertos por testes, e os testes passam;
2. o PR foi revisado;
3. a documentação afetada foi atualizada: README, ADR ou glossário;
4. o código está em produção.

## Marcos

Cada versão do [roadmap](roadmap.md) é um marco (milestone) no GitHub, com data prevista: Marco 0, v0.1, v0.2 e v0.3.

O trabalho é planejado em ciclos de duas semanas, de cerca de 20 horas cada, no campo "Ciclo" do quadro.

## Idiomas

- Documentação, issues, PRs e mensagens de commit em português.
- Nomes no código (classes, métodos, variáveis, tabelas) em inglês. A correspondência entre os termos está no [glossário](glossario.md).

## Uso de IA

O projeto é desenvolvido com o Claude, em modo híbrido:

- o autor escreve as regras de negócio e os testes delas;
- o Claude ajuda com a estrutura do projeto, a infraestrutura e a revisão.

Cada PR informa, no campo "Uso de IA neste PR", o que foi feito com ajuda da IA. O resumo desse uso fica no README.

## Dados

Nenhum dado real de pessoas entra no repositório (ADR 0010).
