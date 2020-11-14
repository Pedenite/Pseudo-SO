package model

class SistemaArquivos {
    char[] mapaDisco

    SistemaArquivos(totalBlocos){
        mapaDisco = new char[totalBlocos]
    }

    @Override
    String toString(){
        String s = ""
        for(i in (0..this.mapaDisco.length-1)){
            s += this.mapaDisco[i]
        }
    }
}