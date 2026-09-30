# 0010. Nenhum dado real no repositório

- Status: aceita
- Data: 2026-09-30

## Contexto

O repositório pode ficar exposto a qualquer pessoa, porque é portfólio e vai ser público. O sistema, por outro lado, trata dados pessoais reais em produção: clientes que conversam com a assistente e usuários do painel.

## Decisão

Nenhum dado real de pessoa entra no repositório, em nenhuma forma:

- **Dados de teste, dados iniciais, conversas de avaliação da IA e exemplos da documentação:** sempre inventados ou gerados.
- **Capturas de tela e vídeos:** só da empresa de demonstração, que é gerada pelo sistema e recriada toda noite.
- **Arquivos de configuração local (`.env`), logs e exportações do banco:** nunca entram. O `.gitignore` bloqueia esses arquivos, e só um `.env.example` com valores falsos é versionado.
- **Segredos** (chaves e senhas): só em variáveis de ambiente. Uma verificação automática de segredos roda na integração contínua.
- **Revisão:** o checklist de todo PR tem o item "Nenhum dado real de pessoas neste PR".

## Consequências

- Bugs de produção não podem ser reproduzidos copiando dados reais para testes. Eles são reproduzidos com dados inventados que imitam o caso.
- O painel de demonstração público mostra a empresa gerada, e nunca a empresa RecepcIA, que recebe mensagens reais.
