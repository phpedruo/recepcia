# 0002. Monólito modular num repositório único

- Status: aceita
- Data: 2026-09-30

## Contexto

A RecepcIA atende várias empresas e vai ganhar muitas funcionalidades com o tempo: agenda, clientes, conversas, indicadores, pagamentos. Ao mesmo tempo, é feita por uma pessoa só, com orçamento zero e hospedagem gratuita. Microsserviços exigiriam vários deploys, comunicação entre serviços e mais infraestrutura, sem ganho nenhum nesta fase.

## Decisão

- **Um repositório só (monorepo)**, com o backend, o painel, a infraestrutura local e a documentação.
- **Um backend só, dividido em módulos** com fronteiras claras, organizados com o Spring Modulith:

  | Módulo | Responsabilidade |
  |---|---|
  | Empresas e acessos | empresas, planos, usuários, vínculos e papéis |
  | Catálogo | serviços, profissionais e horários de funcionamento |
  | Clientes | cadastro e histórico dos clientes de cada empresa |
  | Agenda | disponibilidade e agendamentos, com as regras |
  | Conversas | integração com o WhatsApp, conversas e mensagens |
  | Assistente | a IA que atende e usa as funções dos outros módulos |
  | Notificações | e-mails e mensagens enviadas por iniciativa do sistema |
  | Indicadores | números do painel |

- Os módulos só se comunicam por interfaces públicas e eventos. Um teste automático do Spring Modulith falha se um módulo acessar o interior de outro.
- Cada funcionalidade nova entra como um módulo novo.
- Cada empresa tem os módulos liberados conforme o plano que assina.
- Um módulo só vira serviço separado se um dia precisar escalar sozinho. O candidato mais provável é o que recebe as mensagens do WhatsApp.

## Consequências

- Um deploy, um banco e um processo para acompanhar.
- A separação entre módulos depende da disciplina verificada pelos testes, e não de uma fronteira de rede.
- Extrair um módulo no futuro é possível, mas não gratuito.
