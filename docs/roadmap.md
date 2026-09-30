# Roadmap

## Etapas

### Marco 0: fundação (1 a 2 semanas)

- Documentação das decisões: README, ADRs e glossário.
- Organização no GitHub: quadro, modelos de issue e de PR, marcos e as issues da v0.1.
- Esqueleto do projeto: backend Spring Boot, painel Angular e Docker Compose com PostgreSQL e um servidor de e-mail falso.
- Integração contínua no GitHub Actions.
- Imagem Docker e primeiro deploy no Heroku.

### v0.1: agenda pelo painel (até a semana 8)

- Login e papéis, com uma pessoa podendo fazer parte de várias empresas.
- Cadastro da empresa, dos serviços, dos profissionais e dos horários de funcionamento.
- Clientes.
- Agenda com as regras: horários livres, conflitos e antecedência mínima. Por enquanto, quem agenda é a recepção, pelo painel.
- Painel de demonstração com a empresa gerada, recriada toda noite.

### v0.2: assistente no WhatsApp

- Integração com a API oficial do WhatsApp.
- A assistente tira dúvidas e marca, remarca e cancela pela agenda, com a confirmação do cliente.
- Aceite da política de privacidade e tratamento da recusa.
- Transferência para humano, na forma mínima.
- Transcrição de áudios.
- Limite de respostas por número.
- Conversas visíveis no painel.
- Avaliações da IA com conversas de exemplo.

### v0.3: operação

- Um funcionário assume a conversa pelo painel.
- Confirmação e lembrete automáticos pelo WhatsApp.
- Primeiros indicadores.
- Exclusão de dados pelo painel, com registro de auditoria, e retenção automática das mensagens.

## Backlog depois da v0.3, na ordem

1. Lista de espera: quando alguém cancela, a assistente oferece o horário a quem está esperando.
2. Sinal pago via Pix.
3. Comissão dos profissionais e caixa do dia.
4. Pacotes e fidelidade.
5. Avaliação depois do atendimento.
6. Campanhas, como mensagens para aniversariantes e para clientes sumidos. Mensagens de marketing no WhatsApp são cobradas por envio.
7. Empresas se cadastrando sozinhas e cobrança da assinatura.

Fora do escopo: nota fiscal, que exige certificado digital e integração com a Sefaz.

## Para a fase de vender

Anotado, sem decisão por enquanto:

- abrir CNPJ, provavelmente como microempresa (ME), já que MEI parece não permitir atividade de software. Confirmar com um contador;
- verificar a empresa na Meta e ter o app aprovado como provedor de tecnologia, para as empresas clientes conectarem os próprios números;
- cobrança da assinatura: Asaas e Mercado Pago são candidatos, porque têm Pix recorrente e ambiente de testes;
- revisar a política de privacidade e os termos de uso com um advogado;
- registrar a marca no INPI e o domínio recepcia.com.br.
