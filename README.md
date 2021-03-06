# afc2md
Conversor de `.afc` (Tubaina) para `.md` (Markdown) no _sabor_ do [Gitbook](https://github.com/GitbookIO/gitbook), com algumas pitadas do plugin [gitbook-plugin-cdc](https://github.com/casadocodigo/gitbook-plugin-cdc). Pronto para ser usado com o [tubaina2](https://github.com/caelum/tubaina2).

## Como executar

### Gerando o jar
Você vai precisar dos seguintes pré-requisitos:
* Java JDK 1.7+
* Maven

Baixe o projeto e execute `mvn package`. Será gerado o `afc2md-1.0.0.jar` na pasta `target`.

### Executando
Execute o comando:
```
java -jar afc2md-1.0.0.jar -d /diretorio/onde/estao/os/afcs
```

A opção `-d` deve ser utilizada para informar o diretório onde estão os arquivos `.afc` que serão convertidos em `.md`. Caso essa opção seja omitida, será considerado o diretório corrente.

E pronto! Os arquivos `.md` devem ter sido gerados! 

Também devem ter sido gerados como arquivos auxiliares como `package.json`, `book.properties`, `cover.jpg`, `.bookignore` e `.gitignore`. Todos são arquivos importantes para o [tubaina2](https://github.com/caelum/tubaina2).

### Usando o tubaina2

Para informaçoes sobre como usar o tubaina2 para gerar um site ou ebooks nos formatos `.pdf`, `.mobi` ou `.epub`, veja: 
https://github.com/caelum/tubaina2

## Conversões realizadas

São feitas as seguintes conversões:

* Capítulos como `[chapter Eclipse IDE]` viram `# Eclipse IDE`

* Seções como `[section Como aprender Java?]` viram `## Como aprender Java?`

* Títulos como `[title Aprendendo a aprender]` viram `### Aprendendo a aprender`

* Texto em itálico como `::primary key::` viram `_primary key_`

* Códigos inline como `%%alert("Oi")%%` viram \``alert("Oi")`\`

* Imagens como `[img imagens/logo.png w=80% "Logo da empresa"]` viram `![Logo da empresa {w=90%}](imagens/logo.png)`

* Tarefas como `[TODO melhorar texto]` viram `<!--@todo melhorar texto -->`

* Notas para instrutores como 
  ```
    [note]
        Mostre também a outra solução.
    [/note]
   ``` 
   viram  
   ```
   <!--@note 
   Mostre também a outra solução.
    -->
   ```

* Citações como `[quote "Olho por olho, eo mundo acabará cego." -- Ghandi]` viram `_"Olho por olho, eo mundo acabará cego." -- Ghandi_`

* Uma lista do tipo
  ```
    [list]
    * responder o que é Java;
    * mostrar as vantagens e desvantagens do Java;\n"+
    * entender bem o conceito de máquina virtual;\n"+
    * compilar e executar um programa simples.\n"+
    [/list]
  ```
  vira
  ```
    * responder o que é Java;
    * mostrar as vantagens e desvantagens do Java;\n"+
    * entender bem o conceito de máquina virtual;\n"+
    * compilar e executar um programa simples.\n"+
  ```
Listas como `[list number]`, `[list letter]` e `[list #]` viram a mesma coisa, sem suporte a números ou letras.

* Exercícios, que começam com `[exercise]` são simplesmente removidos

* Questões de exercícios como 
   ```
    [question]
        Em WebContent, através do menu File -> New -> Other -> HTML crie um arquivo chamado index.xhtml.
    [/question]
    [question]
        Implemente nosso primeiro código JSF com um campo de texto e um botão.
    [/question]
    ```
    viram listas numeradas como
   ```
    1. Em WebContent, através do menu File -> New -> Other -> HTML crie um arquivo chamado index.xhtml.
    1. Implemente nosso primeiro código JSF com um campo de texto e um botão.
    ```
Linhas consecutivas mantém apenas um nível de _TAB_, exceto em códigos. Não deve existir mais de um _ENTER_ entre linhas consecutivas.

* Respostas de exercício como 
    ```
    [answer]
        42
    [/answer]
    ``` 
    viram 
    ```
    <!--@answer 
        42
    -->
    ```

* Caixas de texto como
    ```
    [box Git e Github são a mesma coisa?]
        Não.
    [/box]
    ```
    viram
    ```
    > **Git e Github são a mesma coisa?**
    > Não.
    ```
    
Se houver um rótulo para o box, como no exemplo anterior, a primeira linha contém o rótulo em negrito. Linhas consecutivas mantém apenas um nível de _TAB_, exceto em códigos. Não pode existir mais de um _ENTER_ entre as linhas do box. Dois box consecutivos devem ser separados com um comentário HTML, para não serem unificados.

* Código como 
    ```
    [code java]
        public class Main {
            public static void main(String... args){
            }
        }
    [/code]
    ```
    viram
    
    ``` 
    ``` java
        public class Main {
            public static void main(String... args){
            }
        }
    ``` _
    ```
Os _TAB_ dentro do bloco de código sempre são mantido. Linhas destacadas com `[code h=3]` não são suportadas.

* Parágrafos normais: não podem ter mais de um nível de _TAB_.


Não há suporte a:

* `[index]`
* `[ref-label]`
* `[label]` nem `[img label-logo]`
