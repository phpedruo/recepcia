-- Necessária para a restrição de exclusão que impede dois agendamentos
-- sobrepostos para o mesmo profissional (ADR 0004).
CREATE EXTENSION IF NOT EXISTS btree_gist;
