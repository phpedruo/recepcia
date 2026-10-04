# Glossário

A linguagem do domínio da RecepcIA. A documentação usa os termos em português, e o código usa o nome em inglês da segunda coluna. Quando um termo mudar, ele muda aqui primeiro.

## Módulos

Os módulos do backend (ADR 0002) e o pacote de cada um, dentro de `me.recepcia`. Um módulo entra aqui quando é criado.

| Módulo | Pacote |
| --- | --- |
| Empresas e acessos | `business` |
| Catálogo | `catalog` |
| Clientes | `customer` |
| Agenda | `scheduling` |

## Empresas e acessos

| Termo | No código | Definição |
| --- | --- | --- |
| Empresa | `Business` | Negócio que assina a RecepcIA, como um salão ou uma barbearia. Toda informação de cliente, agenda e conversa pertence a uma empresa, e uma empresa nunca vê os dados de outra. |
| Empresa da sessão | `BusinessContext` | A empresa cujos dados o sistema mostra e altera num dado momento, como a que a pessoa escolheu ao entrar no painel. Sem empresa da sessão, nenhum dado de empresa aparece. |
| Segmento | `Segment` | Tipo de negócio da empresa: salão, barbearia, estética, estúdio, pet shop, personal trainer. Muda o vocabulário e alguns campos, mas não as regras centrais. |
| Plano | `Plan` | O que a empresa assina. Define quais módulos ela pode usar. |
| Usuário | `User` | Pessoa com login no painel. |
| Vínculo | `Membership` | Ligação entre um usuário e uma empresa, com um papel. Um usuário pode ter vínculos com várias empresas. |
| Papel | `Role` | O que o usuário pode fazer numa empresa: Dono (`OWNER`), Recepção (`RECEPTION`) ou Profissional (`PROFESSIONAL`). O Administrador da plataforma (`PLATFORM_ADMIN`) fica acima das empresas. |

## Catálogo e agenda

| Termo | No código | Definição |
| --- | --- | --- |
| Serviço | `Offering` | O que a empresa vende, com preço e duração, como "corte masculino, 30 minutos". Não se chama `Service` no código para não confundir com os serviços do Spring. |
| Profissional | `Professional` | Pessoa que executa os serviços e tem agenda própria. Pode ou não ter login no painel. |
| Horário de funcionamento | `OpeningHours` | Dias e horários em que a empresa ou o profissional atende. |
| Cliente | `Customer` | Pessoa atendida pela empresa. No WhatsApp, é identificada pelo código que a Meta gera para cada empresa (BSUID), e não pelo telefone. |
| Agendamento | `Appointment` | Um serviço marcado para um cliente com um profissional, com início e fim. |
| Disponibilidade | `Availability` | Os horários livres de um profissional para um serviço, calculados a partir do horário de funcionamento, dos agendamentos existentes e das regras. |
| Conflito | — | Dois agendamentos do mesmo profissional que se sobrepõem. O banco de dados proíbe. |
| Antecedência mínima | `minimumNotice` | Com quanto tempo de antecedência um horário pode ser marcado. O dono configura, e ela vale para todos que marcam. |
| Intervalo entre horários | `slotInterval` | De quanto em quanto tempo começam os horários livres oferecidos, como 9h00, 9h15, 9h30. A empresa define um padrão, e cada serviço pode ter o seu. |
| Política de cancelamento | `CancellationPolicy` | Até quando um agendamento pode ser cancelado ou remarcado sem penalidade. |

## Atendimento pelo WhatsApp

| Termo | No código | Definição |
| --- | --- | --- |
| Assistente | `Assistant` | A IA que atende os clientes da empresa pelo WhatsApp. Tira dúvidas e marca, remarca e cancela agendamentos pelas funções do sistema. |
| Nome da assistente | `assistantName` | Nome opcional que a empresa dá à assistente. Sem nome, ela se apresenta como "assistente virtual da [empresa]". |
| Confirmação do cliente | — | Resumo que a assistente mostra e o cliente aprova antes de qualquer marcação, remarcação ou cancelamento. |
| Conversa | `Conversation` | Troca de mensagens entre um cliente e uma empresa. |
| Mensagem | `Message` | Uma mensagem da conversa: texto ou áudio já transcrito. |
| Janela de atendimento | `CustomerServiceWindow` | As 24 horas depois da última mensagem do cliente, em que a empresa pode responder livremente, pelas regras da Meta. |
| Transferência para humano | `Handoff` | Quando o cliente pede uma pessoa: a assistente para de responder naquela conversa, e a conversa aparece marcada no painel. |
| Transcrição | `Transcription` | O texto de um áudio enviado pelo cliente. O áudio é descartado depois de transcrito. |
| Limite de respostas | `ReplyQuota` | Máximo de respostas por número de WhatsApp por mês. Ao chegar nele, o sistema para de responder. |

## Privacidade

| Termo | No código | Definição |
| --- | --- | --- |
| Política de privacidade | `PrivacyPolicy` | O texto que explica como os dados do cliente são tratados. Tem versões. |
| Aceite da política | `PolicyAcceptance` | O registro de que o cliente aceitou uma versão da política, com data e hora. Sem aceite, a assistente não atende. |
| Recusa da política | `PolicyRefusal` | O registro de que o cliente não aceitou. Guarda só data e versão, sem o conteúdo das mensagens. |
| Solicitação de exclusão | `ErasureRequest` | Pedido do cliente para apagar os dados dele, recebido pelo e-mail da empresa e executado pelo dono no painel. |
| Registro de auditoria | `AuditLog` | Quem fez o quê e quando, em ações sensíveis: exclusões e acessos do administrador da plataforma. |

## Demonstração

| Termo | No código | Definição |
| --- | --- | --- |
| Empresa de demonstração | `DemoBusiness` | Empresa gerada pelo sistema, com dados inventados. É usada para desenvolver, nas capturas de tela e, a partir da v0.2, no painel público de demonstração. |
| Empresa RecepcIA | — | A própria RecepcIA como empresa no sistema, atendendo pelo número real de WhatsApp da demonstração. Os dados dela nunca aparecem em público. |
