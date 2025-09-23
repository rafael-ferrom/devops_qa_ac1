# DQAP - Sistema de Progresso de Alunos

Este projeto é uma aplicação Spring Boot que permite que alunos acompanhem seu progresso em cursos concluídos, incluindo a média geral e a quantidade de cursos restantes para atingir o bônus de 12 cursos.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **Banco de Dados**
  - H2 (em memória)
  - MySQL (opcional)
- **Testes**
  - JUnit 5
  - Mockito
- **Jacoco** para cobertura de testes

## Funcionalidades

- Exibir o progresso de cursos concluídos por aluno.
- Calcular a média geral dos cursos concluídos.
- Informar a quantidade de cursos restantes para atingir o bônus de 12 cursos.
- Mensagens personalizadas baseadas no progresso do aluno.

## Como Executar

1. **Pré-requisitos**:
   - Java 17 ou superior instalado.
   - Maven instalado.

2. **Executar a Aplicação**:
Se o Maven Wrapper (`mvnw`) estiver presente no projeto:
```
./mvnw spring-boot:run
```

3. **Acessar a API**:
- Acesse http://localhost:8080/progresso/{idAluno} para visualizar o progresso de um aluno, onde `{idAluno}` deve ser um identificador numérico correspondente ao aluno.

## Estrutura do Projeto

- controller/: Contém os controladores REST.
- service/: Contém a lógica de negócios.
- repository/: Contém os repositórios JPA.
- entity/: Contém as entidades do banco de dados.
- dto/: Contém os objetos de transferência de dados.
- test/: Contém os testes unitários e de integração.

## User Story
Como um aluno, eu quero visualizar meu progresso de cursos concluídos e a média de cada um, para saber quantos preciso fazer para ganhar o bônus de 12 cursos.

## BDD

**BDD 1: Diego Justino da Silva**
Dado que um aluno está logado na plataforma e ele concluiu 5 cursos e a média dos cursos é maior que 7,
quando ele acessa a tela "meu progresso"
então ele deve ver a média de cada um e o sistema deve indicar que faltam 7 cursos para o bônus.

**BDD 2: Lucas de Moraes Silveira**
Dado que um aluno está logado na plataforma e ele concluiu 3 cursos e a média de um desses cursos é maior que 7,
quando ele acessa a tela "meu progresso"
então ele deve ver apenas 2 cursos como concluídos e o sistema deve indicar que faltam 10 cursos para o bônus.

**BDD 3: Rafael Ferro Machado**
Dado que um aluno está logado na plataforma e ele não concluiu nenhum curso,
quando ele acessa a tela "meu progresso"
então ele deve ver a mensagem informando que nenhum curso foi concluído e o sistema deve indicar que faltam 12 cursos para o bônus.

## Testes
Os testes estão localizados no diretório `src/test/java`. Eles cobrem:

- Service Layer: Testes unitários para a lógica de cálculo de progresso.
- Controller Layer: Testes de integração para os endpoints REST.

## Cobertura de Código
A cobertura de código é gerada automaticamente pelo Jacoco. Após executar o comando mvnw test, o relatório estará disponível em target/site/jacoco/index.html. 
Abra este arquivo em um navegador web para visualizar o relatório de cobertura.
