# 0013. Integração contínua no GitHub Actions, com Semgrep e gitleaks

- Status: aceita
- Data: 2026-10-01

## Contexto

O ADR 0011 deixou o repositório privado por enquanto. Em 2026-10-01, ficou decidido que ele continua privado ao montar a integração contínua. Em repositório privado, mesmo com o GitHub Pro do Student Pack, a análise de segurança do código (CodeQL) e a detecção de segredos do GitHub são pagas.

O ADR 0010 exige uma verificação automática de segredos. O ADR 0012 exige que todos os testes rodem a cada PR, com a cobertura exibida no README.

## Decisão

A integração contínua roda no GitHub Actions, a cada PR e a cada push na `main`, com quatro verificações:

- **Backend:** o `./mvnw verify`, com os testes, o PostgreSQL do Testcontainers, a cobertura do JaCoCo e o `.jar` com o painel dentro.
- **Painel:** os testes do Angular, com a cobertura do Vitest.
- **Segredos:** o gitleaks procura chaves e senhas em todo o histórico do repositório.
- **Segurança do código:** o Semgrep, com as regras gratuitas do conjunto `p/default`.

A regra de proteção da `main` exige as quatro antes do merge.

Outras regras:

- **Selos de cobertura:** depois de cada merge, a integração contínua gera os selos e os publica na branch `badges`, de onde o README os mostra. Assim, nenhum serviço externo, como o Codecov, precisa de acesso ao código.
- **Actions fixadas pelo hash do commit, e não pela versão.** Uma versão pode ser trocada depois de publicada, e o hash não. O Dependabot propõe as atualizações uma vez por mês, só com versões publicadas há pelo menos 7 dias.

## Consequências

- Os resultados do Semgrep e do gitleaks aparecem no log de cada execução, e não na aba de segurança do GitHub, que também é paga em repositório privado.
- Os minutos do GitHub Actions têm cota: 3.000 por mês no GitHub Pro.
- As imagens do gitleaks e do Semgrep têm a versão fixada no workflow e são atualizadas à mão.
- Quando o repositório abrir, o CodeQL e a detecção de segredos do GitHub ficam grátis e podem substituir o Semgrep e o gitleaks.
