package model

class Processo {
    int pid
    int tempoInicio
    int prioridade
    int tempoUsado
    int offset
    int blocks
    int impressora
    int scanner
    int modem
    int drivers

    static int count

    @Deprecated
    Processo(){}

    @Deprecated
    Processo(tempoInicio, prioridade, tempoUsado, blocks, impressora, scanner, modem, drivers){
        this.pid = ++count
        this.tempoInicio = tempoInicio
        this.prioridade = prioridade
        this.tempoUsado = tempoUsado
        // offset -> implementar memoria
        this.blocks = blocks
        this.impressora = impressora
        this.scanner = scanner
        this.modem = modem
        this.drivers = drivers
    }

    void setPid(pid){
        throw new IllegalArgumentException("Não é possível alterar o PID!")
    }

    @Override
    boolean equals(p){
        return this.pid == p.pid
    }

    @Override
    String toString(){
        return pid as String
    }
}
