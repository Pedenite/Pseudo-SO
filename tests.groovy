import model.Processo

def p = new Processo()
try {
    p.pid = 11  // vai dar erro
} catch(Exception e){
    println e.message
}
println p.pid

if(!args.size()){
    return println("\u001B[31mNão foram passados argumentos!\u001B[0m")
}

def manager = new Manager() // sistema operacional

for(arg in args){
    try {
        def procsInfo = new File(arg).text.replace(" ", "")split("\n")*.split(",")
        for(pInfo in procsInfo){            
            manager.dispatch(pInfo)
        }
    } catch(FileNotFoundException e){
        return println("\u001B[31mArquivo não encontrado! -->> $e\u001B[0m")
    } catch(IllegalArgumentException e){
        return println("\u001B[31mProcesso inválido -->> ${e.message}\u001B[0m")
    }
}

println manager.processos
