package pso.module

class SistemaArquivos {
    Arquivo[] mapaDisco
    int opNum

    SistemaArquivos(totalBlocos){
        mapaDisco = new Arquivo[totalBlocos]
    }

    @Deprecated // Apenas para inicializacao a partir do segundo arquivo de entrada
    void init(name, primeiroBloco, qtdBlocos){
        if(mapaDisco.contains(name)){
            println "Arquivo ${name} já existe!"
            return
        }

        if(name.size() != 1 || !name.matches("[a-zA-Z]+")){
            println("Os arquivos devem ter apenas uma letra como nome!")
            return
        }

        for(int i = primeiroBloco; i < qtdBlocos+primeiroBloco; i++){
            if(mapaDisco[i]){
                println "Arquivo ${name}: Bloco ocupado!"
                return
            }
        }
        
        for(int i = primeiroBloco; i < qtdBlocos+primeiroBloco; i++){
            mapaDisco[i] = new Arquivo(name, 0)
        }
    }

    // first fit
    void create(name, pid, blocosSolicitados){
        this.opNum++
        print("Operação ${opNum} >>> ")
        if(name.size() != 1 || !name.matches("[a-zA-Z]+")){
            println("falha\nOs arquivos devem ter apenas uma letra como nome!")
        }

        for(arquivo in mapaDisco){
            if(arquivo?.nome == name){
                println("falha\nNão foi possível criar o arquivo (Já existe um arquivo com o mesmo nome)")
                return 
            }
        }

        boolean sucesso = false
        int blocosDisponiveis = 0
        for(int i = 0; i < this.mapaDisco.length; i++){
            if(mapaDisco[i]){
                blocosDisponiveis = 0
                continue
            }

            blocosDisponiveis++
            if(blocosDisponiveis == blocosSolicitados){
                for(int j = i; j > i-blocosDisponiveis; j--){
                    mapaDisco[j] = new Arquivo(name, pid)
                }
                sucesso = true
                break
            }
        }

        if(sucesso){
            println("sucesso\nArquivo ${name} criado com sucesso!")
        } else {
            println("falha\nNão foi possível criar o arquivo (Não há espaço o suficiente)")
        }
    }

    void delete(name, pid, prioridade){
        this.opNum++
        print("Operação ${opNum} >>> ")
        if(pid < 0){
            println("falha\nProcesso inexistente!")
            return
        }

        boolean processoTempoReal = prioridade == 0
        int posicao = mapaDisco.findIndexOf{ elem -> 
            elem?.nome == name && (processoTempoReal || elem.processoCriador == pid)
        }
        if(posicao < 0){
            println("falha\nArquivo ${name} inexistente ou processo não possui acesso!")
            return
        }

        while(mapaDisco[posicao]?.nome == name){
            mapaDisco[posicao] = null
            posicao++
        }
        println("sucesso\nProcesso ${pid} deletou o arquivo ${name}")
    }

    // Nao sera possivel acessar a propriedade diretamente
    def setMapaDisco(p){throw new IllegalArgumentException("Operação ilegal!")}
    def getMapaDisco(){throw new IllegalArgumentException("Operação ilegal!")}

    @Override
    String toString(){
        String s = "Mapa de ocupação do disco:\n|"
        for(arquivo in this.mapaDisco){
            s += " ${arquivo ? arquivo : '0'} |"
        }
        return s
    }
}
