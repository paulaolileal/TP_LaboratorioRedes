## PUC Minas 2017
- Autor: Paulo Victor de Oliveira Leal

## Objetivo
- Implementação do jogo de Truco com o uso de:
    - Multithread 
    - Sockets

## Observação
- Projeto desenvolvido no IntelliJ
- São necessarios quatro clientes conectarem ao servidor para jogar

## Compilação no terminal 
- Copie os arquivos, para algum lugar de sua preferência:
  - ServidorTCP.java
  - ClienteTCP.java
  - gson-2.8.0.jar (que está na pasta lib)  
- Vá até o diretorio dos arquivos que você copiou através do terminal
- Digite: javac -g -cp gson-2.8.0.jar *.java

## Execução no terminal
- Vá até o diretorio dos arquivos que você copiou através do terminal
- Em uma janela do terminal, execute:
- Para servidor: java -cp gson-2.8.0.jar;. ServidorTCP
- Para cliente: java -cp gson-2.8.0.jar;. ClienteTCP

