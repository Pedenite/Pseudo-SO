import model.*

class Manager {
    List<Processo> processos = []

    Processo dispatch(listaAtributos){
        if(listaAtributos?.size() < 8){
            throw new IllegalArgumentException("${listaAtributos}")
        }
        listaAtributos = listaAtributos*.toInteger()
        def p = new Processo(listaAtributos[0], listaAtributos[1], listaAtributos[2], listaAtributos[3], listaAtributos[4], listaAtributos[5], listaAtributos[6], listaAtributos[7])
        println "\ndispatcher =>\n  PID: ${p.pid}\n  offset: ${p.offset}\n  blocks: ${p.blocks}\n  priority: ${p.prioridade}\n  time: ${p.tempoUsado}\n  printers: ${p.impressora != 0}\n  scanners: ${p.scanner != 0}\n  modems: ${p.modem != 0}\n  drives: ${p.drivers != 0}"
        this.processos << p
        return p
    }
}
