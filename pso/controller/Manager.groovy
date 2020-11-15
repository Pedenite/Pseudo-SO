package pso.controller

import pso.module.*

class Manager {
    SistemaArquivos fs
    Escalonador escalonador
    Memoria memoria

    Manager(Processo dispatcher){
        memoria = new Memoria()
        escalonador = new Escalonador()
        escalonador.prepara(dispatcher)
        escalonador.escalona(memoria)
    }

    Processo prepareProcess(listaAtributos){
        if(listaAtributos?.size() < 8){
            throw new IllegalArgumentException("${listaAtributos}")
        }
        listaAtributos = listaAtributos*.toInteger()

        return new Processo(listaAtributos[0], listaAtributos[1], listaAtributos[2], listaAtributos[3], listaAtributos[4], listaAtributos[5], listaAtributos[6], listaAtributos[7])
    }

    void dispatch(processo){
        
        println "  PID: ${processo.pid}\n  offset: ${processo.offset}\n  blocks: ${processo.blocks}\n  priority: ${processo.prioridade}\n  time: ${processo.tempoUsado}\n  printers: ${processo.impressora != 0}\n  scanners: ${processo.scanner != 0}\n  modems: ${processo.modem != 0}\n  drives: ${processo.drivers != 0}\n"
        escalonador.prepara(processo)
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
            fs.add(name, bloco, qtdBlocos)
        }
        return n+3
    }
}
