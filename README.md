# Prova de Validação de conhecimento (Estágio em desenvolvimento) - Topaz

Olá! Está é a resolução da prova de validação de conhecimento técnico da Topaz.  

## Proposta
Foi proposta a criação de uma aplicação web com backend e frontend. O backend deveria se comunicar com o banco de dados e enviar os dados através de uma api rest.  
Eu desenvolvi a API utilizando o Java com Spring boot, o banco de dados escolhido foi o PostgreeSQL e o frontend eu desenvolvi com HTML, CSS e JavaScript.

## Resolução da proposta
Após fazer o backend e ver a lista de tarefas pronta eu pensei que um jeito criativo de consumir a mesma seria em um quadro Kanban, por ser algo que pode ser util no dia a dia em diversas situações.

## Instalação

### Instalação via docker
* Para executar a instalação desta maneira você deve ter o docker e o git previamente instalados em seu computador, após isso é bem simples:
 1. Fazer o clone do repositório para a sua máquina local:
 ~~~git
 git clone https://github.com/victot-exe/todo_list_topaz.git
 ~~~
 _Favor não executar no codespaces do github_

 2. Antes de executar, certifique-se de que as portas: 3000, 8080 e 5432 estejam livres para que o docker consiga subir as novas portas rodando, caso não estejam você pode alterar as mesmas no `.\docker-compose-yml` alterando o primeiro conjunto de números antes da barra para a porta desejada. (Caso faça isso por favor altere a const URI para a nova porta de saída da api no arquivo de script.js -> `.\todo_list_topaz_front\script.js`)

 3. Abra a pasta raiz do projeto no terminal e execute o comando:
 ~~~cli
 docker-compose up --build
 ~~~

 4. Acesse `http://localhost:3000` -> ou para a porta que você alterou para ver a aplicação funcionando.

### Executar via IDE
* Para executar na IDE você precisa ter o Java pelo menos na versão 21 e ter uma instância do banco de dados postgrees instalado.
1. Faça o clone do repositório na sua máquina local:
 ~~~git
 git clone https://github.com/victot-exe/todo_list_topaz.git
 ~~~

 2. Abra a pasta `./todo_list_topaz` na IDE de sua preferência _eu indico o inteliJ pois ele já vai baixar as dependências do projeto_

 3. Certifique-se de que o Postgree esteja rodando

 4. Certifique-se de que o Postgrees tenha um banco de dados chamado `tarefas`

 5. No arquivo `.\todo_list_topaz\src\main\resources\application.properties` coloque os valores correspondentes as variáveis:
 ~~~properties
spring.datasource.url=jdbc:postgresql://db:5432/tarefas <- aqui você coloca jdbc:postgresql://endereço e porta do seu banco/tarefas
spring.datasource.username=root <- aqui o user do seu banco
spring.datasource.password=1234 <- aqui a senha do seu banco
~~~

 6. Execute o método main do arquivo `.\todo_list_topaz\src\main\java\com\victot_exe\todo_list_topaz\TodoListTopazApplication.java`

 * O backend executa por padrão na porta 8080 então certifique-se de que a porta está livre

 7. Após a API estar rodando você pode abrir a pasta `.\todo_list_topaz_front` no VS code e executar o arquivo `.\todo_list_topaz_front\index.html` para ver a aplicação web funcionando.

## End-points
_Além de ter documentado aqui os end-points eu deixei disponível o swagger da aplicação `http://localhost:8080/swagger-ui/index.html`_
1. GET(`localhost:8080/tarefas`) -> Este endpoint retorna uma lista com todas as tarefas já registradas na aplicação, caso a lista esteja vazia ele retorna o código 404 dizendo que a lista está vazia e não encontrou nada.
* Por padrão eu configurei migrations para que quando seja executada o banco de dados já seja populado com algumas tarefas.

2. GET(`localhost:8080/tarefas/{id}`) -> Este endpoint retorna uma única tarefa pelo seu id, que é um Long, caso ele não encontre o id responsável ele retorna o código 404 dizendo que o id não foi encontrado.

3. PUT(`localhost:8080/tarefas`) -> Para esta requisição precisamos mandar um body em json no formato:
~~~json
{
  "titulo": "Titulo da tarefa",
  "descricao": "Descrição da tarefa",
  "status": "NAO_INICIADO"
}
~~~
* Eu coloquei validações para que caso algum campo esteja em branco ou no caso do status que seja um status que não está na lista que usei ele avise o usuário como colocar de forma correta (No frontend eu deixei isso implícito nos forms, para que não dê para submeter o mesmo sem ter preenchido tudo e colocado dados válidos.)

4. PUT (`localhost:8080/tarefas/{id}`) -> Esta requisição pede uma id referente a tarefa que queremos alterar, e um body com a nova tarefa, o body também em json no formato:
~~~json
{
  "titulo": "Titulo alterado",
  "descricao": "Descrição alterada",
  "status": "EM_ANDAMENTO"
}
~~~

5. DELETE (`localhost:8080/tarefas/{id}`) -> Esta é uma requisição para remover tarefas onde passamos o id desejado e deletamos o mesmo do banco de dados.

## Diagrama do sistema

~~~mermaid
---
config:
  theme: redux
  layout: dagre
---
flowchart TD
 subgraph s1["BackEnd"]
  end
    n1["Client (browser)"] --> n2["FrontEnd"]
    n2 --> s1 --> n5["DataBase<br>Postgree"]
    n1@{ shape: dbl-circ}
    n2@{ shape: rounded}
    n5@{ shape: cyl}

~~~

## Diagrama de classe
~~~mermaid
classDiagram
    class Tarefa{
      id: Long
      titulo: String
      descricao: String
      status: Status
    }

    class Status{
        NAO_INICIADO,
        EM ANDAMENTO,
        PAUSADA,
        CONCLUIDA
    }

    Tarefa --> Status
~~~

## Funcionamento do backend
~~~mermaid
flowchart TD
    A(Controller) -->|Recebe Requisições| B(Service)
    B -->|Trata a requisicao| C(Repository)
    C -->|Faz a persitência no banco, enviando os dados ou recebendo o que foi solicitado| D(Banco e dados)
~~~
 ## DTO
 Utilizei dois tipos de DTOS diferentes para não correr o risco de enviarem algum id por engano, isso acaba gerando um erro na hora de fazer a requisição
 * TarefaDTORequest -> Tem apenas os campos editáveis da tarefa {titulo, descrição e status}
 * TarefaDTOResponse -> Tem todos os campos da tarefa, pois utilizamos o id para fazer requisições de getById(id), ou deleteById(id), ou requisição de updateTarefa(id, tarefaDTORequest)

 ## Considerações finais
Acredito que aprendi bastante ao realizar esta prova de validação. Planejar previamente o backend e pensar em como ele seria consumido pelo frontend me ajudou a economizar tempo, pois já comecei a codar com uma visão clara do que seria necessário.  

O maior desafio, para mim, foi no frontend, já que tenho mais familiaridade e experiência com o backend. Ainda assim, foi uma oportunidade valiosa para praticar e sair da zona de conforto.  

Procurei seguir boas práticas de desenvolvimento, com foco em manter o código limpo e organizado — algo que acredito ter conseguido aplicar especialmente bem no backend.  

No fim, consegui montar o quadro Kanban e fiquei satisfeito com o resultado. Agradeço pela oportunidade e pelo desafio, e espero ter a chance de conversar com vocês em breve!