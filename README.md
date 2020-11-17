# PSEUDO-SO
Trabalho final de Introdução a Sistemas Operacionais UnB 2020/1

Para rodar (raiz do repositório): `groovy pso/dispatcher.groovy <arquivo_processos.txt> <arquivo_arqs.txt>`

ou

`groovy pso/dispatcher.groovy sample/*.txt`

Precisa do [Apache Groovy](https://groovy.apache.org/download.html) instalado! Projeto desenvolvido na versão 3.0.4 ou superior.

## Módulos
O programa deve possuir 5 módulos principais:

### Módulo de Processos
classes e estruturas de dados relativas ao processo. Basicamente, mantém informações específicas do processo.

### Módulo de Filas
mantém as interfaces e funções que operam sobre os processos.

### Módulo de Memória
provê uma interface de abstração de memória RAM.

### Módulo de Recurso
trata a alocação e liberação dos recursos de E/S para os processos.

### Módulo de Arquivos
trata as operações create e delete sobre os arquivos. O nome dos arquivos devem conter apenas 1 letra (ctrl+f -> letra no documento de especificação).

## Input
Programa deve receber 2 arquivos em sua chamada.

### Informações dos processos:
Na ordem mostrada a seguir:

`{tempo de inicialização}, {prioridade}, {tempo de processador}, {blocos em memória}, {númerocódigo da impressora requisitada}, {requisição do scanner}, {requisição do modem}, {númerocódigo do disco}`

# Artigo IOS
link overleaf: https://pt.overleaf.com/project/5fac7d4c430288ba9a513bea
