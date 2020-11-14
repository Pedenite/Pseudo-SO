package model

class SistemaArquivos {
    char[] mapaDisco
    int opNum

    SistemaArquivos(totalBlocos){
        mapaDisco = new char[totalBlocos]
    }

    boolean create(name){

    }

    boolean delete(){

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
