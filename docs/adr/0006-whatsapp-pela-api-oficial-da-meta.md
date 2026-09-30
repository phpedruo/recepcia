# 0006. WhatsApp pela API oficial da Meta

- Status: aceita
- Data: 2026-09-30

## Contexto

O atendimento aos clientes das empresas acontece só pelo WhatsApp. Existem bibliotecas não oficiais (Evolution API, Baileys) que são grátis e muito usadas no Brasil, mas elas violam os termos do WhatsApp, o número pode ser banido a qualquer momento e não dá para vender um produto construído sobre elas.

Situação levantada em 2026-09-30:
- **Cobrança:** a partir de 2026-10-01, a Meta cobra as respostas ao cliente, cerca de R$ 0,035 por mensagem no Brasil. Empresas parceiras da Meta relatam uma franquia de 1.000 respostas grátis por número por mês e a exigência de um cartão cadastrado para as respostas continuarem sendo entregues. Nenhuma das duas coisas aparece na página oficial.
- **Número de teste:** a Meta oferece um número de teste gratuito, que só envia para números cadastrados e não recebe mensagens de fora.
- **Sem CNPJ:** dá para ligar um número real sem verificar empresa. As respostas a quem escreve primeiro não têm limite de quantidade.
- **Política da Meta:** permite assistentes de atendimento de uma empresa e exige um caminho claro para o cliente falar com um humano.

## Decisão

- **API oficial da Meta (Cloud API)**, chamada com o cliente HTTP do Spring, porque não existe biblioteca Java oficial. A integração fica atrás de uma interface do módulo Conversas, para dar para trocar de provedor sem mexer no resto.
- **Desenvolvimento:** usa o número de teste da Meta.
- **Demonstração:** um número real, com um chip pré-pago que nunca teve WhatsApp, atendendo a própria RecepcIA. O chip é comprado antes da v0.2.
- **Recebimento de mensagens:** o endereço que recebe as mensagens responde na hora e processa depois. Mensagens repetidas, que a Meta pode entregar mais de uma vez, são descartadas pelo identificador.
- **Identificação do cliente:** pelo código que a Meta gera para cada empresa (BSUID), porque o telefone pode não vir na mensagem.
- **Limite de respostas por número:** o sistema conta as respostas enviadas no mês e para de responder ao chegar no limite, que é de 900 na demonstração. O limite é configurável.
- **Humano:** desde a v0.2, quando o cliente pede uma pessoa, a assistente para de responder naquela conversa, marca a conversa no painel e passa o contato da empresa.
- **Áudios:** baixados, transcritos (ADR 0007) e descartados em seguida.

## Consequências

- Para atender empresas de verdade, será preciso ter CNPJ, verificar a empresa na Meta, ter o app aprovado e usar o cadastro de números da Meta para provedores de tecnologia (Tech Provider com Embedded Signup). Cada empresa cliente paga as próprias mensagens direto para a Meta.
- No primeiro mês com o número real, é preciso conferir no painel de cobrança da Meta se a franquia gratuita existe. Se não existir, o limite de respostas deve ser reduzido.
