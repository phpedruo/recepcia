# 0007. Provedor de IA: Groq, com troca pelo Spring AI

- Status: aceita
- Data: 2026-09-30

## Contexto

A assistente precisa de um modelo de IA que entenda português, chame funções do sistema e transcreva áudios. O orçamento é zero no início, e as conversas contêm nomes e dados de clientes, então o provedor não pode usar esses dados para treinar modelos.

Levantamento de 2026-09-30:

| Provedor | Situação |
| --- | --- |
| Groq | Gratuito, com chamada de funções e uso comercial permitido. O contrato proíbe treinar com os dados, e dá para ativar a opção de não guardar nada. Limite de 8 mil tokens por minuto. Os dados passam por servidores nos EUA. Transcreve até 8 horas de áudio por dia de graça. |
| Cloudflare Workers AI | Gratuito e não treina com os dados, mas dá para cerca de 80 chamadas por dia, e a chamada de funções ainda está em teste. |
| Gemini (plano gratuito) | **Descartado.** Treina com os dados, e os próprios termos pedem para não enviar dados pessoais. |
| Mistral | Treina com os dados por padrão, com opção de desligar. |
| Claude (Anthropic) e OpenAI | Sem plano gratuito. |

## Decisão

- **Provedores:** Groq como principal e Cloudflare Workers AI como reserva, os dois ligados pelo Spring AI. A retenção zero do Groq fica ativada.
- **O que a assistente pode fazer:** tirar dúvidas, e marcar, remarcar e cancelar agendamentos, sempre chamando as funções do sistema. Ela nunca altera a agenda diretamente. Antes de confirmar qualquer mudança, mostra um resumo e o cliente aprova.
- **O que ela não pode fazer:** a assistente não tem nenhuma função de apagar dados (ADR 0009).
- **Escolha do modelo:** decidida por avaliações, um conjunto de conversas de exemplo em português com o resultado esperado. As conversas são sempre inventadas (ADR 0010).
- **Troca de provedor:** quando houver receita, dá para trocar por OpenAI ou Claude. Antes da troca, as mesmas avaliações rodam contra o provedor novo.

## Consequências

- O limite de 8 mil tokens por minuto aguenta a demonstração, mas poucas conversas ao mesmo tempo.
- A política de privacidade precisa informar que os dados passam por servidores nos EUA.
- Trocar de provedor muda a política de privacidade, e os clientes precisam aceitar a versão nova (ADR 0009).
- A qualidade em português dos modelos gratuitos ainda não foi verificada, e isso é feito na v0.2.
