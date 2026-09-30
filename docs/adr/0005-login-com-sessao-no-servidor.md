# 0005. Login do painel com sessão no servidor e papéis por empresa

- Status: aceita
- Data: 2026-09-30

## Contexto

O painel é usado por donos e funcionários de várias empresas. Uma mesma pessoa pode trabalhar em mais de uma empresa, como um profissional que atende em dois salões. O login precisa ser seguro num painel web e fazer parte do que o projeto demonstra.

## Decisão

- **Sessão no servidor:** o Spring Security guarda a sessão no próprio PostgreSQL (Spring Session JDBC), para ela sobreviver a reinícios. O navegador recebe só um cookie `HttpOnly`, `Secure` e `SameSite`, que o JavaScript da página não consegue ler, com proteção contra CSRF.
- **Recuperação de senha por e-mail.**
- **Papéis:**
  - **Administrador da plataforma:** gerencia todas as empresas. Todo acesso dele aos dados de uma empresa fica registrado.
  - **Dono:** faz tudo dentro da empresa, inclusive gerenciar usuários e plano.
  - **Recepção:** agenda, clientes e conversas.
  - **Profissional:** só a própria agenda e os clientes que atende.
- **Vínculo pessoa-empresa:** um usuário pode ter vínculos com várias empresas, com um papel em cada, e escolhe a empresa ao entrar no painel.

Foram descartados:
- tokens JWT guardados no navegador, que ficam expostos se alguém rodar um script malicioso na página e são difíceis de revogar;
- um servidor de identidade separado (Keycloak), que seria mais um servidor para manter;
- um serviço pronto (Auth0 e parecidos), que tiraria do projeto uma parte que vale demonstrar.

## Consequências

- O painel precisa estar no mesmo domínio da API (ADR 0003).
- A lógica de login, recuperação de senha e permissões é mantida no próprio projeto.
