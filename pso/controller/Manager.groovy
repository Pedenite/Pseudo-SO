package pso.controller

import pso.module.*

class Manager {
    SistemaArquivos fs
    Escalonador escalonador
    Memoria memoria
    Processo dispatcher

    Manager(Processo dispatcher){
        memoria = new Memoria()
        escalonador = new Escalonador()
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
        return n+3
    }

    void dispatch(processos){
        escalonador.prepara(processos)
    }

    def verificaProcessosProntos(){
        return escalonador.verificaDisponibilidade(this.memoria)
    }

    void organizaProcessos(){
        boolean continuar = true
        while(continuar){
            continuar = escalonador.classificaProcesso()
        }
    }

    void atribuiQuantum(){
        escalonador.executaProcessos()
    }
}
