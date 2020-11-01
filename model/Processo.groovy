package model

class Processo{
    int pid
    int prioridade
    int offset
    int qtdBlocos
    boolean impressora
    boolean scanner
    boolean drivers

    @Deprecated
    Processo(){}

    Processo(pid, prioridade, offset, qtdBlocos, impressora, scanner, drivers){
        this.pid = pid
        this.prioridade = prioridade
        this.offset = offset
        this.qtdBlocos = qtdBlocos
        this.impressora = impressora
        this.scanner = scanner
        this.drivers = drivers
    }

    // @Override
    // String toString(){
    //     return pid as String
    // }
}