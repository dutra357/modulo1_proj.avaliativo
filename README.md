
# Syllabus - Gestão de Ambiente Educacional

## Sobre o projeto

O sistema Syllabus consiste em uma aplicação web direcionada à gestão de um ambiente educacional, ela engloba o cadastramento de Professores, Alunos, Cursos, Turmas, Matérias e Notas, bem como realiza a gestão e controle desses relacionamentos.

O Software foi desenvolvido pensando na integração das informações com um sistema de dados sólido, escalável, que permita a alocação e acesso célere aos recursos educacionais necessários à atividade de ensino.

Com a implementação de recursos de tecnologia da informação certamente poderemos imprimir agilidade e eficiência de gestão no trato administrativo de sistemas e estruturas de ensino, seja ele básico ou superior.

Não menos importante, ainda podemos citar a facilidade de acesso aos dados por parte de qualquer usuário por meio da rede mundial de computadores, disponibilizando as informações em qualquer tempo e local do globo, desde que provido de acesso a internet.





## Tecnologias utilizadas
### Back end
- Java
- Spring Boot
- Spring Security - JWT Token
- JPA / Hibernate
- Maven
- PostegreSQL (conexão direta na máquina)


## Artefato
modulo1_proj.avaliativo

## Clonar repositório
https://github.com/dutra357/modulo1_proj.avaliativo

## Trello
https://trello.com/b/nxf5tBfg/modulo-1-projeto-avaliativo


## Execução e cuidados
A execução da aplicação reclama alguns cuidados, notadamente com o cadastro inicial de administrador (ADMIN).

Ainda que possa ser feita uma rotina de cadastro automático de "root", preferimos que a aplicação seja primeiramente iniciada modificand-se as configurações de segurança (SecConfig), setando-se o POST de cadastro para ".permitAll()", e o campo final de ".anyRequest()" para ".permitAll()".

Feito isso, o usuário pderá cadastrar o primeiro ADMIN no banco de dados, retornando às configurações iniciais e reiniciando o sistema. 

Qualquer que seja o caso, é primordial efetivar o cadastramento de novo ADMIN com senha diversa da padrão (login: root / senha:root).

Acrescentamos ainda a importância da ordem de cadastros junto ao sistema, em face de suas inequívocas dependências que visam a garantia da integridade dos dados.

Assim, a ordem seguida pelas requisições do Postman é sugerida (arquivo presente no projeto "/postman"), dado que, a exemplo do cadastro de Notas, não poderão elas ser cadastradas sem o anterior cadastro de Curso, Turma e Aluno.

A atribuição de Professor a um Curso ou Turma, entretanto, foi propositalemente deixada em aberto, dado que tais elementos podem ser criados posteriormente, com a inclusão do Docente.

Nesta mesma linha, permite que seja alterado o Professor, ou outro seja atribuído.



## Considerações sobre a Segurança (importante!)
O sistema de segurança faz uso do Baerer Token (Bearer Authentication), enviando um token ao usuario cadastrado que solicite login e armazenando as suas credenciais e "role" de acesso ao sistema.

Uma fez feito um cadastro de usuário, o seu login é armazenado no banco de dados (String), bem como sua senha em criptografada (hash).

Ao enviar suas credenciais para o serviço de login (POST) o sistema criptografa a senha recebida e a compara com a hash presente no banco de dados. Sendo positiva a comparação, retorna o token de autenticação ao usuário (autenticação stateless).

Desta forma, o usuário ao se valer do Postman deverá incialmente realizar seu login com o usuário pretendido, recebendo o token de autenticação.

Na sequência, poderá realizar as operações em cada endpoint fazendo uso da chave/tolken recebida junto a guia "Auth".


## Melhorias a serem implementadas
Como sugestões ao aprimoramento do projeto podemos citar o controle em cascata para a deleção ou atualização de dados, bem como a realocação da entidade Turmas para dentro da entidade Cursos, de modo que cada Curso tenha vários Turmas.

Também vale acrescentar a utilidade de se adicionar a funcionalidade de cálculo de média por matéria, item não previsto no projeto.

## MER - Relacionamento entre entidades

![MER](/MER/MER_projeto_avaliativo.png)

## Endpoints disponibilizados
A cada endpoin apresentado abaixo colacionamos, em auxílio, um modelo de cada tipo-padrão de requisição no seu formato JSON.


### POST /login
Direcionado ao acesso e login no sistema por parte de qualquer usuário cadastrado.

{
"login": "root",
"senha": "root"
}

### POST /cadastro
Acesso ao cadastramento de novos usuários.
Aqui será necessário indicar o Papel/Role do usuário, podendo ser ADMIN, PEDAGOGICO, PROFESSOR, RECRUITER ou ALUNO.

{
"login": "root",
"senha": "root",
"papel": "ADMIN"
}

### POST /docentes
Direcionado ao cadastramento de novos docentes, que deverão ser usuários pré-cadastrados (a serem indicados pelo respectivo id).

{
"nome": "Professor GABRIEL
"usuario_id": 1
}

### GET /docentes/id
Recupera os dados de um docente pelo ID indicado.

### PUT /docentes/id
Altera os dados cadastrais do docente indicado pelo respectivo ID.

{
"nome": "Professor ANDRE(UPDATE)",
"usuario_id": 1
}

### DELETE /docentes/id
Dedicado à exclusão do docente indicado pelo ID.

### GET /docentes
Lista todos os docentes cadastrados no sistema, listando por ID.


### POST /turmas
Endpoint destinado a fazer o cadastro de novas Turmas.

{
"nome": "Turma FMT - SEGUNDA Edição",
"curso_id": 1,
"docente_id": 1
}

### GET /turmas/id
Destinado a recuperar os dados de uma turma, indicada pelo seu ID de cadastro.

### PUT /turmas/id
Ponto destinado a alterar as informações cadastrais de uma determinada turma, indicada pelo ID.

{
"nome": "Turma FMT 2024 (UPDATE1)",
"curso_id": 1,
"docente_id": 1
}

### DELETE /turmas/id
Destinado a excluir determinada turma (indicada por ID).

### GET /turmas
Serve a listar todas as turmas cadastradas no sistema.

### POST /alunos
Serve para cadastrar um novo Aluno, que, a semelhança de Docente, deve ser previamente cadastrado como Usuário (o qual será indicado pelo ID).

{
"nome": "Aluno Fulano de Tal",
"usuario_id": 1,
"dataNascimento": "1981-20-08",
"turma_id": 1
}

### GET /alunos/id
Recupera os dados cadastrais de um Aluno indicado pelo ID.

### PUT /alunos/id
Edita as informações cadastrais de um Aluno indicado pelo ID.

{
"nome": "Aluno Sicrano de Tal (UPDATE)",
"usuario_id": 1,
"dataNascimento": "1981-20-08",
"turma_id": 1
}

### DELETE /alunos/id
Exclui um determinado aluno do banco de dados, indicado pelo ID.

### GET /alunos
Retorna a listagem de todos os alunos cadastrados no sistema.

### POST /cursos
Destinado ao cadastramento de novos Cursos.

{
"nome": "Curso de SPRING BOOT"
}

### GET /cursos/id
Retorna os dados do Curso indicado pelo seu ID.

### PUT /cursos/id
Edita os dados cadastrais do Curso indicado pelo seu ID.

{
"nome": "Curso de JavaScript (UPDATE)"
}

### DELETE /cursos/id
Exclui da base de dados o Curso indicado pelo seu ID de cadastro.

### GET /cursos
Retorna a listagem de todos os cursos cadastrados no sistema.

### GET /cursos/{id_curso}/materias
Retorna todas as matérias cadastradas em um determinado curso, este indicado por ID.

### POST /materias
Cadastra uma nova matéria em um curso, este a ser indicado na requisição (body).

{
"nome": "Spring Security",
"curso_id": 1
}

### GET /materias/id
Retorna os dados de uma determinada matéria cadastrada.

### PUT /materias/id
Altera os dados cadastrais de uma determinada matéria.

{
"nome": "Lógica de Programação com JAVA (UPDATE)",
"curso_id": 1
}

### DELETE /materias/id
Exclui da base de dados a matéria indicada pelo seu ID.

### GET /alunos/{id_aluno}/notas
Retorna todas as notas de um determinado aluno, indicado pelo seu ID.
Caso seja chamado por aluno, retornará apenas as suas próprias notas.

### POST /notas
Cadastra nova nota no sistema. Salientamos que notas só podem ser cadastradas em usuários que possuam a role "ALUNO".

{
"valor": "9.32",
"aluno_id": "1",
"docente_id": "1",
"materia_id":"1"
}

### GET /notas/id
Retorna o valor de uma determinada nota, identificada pelo seu ID de cadastro.

### PUT /notas/id
Edita o valor de uma determinada nota, indicada pelo seu ID.

{
"valor": "7.19",
"aluno_id": 1,
"docente_id": 1,
"materia_id": 1
}

### DELETE /notas/id
Exclui da base de dados uma determinada nota, indicada pelo seu ID.

### GET /alunos/{id}/pontuacao
Retorna a pontuação total de um aluno, indicado pelo ID, conforme a métrica solicitada pelo projeto.





## Autor

David Alves Dutra

contato: dadutra@hotmail.com


https://github.com/dutra357


