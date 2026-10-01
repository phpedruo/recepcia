# RecepcIA

Recepcionista virtual com IA no WhatsApp para negócios de horário marcado.

Salões, barbearias, estúdios, pet shops e outros negócios que vivem de agenda recebem as mesmas perguntas o dia inteiro: preço, horário livre, endereço, remarcação. A RecepcIA dá a cada empresa uma assistente no WhatsApp que responde essas perguntas e marca, remarca e cancela horários direto na agenda. Antes de qualquer mudança, o cliente confirma. Donos e funcionários acompanham tudo num painel: agenda, clientes, conversas e indicadores.

> **Status:** Marco 0, a fundação. O esqueleto do projeto está sendo montado. O planejamento está no [roadmap](docs/roadmap.md).

## Como funciona

- **A IA conversa, e o sistema decide.** A assistente não mexe na agenda diretamente: ela chama as funções do sistema, e o sistema recusa qualquer coisa que quebre uma regra, como um horário ocupado, fora do expediente ou em cima da hora.
- **Várias empresas, dados isolados.** Cada empresa só enxerga os próprios dados. O isolamento é garantido pela aplicação e pelo próprio banco de dados.
- **Privacidade desde o início.** O cliente aceita a política de privacidade antes de ser atendido, pode pedir para falar com uma pessoa a qualquer momento, e as mensagens são apagadas automaticamente depois de 12 meses.

## Tecnologia

| Parte | Escolha |
| --- | --- |
| Backend | Java 25, Spring Boot, Spring Modulith, Spring Security, Spring AI |
| Painel | Angular com TypeScript |
| Banco de dados | PostgreSQL |
| WhatsApp | API oficial da Meta (Cloud API) |
| IA | Groq, com Cloudflare Workers AI de reserva |
| Hospedagem | Heroku, com imagem Docker |
| Integração contínua | GitHub Actions |

Os motivos de cada escolha estão nos [ADRs](docs/adr/README.md).

## Como rodar

É preciso ter o Java 25 e o Docker. O Maven vem junto, pelo Maven Wrapper. Para trabalhar no painel, também é preciso o Node.js 24.

### Backend

```bash
cd backend
./mvnw test                   # roda os testes; os de integração sobem um PostgreSQL no Docker
./mvnw spring-boot:test-run   # roda a API com um PostgreSQL temporário no Docker
./mvnw verify                 # roda os testes e gera o .jar com o painel dentro
```

Com a API rodando, <http://localhost:8080/actuator/health> responde `{"status":"UP"}`. O `./mvnw verify` baixa o próprio Node.js para gerar o painel, então não depende do Node instalado na máquina.

> Feche o `npm start` e o `npm test` do painel antes do `./mvnw verify`. Ele roda `npm ci`, que apaga e reinstala o `node_modules` do painel, e esses processos podem começar a dar erro. Se esquecer, é só reiniciá-los depois.

### Painel

```bash
cd frontend
npm ci      # instala as dependências
npm start   # abre o painel em http://localhost:4200 e repassa /api e /actuator para a API na porta 8080
npm test    # roda os testes do painel
```

## Documentação

- [Roadmap](docs/roadmap.md): etapas, backlog e o que falta para vender.
- [Decisões de arquitetura (ADRs)](docs/adr/README.md).
- [Glossário](docs/glossario.md): a linguagem do domínio e os nomes usados no código.
- [Processo de desenvolvimento](docs/processo.md): quadro, issues, branches, commits e definição de pronto.

## Uso de IA no desenvolvimento

Este projeto é desenvolvido com a ajuda do Claude, em modo híbrido:

- o autor escreve as regras de negócio e os testes delas;
- a IA ajuda com a estrutura do projeto, a infraestrutura e a revisão de código.

Cada PR informa o que foi feito com ajuda da IA.

## Dados

Nenhum dado real de pessoas está neste repositório. Dados de teste, exemplos e capturas de tela são sempre inventados ([ADR 0010](docs/adr/0010-nenhum-dado-real-no-repositorio.md)).

## Licença

[AGPL-3.0](LICENSE).
