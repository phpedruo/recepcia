-- As empresas e o isolamento dos dados de cada uma (ADR 0004).

-- A empresa da sessão. A API informa a empresa no começo de cada transação, com
-- set_config('app.business_id', ..., true), e o valor dura até o fim dela. Sem
-- empresa informada, a função devolve NULL, e as tabelas com dados de empresa
-- não mostram nem aceitam nada. O NULLIF cobre a conexão que já teve empresa
-- numa transação anterior: depois dela, o valor fica vazio, e não NULL.
CREATE FUNCTION current_business_id() RETURNS uuid
    LANGUAGE sql STABLE
    AS $$ SELECT NULLIF(current_setting('app.business_id', true), '')::uuid $$;

-- A lista de empresas não tem isolamento: ela é consultada antes de existir
-- uma empresa na sessão, como na escolha da empresa ao entrar no painel.
CREATE TABLE business (
    id   uuid PRIMARY KEY,
    name text NOT NULL
);

-- Receita para toda tabela com dados de empresa, como a de serviços:
--
--   CREATE TABLE offering (
--       id          uuid PRIMARY KEY,
--       business_id uuid NOT NULL REFERENCES business (id),
--       ...
--   );
--   CREATE INDEX ON offering (business_id);
--   ALTER TABLE offering ENABLE ROW LEVEL SECURITY;
--   ALTER TABLE offering FORCE ROW LEVEL SECURITY;
--   CREATE POLICY business_isolation ON offering
--       USING (business_id = current_business_id());
--
-- O índice serve às consultas, que sempre filtram pela empresa. O FORCE faz a
-- regra valer também para o dono da tabela, que no Heroku é o próprio usuário
-- da API. A entidade da tabela estende BusinessScopedEntity. O teste
-- BusinessTablesTests falha se alguma tabela com business_id ficar sem a regra.
