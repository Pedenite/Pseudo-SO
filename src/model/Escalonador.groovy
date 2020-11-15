package model

class Escalonador {
    Fila processosProntos
    Fila tempoReal
    Fila processosUsuario
    Fila prioridade1
    Fila prioridade2
    Fila prioridade3

    Escalonador(){
        processosProntos = new Fila()
        tempoReal = new Fila()
        processosUsuario = new Fila()
        prioridade1 = new Fila()
        prioridade2 = new Fila()
        prioridade3 = new Fila()
    }

    void prepara(processo){
        processosProntos << processo
    }

    void escalona(){
        def processo = processosProntos.pop()
            if(processo.tempoReal){
                tempoReal << processo
            } else {
                processosUsuario << processo
            }
        
    }
}