import model.Processo

def p = new Processo()
try {
    p.pid = 11  // vai dar erro
} catch(Exception e){
    println e.message
}
println p.pid

if(!args.size())
    return println("\u001B[31mNão foram passados argumentos!\u001B[0m")

def procs = []

for(arg in args){
    def processoErro
    try {
        def procsInfo = new File(arg).text.replace(" ", "")split("\n")*.split(",")
        for(pInfo in procsInfo){
            processoErro = pInfo
            if(pInfo?.size() < 8)
                throw new IllegalArgumentException()

            procs << new Processo(pInfo[1] as int, pInfo[2] as int, pInfo[3] as int, pInfo[4] == 1, pInfo[5] == 1, pInfo[6] == 1) // nao sera assim no final (ver README)
        }
    } catch(FileNotFoundException e){
        return println("\u001B[31mArquivo não encontrado! -->> $e\u001B[0m")
    } catch(IllegalArgumentException e){
        return println("\u001B[31mProcesso inválido! ($processoErro)\u001B[0m")
    }
}

println procs*.pid

def dispatcher(pInfo){
    
}
