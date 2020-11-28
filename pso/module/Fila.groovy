package pso.module

class Fila {
    def procs = []
    final static int max = 1000

    boolean push(p){
        if(!(p instanceof Processo) || this.procs.size() == max || this.procs.contains(p)){
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

    boolean isEmpty(){
        return this.procs.size() == 0
    }

    int size(){
        return this.procs.size()
    }

    // Nao sera possivel acessar a propriedade diretamente
    def setProcs(p){throw new IllegalArgumentException("Operação ilegal!")}
    def getProcs(){throw new IllegalArgumentException("Operação ilegal!")}

    @Override
    String toString(){
        if(this.isEmpty()){
            return "-"
        }
        def lista = []
        for(int i = procs.size()-1; i >= 0; i--){
            lista.add(procs[i])
        }
        return lista.join(" | ") + " >"
    }
}
