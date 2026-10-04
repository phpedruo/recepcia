#!/bin/bash
# Cria o usuário recepcia, que a API usa: dono do banco, mas sem poderes de
# superusuário, como o usuário que o Heroku entrega. O PostgreSQL não aplica a
# segurança em nível de linha a superusuários, e sem este usuário o isolamento
# entre empresas valeria só em produção (ADR 0004).
#
# A imagem do PostgreSQL roda este script uma vez, ao criar o banco, com o
# superusuário postgres. O recepcia recebe a mesma senha dele. O Docker Compose
# e os testes usam este mesmo script.
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
	-v password="$POSTGRES_PASSWORD" <<'SQL'
CREATE ROLE recepcia LOGIN PASSWORD :'password';
ALTER DATABASE :"DBNAME" OWNER TO recepcia;
SQL
