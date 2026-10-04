# 0014. Chaves UUID versão 7, geradas pela aplicação

- Status: aceita
- Data: 2026-10-04

## Contexto

A tabela das empresas, a primeira do sistema, define o tipo da coluna `business_id`, que vai estar em quase todas as outras tabelas. As chaves também aparecem nos endereços da API e do painel. Trocar o tipo depois exigiria migrar todas as tabelas.

## Decisão

- Toda chave primária é um UUID, guardado no tipo `uuid` do PostgreSQL.
- A aplicação gera as chaves pelo Hibernate, na versão 7 do UUID (`@UuidGenerator(style = VERSION_7)`). A versão 7 começa pela data e hora, então as chaves novas saem em ordem e entram no fim do índice. As da versão 4, aleatórias, se espalham pelo índice inteiro.

Foram descartados os números em sequência (`bigint`). Eles ocupam metade do espaço, mas revelam quantas empresas, clientes e agendamentos existem e deixam adivinhar as chaves vizinhas nos endereços.

## Consequências

- Cada chave ocupa 16 bytes, o dobro de um `bigint`, o que não pesa no volume esperado.
- Uma chave não dá pista das outras, mas o UUID versão 7 revela a data e a hora em que o registro foi criado.
- A chave existe antes de o registro ser gravado. Os testes e o gerador de dados inventados criam as próprias chaves, sem consultar o banco.
