package model

class Fila {
    def procs = []
    final static int max = 1000

    boolean push(p){
        if(!(p instanceof Processo) || this.procs.size() == max){
            return false
        }
        this.procs << p
    }
    boolean leftShift(p){return this.push(p)}

    Processo pop(){
        if(this.procs.size() == 0)
            return null
        return this.procs.remove(0)
    }

    // Nao sera possivel acessar a propriedade diretamente
    def setProcs(p){throw new IllegalArgumentException("Operação ilegal!")}
    def getProcs(){throw new IllegalArgumentException("Operação ilegal!")}

    @Override
    String toString(){
        return this.procs.toString()
    }
}
