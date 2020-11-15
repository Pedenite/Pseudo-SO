package pso.module

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

    boolean escalona(memoria){
        if(processosProntos.isEmpty())
            return
        def processo = processosProntos.pop()
        processo.offset = memoria.verificarOffsetDisponivel(processo.blocks, processo.tempoReal)
        if(processo.offset < 0){
            // pode causar starvation! Verificar depois
            processosProntos.push(processo)
            return false
        }

        if(processo.tempoReal){
            memoria.alocarProcesso(processo.offset, processo.blocks, processo.tempoReal)
            tempoReal << processo
        } else {
            memoria.alocarProcesso(processo.offset, processo.blocks, processo.tempoReal)
            processosUsuario << processo
        }
        return true
    }

    @Override
    String toString(){
        return this.tempoReal.toString()
    }
}