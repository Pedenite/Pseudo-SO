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
    int sata
    int instrucaoAtual

    static int count

    Processo(blocks){
        this.pid = count++
        this.blocks = blocks
    }

    Processo(tempoInicio, prioridade, tempoUsado, blocks, impressora, scanner, modem, sata){
        this.pid = count++
        this.tempoInicio = tempoInicio
        this.prioridade = prioridade
        this.tempoUsado = tempoUsado
        this.offset = -1
        this.blocks = blocks
        this.impressora = impressora
        this.scanner = scanner
        this.modem = modem
        this.sata = sata
    }

    boolean execute(){
        if(this.instrucaoAtual == 0){
            println("P${this.pid}: Inicializando")
        }

        this.instrucaoAtual++
        println("P${this.pid}: Executando instrucao ${this.instrucaoAtual}")

        if(this.instrucaoAtual >= tempoUsado){
            println("P${this.pid}: Finalizado com sucesso")
            return true
        }
        
        return false
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
