final def erro = "\u001B[31m"
final def erroF = "\u001B[0m"

Manager manager = new Manager()
def instructions = []

if(args.size() < 2){
    return println("${erro}Não foram passados argumentos suficientes!${erroF}")
}
if(args.size() > 2){
    return println("${erro}Foram passados mais argumentos que o necessário!${erroF}")
}

try {
    def procsInfo = new File(args[0]).text.replace(" ", "").split("\n")*.split(",")
    for(pInfo in procsInfo){            
        manager.dispatch(pInfo)
    }

    def filesInfo = new File(args[1]).text.replace(" ", "").split("\n")
    def instructionsIndex = manager.prepareFS(filesInfo)

    for(int i = instructionsIndex; i < filesInfo.size(); i++){
        instructions << filesInfo[i]
    }

} catch(FileNotFoundException e){
    return println("${erro}Arquivo não encontrado! -->> $e${erroF}\nSaindo com status de erro...")
} catch(IllegalArgumentException e){
    println("dispatcher =>\n${erro}Processo inválido -->> ${e.message}${erroF}")
}

println manager.fs
