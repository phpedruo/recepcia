# 0009. Privacidade e LGPD

- Status: aceita
- Data: 2026-09-30

## Contexto

A assistente conversa com os clientes das empresas pelo WhatsApp e trata dados pessoais: nome, identificador no WhatsApp, horários e o conteúdo das conversas. As conversas também passam por um provedor de IA.

## Decisão

**Responsabilidades.** Cada empresa é a controladora dos dados dos clientes dela, e a RecepcIA é a operadora, que trata esses dados em nome da empresa. Isso fica escrito nos termos de uso.

**Aceite obrigatório.**
- Na primeira mensagem, a assistente avisa que é uma assistente virtual, envia o link da política de privacidade e pede o aceite.
- O aceite fica registrado com data, hora e versão da política.
- Quando a política mudar, a assistente pede um aceite novo.

**Recusa.**
- Se o cliente não aceitar, o atendimento pelo WhatsApp termina, com uma mensagem que explica o motivo e informa outro contato da empresa, cadastrado no painel.
- Se o cliente escrever de novo depois, a assistente pede o aceite outra vez.
- O sistema guarda só o registro da recusa (data e versão da política), e não o conteúdo das mensagens.
- As conversas de quem não aceitou nunca chegam ao provedor de IA.

**Falar com um humano.** O cliente pode pedir a qualquer momento (ADR 0006).

**Exclusão de dados.**
- O pedido é feito pelo e-mail da empresa, informado na política de privacidade, e não pelo WhatsApp.
- O dono executa a exclusão pelo painel, informando a data em que recebeu o pedido. O sistema registra quem executou, quando e o que foi excluído.
- Dados que a empresa é obrigada a guardar, como pagamentos, são anonimizados em vez de apagados.
- A assistente não tem nenhuma função de apagar dados, então ninguém consegue convencê-la a fazer isso.

**Retenção.**
- As mensagens das conversas são apagadas automaticamente depois de 12 meses, por uma tarefa agendada.
- O histórico de atendimentos fica guardado enquanto o cliente estiver ativo.
- Os áudios são descartados logo depois de transcritos, e fica só o texto.

**Outras regras.**
- O provedor de IA não pode treinar modelos com os dados (ADR 0007).
- Dados reais só existem no ambiente de produção (ADR 0010).
- O segmento de saúde fica para depois, porque dados de saúde têm regras mais rígidas na LGPD.

## Consequências

- O fluxo de aceite, os registros de aceite e recusa, a exclusão auditada e a retenção automática são requisitos da v0.2 e precisam de testes.
- Antes de vender para empresas, a política de privacidade e os termos de uso precisam ser revisados por um advogado.
