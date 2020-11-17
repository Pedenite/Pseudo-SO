package pso.module

class Arquivo {
    char nome
    int processoCriador

    Arquivo(nome, processoCriador){
        this.nome = nome
        this.processoCriador = processoCriador
    }

    @Override
    String toString(){
        return this.nome
    }
}