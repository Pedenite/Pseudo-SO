package pso.module

class Escalonador {
    Fila processosProntos
    Fila tempoReal
    Fila processosUsuario
    Fila prioridade1
    Fila prioridade2
    Fila prioridade3
    Processo posseCPU
    int vez

    Escalonador(){
        processosProntos = new Fila()
        tempoReal = new Fila()
        processosUsuario = new Fila()
        prioridade1 = new Fila()
        prioridade2 = new Fila()
        prioridade3 = new Fila()
        vez = 1
    }

    void prepara(processos){
        for(processo in processos){
            processosProntos << processo
        }
    }

    def verificaDisponibilidade(memoria, recurso){
        def processosExecutando = []
        for(int i = 0; i < processosProntos.size(); i++){
            def processo = processosProntos.pop()
            processo.offset = memoria.verificarOffsetDisponivel(processo.blocks, processo.prioridade == 0)
            if(processo.offset < 0){
                processosProntos << processo
                continue
            }
            
            memoria.alocarProcesso(processo.offset, processo.blocks, processo.prioridade == 0)
            def nomesRecursos = ["impressora", "scanner", "sata", "modem"]
            boolean recursoAlocado = true

            for (int j = 0; j < nomesRecursos.size(); ++j) {
                Integer alocarRecurso = processo[nomesRecursos[j]] > 0 ? recurso.alocarRecurso(nomesRecursos[j], processo.pid) : null

                if (alocarRecurso > 0) {
                    processo[nomesRecursos[j]] = alocarRecurso + 1
                    recursoAlocado = true
                }

                if (alocarRecurso == -1) {
                    recursoAlocado = false
                    break
                }
            }

            if(!recursoAlocado){
                memoria.desalocarProcesso(processo.offset, processo.blocks, processo.prioridade == 0)
                recurso.desalocarTudo(processo.pid)
                processosProntos << processo
                continue
            }
            
            boolean sucesso
            if(processo.prioridade == 0){
                sucesso = tempoReal << processo
            } else {
                sucesso = processosUsuario << processo
            }

            if(sucesso){
                processosExecutando << processo.pid
            } else {
                memoria.desalocarProcesso(processo.offset, processo.blocks, processo.prioridade == 0)
                recurso.desalocarTudo(processo.pid)
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

    boolean filasVazias(){
        return processosProntos.isEmpty() && tempoReal.isEmpty() && processosUsuario.isEmpty() && prioridade1.isEmpty() && prioridade2.isEmpty() && prioridade3.isEmpty()
    }

    Processo executaProcessos(){
        Processo finalizado = null
        if(posseCPU){ // para os processos de tempo real
            boolean terminou = posseCPU.execute()
            if(terminou){
                finalizado = posseCPU
                posseCPU = null
            }
            return finalizado
        }

        if(!tempoReal.isEmpty()){
            posseCPU = tempoReal.pop()
        } else {
            switch(vez){
                case 1:
                    this.vez++
                    if(!prioridade1.isEmpty()){
                        posseCPU = prioridade1.pop()
                        boolean terminou = posseCPU.execute()
                        if(!terminou){
                            prioridade2 << posseCPU
                        } else {
                            finalizado = posseCPU
                        }

                        posseCPU = null
                        break
                    }
                case 2:
                    this.vez++
                    if(!prioridade2.isEmpty()){
                        posseCPU = prioridade2.pop()
                        boolean terminou = posseCPU.execute()
                        if(!terminou){
                            prioridade3 << posseCPU
                        } else {
                            finalizado = posseCPU
                        }
                        
                        posseCPU = null
                        break
                    }
                default:
                    this.vez = 1
                    if(!prioridade3.isEmpty()){
                        posseCPU = prioridade3.pop()
                        boolean terminou = posseCPU.execute()
                        if(!terminou){
                            prioridade3 << posseCPU
                        } else {
                            finalizado = posseCPU
                        }
                        
                        posseCPU = null
                    }
            }
        }
        return finalizado
    }

    @Override
    String toString(){
        return "===== FILAS =====\nTempo real:\t${tempoReal}\nProntos:\t${processosProntos}\nUsuario:\t${processosUsuario}\nPrioridade 1:\t${prioridade1}\nPrioridade 2:\t${prioridade2}\nPrioridade 3:\t${prioridade3}"//\n\nPosse da CPU:\t[${posseCPU ? posseCPU : " "}]"
    }
}