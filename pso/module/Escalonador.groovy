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

    void prepara(processos){
        for(processo in processos){
            processosProntos << processo
        }
    }

    def verificaDisponibilidade(memoria){
        def processosExecutando = []
        for(int i = 0; i < processosProntos.size(); i++){
            def processo = processosProntos.pop()
            processo.offset = memoria.verificarOffsetDisponivel(processo.blocks, processo.prioridade == 0)
            if(processo.offset < 0){
                processosProntos << processo
                continue
            }
            
            memoria.alocarProcesso(processo.offset, processo.blocks, processo.prioridade == 0)
            boolean sucesso
            if(processo.prioridade == 0){
                sucesso = tempoReal << processo
            } else {
                sucesso = processosUsuario << processo
                processosExecutando << processo.pid
            }

            if(!sucesso){
                memoria.desalocarProcesso(processo.offset, processo.blocks, processo.prioridade == 0)
                processo.offset = -1
            }
        }
        return processosExecutando
    }

    boolean classificaProcesso(){
        if(this.processosUsuario.isEmpty()){
            return false
        }
        def processo = processosUsuario.pop()
        boolean sucesso
        switch(processo.prioridade) {
            case 1:
                sucesso = prioridade1 << processo
                break
            case 2:
                sucesso = prioridade2 << processo
                break
            default:
                sucesso = prioridade3 << processo
        }

        if(!sucesso){
            if(processo.prioridade > 0){
                processo.prioridade--
            } else {
                return false
            }
            this.processosUsuario << processo
        }

        return true
    }

    boolean FilasVazias(){
        
    }

    void executaProcessos(){
        
    }

    @Override
    String toString(){
        return "tempo real:${tempoReal}\nprontos:${processosProntos}\nusuario:${processosUsuario}\nprioridade1:${prioridade1}\nprioridade2:${prioridade2}\nprioridade3:${prioridade3}"
    }
}