package pso.module

class SistemaArquivos {
    char[] mapaDisco
    int opNum

    SistemaArquivos(totalBlocos){
        mapaDisco = new char[totalBlocos]
    }

    @Deprecated
    String add(name, primeiroBloco, qtdBlocos){
        if(mapaDisco.contains(name)){
            println "Arquivo ${name} já existe!"
            return
        }
        for(int i = primeiroBloco; i < qtdBlocos; i++){
            if(mapaDisco[i]){
                println "Arquivo ${name}: Bloco ocupado!"
                return
            }
        }
        for(int i = primeiroBloco; i < qtdBlocos+primeiroBloco; i++){
            mapaDisco[i] = name
        }
    }

    boolean create(name){
        this.opNum++
    }

    boolean delete(){
        this.opNum++
    }

    @Override
    String toString(){
        String s = "Mapa de ocupação do disco:\n|"
        for(arquivo in this.mapaDisco){
            s += " ${arquivo ? arquivo : '0'} |"
        }
        return s
    }
}
