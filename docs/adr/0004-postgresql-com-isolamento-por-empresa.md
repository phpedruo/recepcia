# 0004. Um PostgreSQL para todas as empresas, com isolamento por empresa

- Status: aceita
- Data: 2026-09-30

## Contexto

A RecepcIA é um SaaS: muitas empresas pequenas usando o mesmo sistema. Os dados de uma empresa nunca podem aparecer para outra. A agenda não pode aceitar dois agendamentos no mesmo horário, nem quando dois clientes pedem o último horário no mesmo instante. E o plano gratuito de hospedagem comporta um banco só.

## Decisão

- **PostgreSQL**, um banco só para todas as empresas.
- **Isolamento por empresa:** cada tabela com dados de empresa tem a coluna `business_id`. O Hibernate filtra automaticamente pela empresa da sessão, e o próprio banco bloqueia o acesso cruzado com segurança em nível de linha (row-level security), como segunda camada.
- **Conflitos de agenda:** uma restrição de exclusão (extensão `btree_gist`) impede, no próprio banco, dois agendamentos sobrepostos para o mesmo profissional.
- **Segmentos diferentes:** os campos específicos de cada tipo de negócio ficam em colunas `jsonb`, sem uma tabela por segmento.
- **Mudanças na estrutura:** versionadas com Flyway, junto com o código.
- **Para quando o volume crescer:** a tabela de mensagens passa a ser dividida por mês (particionamento).
- **Indicadores:** calculados com consultas no próprio PostgreSQL, sem banco separado de análise por enquanto.
- **Conhecimento de cada empresa para a IA:** quando for necessário, busca por semelhança de significado com a extensão `pgvector`, no mesmo banco.

Foram descartados um schema por empresa, que exigiria criar e migrar uma área nova para cada empresa, e um banco por empresa, que não cabe no plano gratuito.

## Consequências

- Um erro no filtro da aplicação ainda é barrado pelo banco.
- Todas as consultas e testes precisam considerar a empresa. Há testes específicos de uma empresa tentando ler dados de outra.
- O projeto fica preso ao PostgreSQL, por causa da restrição de exclusão, da segurança em nível de linha, do `jsonb` e do `pgvector`.
