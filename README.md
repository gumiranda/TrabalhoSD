# Trabalhos da disciplina de Sistemas Distribuídos

Para submeter, crie um fork deste repositorio. Trabalhe no seu fork e um branch com o nome SUBMISSAO_X, onde X é o número da entrega a ser feita. 

Quando estiver pronto para submeter a versao final, faca um pull request para este fork aqui.

Para cada submissão, altere o arquivo README.md ***NO SEU BRANCH*** para conter o nome dos componentes do grupo e instruções de como executar o projeto e testes.

#Para executar

mvn exec:java -Dexec.mainClass="grpc.Servidor" -Dexec.args="0 127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"
mvn exec:java -Dexec.mainClass="grpc.Servidor" -Dexec.args="1 127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"
mvn exec:java -Dexec.mainClass="grpc.Servidor" -Dexec.args="2 127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"


mvn exec:java -Dexec.mainClass="grpc.Cliente" -Dexec.args="127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"
mvn exec:java -Dexec.mainClass="grpc.Cliente" -Dexec.args="127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"
mvn exec:java -Dexec.mainClass="grpc.Cliente" -Dexec.args="127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"
mvn exec:java -Dexec.mainClass="grpc.Cliente" -Dexec.args="127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045"


Teste:
mvn exec:java -Dexec.mainClass="grpc.Cliente" -Dexec.args="127.0.0.1 59043 127.0.0.1 59044 127.0.0.1 59045 testando"




Gustavo Miranda
Lara Carolina
Nathan Rodovalho
Alexandre Pereira