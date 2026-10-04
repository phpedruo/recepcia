-- Só nos testes: uma tabela com dados de empresa, montada pela receita da
-- migração V2, para provar o isolamento antes de existirem as tabelas de
-- verdade. É repetível (R__) para rodar depois das migrações versionadas, sem
-- disputar número com elas.
CREATE TABLE isolation_probe (
    id          uuid PRIMARY KEY,
    business_id uuid NOT NULL REFERENCES business (id),
    label       text NOT NULL
);
CREATE INDEX ON isolation_probe (business_id);
ALTER TABLE isolation_probe ENABLE ROW LEVEL SECURITY;
ALTER TABLE isolation_probe FORCE ROW LEVEL SECURITY;
CREATE POLICY business_isolation ON isolation_probe
    USING (business_id = current_business_id());
