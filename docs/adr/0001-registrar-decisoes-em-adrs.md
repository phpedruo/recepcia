# 0001. Registrar as decisões de arquitetura em ADRs

- Status: aceita
- Data: 2026-09-30

## Contexto

O projeto é feito por uma pessoa só, com cerca de 10 horas por semana, e deve durar meses. Muitas decisões foram tomadas antes de existir qualquer código. Sem registro, daqui a um ano não será possível lembrar por que cada escolha foi feita, nem explicá-la numa entrevista.

## Decisão

Toda decisão de arquitetura, tecnologia ou processo que seja difícil de desfazer vira um ADR (registro de decisão de arquitetura) em `docs/adr/`:

- numerado em sequência (`0001`, `0002`...), com nome curto em português;
- com as seções Contexto, Decisão e Consequências;
- escrito em português, como o resto da documentação.

Um ADR aceito não é reescrito. Se a decisão mudar, um ADR novo a substitui, e o antigo passa para o status "substituída por NNNN".

## Consequências

- O histórico das decisões fica no repositório, junto com o código que elas afetam.
- Toda decisão nova desse tipo custa alguns minutos de escrita.
