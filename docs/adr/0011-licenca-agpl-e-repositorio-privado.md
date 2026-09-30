# 0011. Licença AGPL-3.0, com o repositório privado por enquanto

- Status: aceita
- Data: 2026-09-30

## Contexto

O projeto é ao mesmo tempo portfólio, que precisa ser visto, e um possível negócio, que precisa de proteção. Com uma licença permissiva como a MIT, qualquer pessoa poderia copiar o código e vender um SaaS fechado concorrente.

## Decisão

- **Licença AGPL-3.0.** Qualquer pessoa pode usar, estudar, modificar e vender o código. Mas quem rodar uma versão modificada como serviço online é obrigado a publicar o código dela com a mesma licença.
- **O repositório fica privado por enquanto,** e vai ser aberto mais tarde.

## Consequências

- Como único autor, o dono do projeto pode mudar a licença das versões futuras. As versões já publicadas continuam com a AGPL.
- Enquanto o repositório for privado, no plano gratuito do GitHub:
  - a análise de segurança do código (CodeQL) e a detecção de segredos do GitHub são pagas. As alternativas gratuitas são o Semgrep e o gitleaks;
  - o GitHub Actions tem uma cota mensal de minutos;
  - a proteção da branch `main` exige o GitHub Pro, que vem no Student Pack.
- Essas limitações precisam ser resolvidas antes de montar a integração contínua: ou o repositório é aberto, ou as alternativas são adotadas.
