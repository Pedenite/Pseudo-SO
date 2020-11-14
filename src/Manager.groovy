import model.*

class Manager {
    Fila processosProntos
    Fila tempoReal
    Fila processosUsuario
    Fila prioridade1
    Fila prioridade2
    Fila prioridade3

    Manager(){
        processosProntos = new Fila()
        tempoReal = new Fila()
        processosUsuario = new Fila()
        prioridade1 = new Fila()
        prioridade2 = new Fila()
        prioridade3 = new Fila()
    }

    void dispatch(listaAtributos){
        if(listaAtributos?.size() < 8){
            throw new IllegalArgumentException("${listaAtributos}")
        }
        listaAtributos = listaAtributos*.toInteger()
        def processo = new Processo(listaAtributos[0], listaAtributos[1], listaAtributos[2], listaAtributos[3], listaAtributos[4], listaAtributos[5], listaAtributos[6], listaAtributos[7])
        
        println "\ndispatcher =>\n  PID: ${processo.pid}\n  offset: ${processo.offset}\n  blocks: ${processo.blocks}\n  priority: ${processo.prioridade}\n  time: ${processo.tempoUsado}\n  printers: ${processo.impressora != 0}\n  scanners: ${processo.scanner != 0}\n  modems: ${processo.modem != 0}\n  drives: ${processo.drivers != 0}"
        this.processosProntos << processo
    }

    void prepare(fileSystemProperties){

    }
}
