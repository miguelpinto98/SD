SD
==

µKickStarter

Pelo que estive a ler, o que nós temos que fazer é criar dois programas: um cliente e um servidor. A comunicação entre eles é feita por sockets, neste caso por TCP.

A meu ver, temos de ter as seguintes classes:
- Utilizador.java 
- Projecto.java
- Servidor.java, onde estará o nosso sistema, onde temos uma thread a correr que recebe pedidos e cria uma nova thread para cada um.
- Cliente.java, esta classe é aquela que recebe os dados(por exemplo de uma autenticacao) e vai pedir à classe Servidor se existe algum utilizador com esses dados. É a classe que vai fazer os pedidos ao Servidor.

Vejam este esquema: http://i.imgur.com/t09Mx46.jpg

=================

Temos de pensar nisto como o sistema das caixas-multibanco. Em que as caixas-mb são os clientes, insere-se um pin e o sistema verifica se está correcto. E fazemos operações (levantar,depositar,etc), tudo isto são pedidos ao sistema, feitos pelas caixas-mb que são clientes.
