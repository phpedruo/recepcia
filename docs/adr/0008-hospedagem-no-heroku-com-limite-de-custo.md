# 0008. Hospedagem no Heroku, com limite de custo e portabilidade

- Status: aceita
- Data: 2026-09-30

## Contexto

O orçamento é zero no início. O servidor precisa ficar sempre ligado, porque o WhatsApp avisa o sistema de cada mensagem na hora e os planos gratuitos que "dormem" levam cerca de 1 minuto para acordar. Java também precisa de memória suficiente.

O GitHub Student Developer Pack dá US$ 13 por mês de crédito no Heroku durante 24 meses. Para ativar, é preciso cadastrar um cartão, que só é cobrado se o uso passar do crédito. Não há opção de teto de gasto no Heroku, mas cada recurso tem um preço mensal máximo.

## Decisão

**Hospedagem no Heroku, pago com o crédito do Student Pack, com estas regras para nunca passar do crédito:**

1. Só dois recursos: um servidor Basic, que nunca dorme (máximo de US$ 7 por mês), e um PostgreSQL Essential-0 (máximo de US$ 5 por mês). Somam US$ 12.
2. Nenhum complemento de terceiros, porque o crédito não os cobre.
3. Nunca um segundo servidor. As tarefas agendadas rodam dentro do próprio sistema, e não no agendador do Heroku.
4. Um ambiente só no Heroku, o de produção. Desenvolvimento e testes rodam localmente e no GitHub Actions.
5. O uso do mês é conferido na aba de cobrança do Heroku.

**Portabilidade, para dar para trocar de hospedagem:**

- A aplicação é uma imagem Docker gerada na integração contínua.
- Toda a configuração vem de variáveis de ambiente.
- O endereço do painel e o que recebe as mensagens do WhatsApp usam o domínio próprio, e não o endereço do Heroku.

**Outros serviços, nos planos gratuitos:**

- Resend para e-mails (3.000 por mês);
- Sentry para erros;
- Grafana Cloud para métricas e logs.

**Domínio:** recepcia.me, grátis no primeiro ano pelo Student Pack. O recepcia.com.br estava livre em 2026-09-30 e fica para depois, com o risco aceito de alguém registrar antes.

## Consequências

- O projeto depende de o Student Pack ser aprovado. O crédito acaba em 24 meses.
- Trocar de hospedagem é exportar e importar o banco, apontar o domínio para o lugar novo e subir a mesma imagem Docker.
- Como não há ambiente de homologação, o que vai para a `main` vai direto para produção. A proteção vem dos testes na integração contínua.
