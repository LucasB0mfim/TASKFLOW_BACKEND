# TaskFlow API
TaskFlow é uma API para gerenciamento de tarefas, construída com Spring Boot e projetada para ser executada em um ambiente Docker. A API permite realizar operações de CRUD e reordenar tarefas de forma flexível.

## Tecnologias Utilizadas
- **Java 17:** Linguagem principal para desenvolvimento.


- **Spring Boot 3.3.6:** Framework para desenvolvimento rápido e simplificado.


- **PostgreSQL:** Banco de dados relacional.


- **JPA/Hibernate** Para mapeamento objeto-relacional (ORM).


- **Docker:** Para facilitar a execução em contêineres.


- **Maven:** Gerenciador de dependências e build.


- **SLF4J:** Biblioteca de logging.

## Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas no seu sistema:

- **Docker** (versão mais recente)


- **Docker Compose** (separado ou integrado ao Docker Desktop)

## Endpoints Principais
| Método  | Endpoint                  | Descrição                           |
|---------|---------------------------|-------------------------------------|
| GET     | `/api/tarefas`            | Lista todas as tarefas.            |
| POST    | `/api/tarefas`            | Cria uma nova tarefa.              |
| PUT     | `/api/tarefas/{id}`       | Atualiza uma tarefa existente.     |
| DELETE  | `/api/tarefas/{id}`       | Exclui uma tarefa pelo ID.         |
| PUT     | `/api/tarefas/reordenar`  | Reordena as tarefas.               |

## Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## Contato
**Autor:** Lucas Bomfim

**E-mail:** lucasbomfimsobral@gmail.com