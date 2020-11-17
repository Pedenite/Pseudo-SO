package pso.module

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

    Processo(blocks){
        this.pid = count++
        this.blocks = blocks
    }

    Processo(tempoInicio, prioridade, tempoUsado, blocks, impressora, scanner, modem, drivers){
        this.pid = count++
        this.tempoInicio = tempoInicio
        this.prioridade = prioridade
        this.tempoUsado = tempoUsado
        this.offset = -1
        this.blocks = blocks
        this.impressora = impressora
        this.scanner = scanner
        this.modem = modem
        this.drivers = drivers
    }

    // operacoes ilegais
    void setPid(pid){throw new IllegalArgumentException("Não é possível alterar o PID!")}
    void setCount(count){throw new IllegalArgumentException("Operação ilegal!")}

    @Override
    boolean equals(p){
        return this.pid == p.pid
    }

    @Override
    int hashCode(){
        return this.pid.hashCode()
    }

    @Override
    String toString(){
        return pid as String
    }
}
