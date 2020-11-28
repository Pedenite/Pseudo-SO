package pso.controller

import pso.module.*

class Manager {
    SistemaArquivos fs
    Escalonador escalonador
    Memoria memoria
    Processo dispatcher
    Recurso recurso

    Manager(Processo dispatcher){
        memoria = new Memoria()
        escalonador = new Escalonador()
        recurso = new Recurso()
        this.dispatcher = dispatcher
    }

    Processo prepareProcess(listaAtributos){
        if(listaAtributos?.size() < 8){
            throw new IllegalArgumentException("${listaAtributos}")
        }
        
        listaAtributos = listaAtributos*.toInteger()
        if(listaAtributos[3] > 960 || listaAtributos[3] > 64 && listaAtributos[1] == 0){
            throw new IllegalArgumentException("O sistema não possui blocos de memória suficeientes para o processo ${listaAtributos}")
        }

        return new Processo(listaAtributos[0], listaAtributos[1], listaAtributos[2], listaAtributos[3], listaAtributos[4], listaAtributos[5], listaAtributos[6], listaAtributos[7])
    }

    int prepareFS(fileSystemProperties){
        fs = new SistemaArquivos(fileSystemProperties[0].toInteger())
        def n = fileSystemProperties[1].toInteger()
        for(i in (0..n-1)){
            def file = fileSystemProperties[i+2].split(",")
            if(file.size() != 3){
                throw new IllegalArgumentException("Arquivo inválido: ${file}")
            }
            def name = file[0]
            def bloco = file[1].toInteger()
            def qtdBlocos = file[2].toInteger()
            fs.init(name, bloco, qtdBlocos)
        }
        return n+2
    }

    void dispatch(processos){
        escalonador.prepara(processos)
    }

    def verificaProcessosProntos(){
        return escalonador.verificaDisponibilidade(this.memoria, this.recurso)
    }

    void organizaProcessos(){
        boolean continuar = true
        while(continuar){
            continuar = escalonador.classificaProcesso()
        }
    }

    boolean atribuiQuantum(){
        def processoFinalizado = escalonador.executaProcessos()
        if(processoFinalizado){
            memoria.desalocarProcesso(processoFinalizado.offset, processoFinalizado.blocks, processoFinalizado.prioridade == 0)
            recurso.desalocarTudo(processoFinalizado.pid)
            
            processoFinalizado.impressora > 0 ? recurso.desalocarRecurso("impressora", (processoFinalizado.impressora - 1)) : 0;
            processoFinalizado.scanner > 0 ? recurso.desalocarRecurso("scanner", (processoFinalizado.scanner - 1)) : 0;
            processoFinalizado.sata > 0 ? recurso.desalocarRecurso("sata", (processoFinalizado.sata - 1)) : 0;
            processoFinalizado.modem > 0 ? recurso.desalocarRecurso("modem", (processoFinalizado.modem - 1)) : 0;
        }
        return escalonador.filasVazias()
    }

    void chamaSistemaArquivos(processo, opcode, nome, numeroBlocos){
        switch(opcode){
            case 0:
            case "0": 
                fs.create(nome, processo.pid, numeroBlocos)
                break
            case 1:
            case "1": 
                fs.delete(nome, processo.pid, processo.prioridade)
        }
    }
}
