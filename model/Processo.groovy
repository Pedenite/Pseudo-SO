package model

class Processo{
    int pid
    int prioridade
    int offset
    int qtdBlocos
    boolean impressora
    boolean scanner
    boolean drivers

    static int count

    @Deprecated
    Processo(){}

    Processo(prioridade, offset, qtdBlocos, impressora, scanner, drivers){
        this.pid = ++count
        this.prioridade = prioridade
        this.offset = offset
        this.qtdBlocos = qtdBlocos
        this.impressora = impressora
        this.scanner = scanner
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