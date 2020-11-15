package pso

import pso.controller.Manager
import pso.module.Processo
import pso.util.Logger

def manager = new Manager()
def procsInfo
def filesInfo
def instructionsIndex
def instructions = []

if(args.size() < 2){
    Logger.error("Não foram passados argumentos suficientes!")
}
if(args.size() > 2){
    Logger.error("Devem ser passados apenas 2 arquivos para a execução!")
}

/******** Inicializacao dos modulos ********/
try {
    procsInfo = new File(args[0]).text.replace(" ", "").split("\n")*.split(",")
    filesInfo = new File(args[1]).text.replace(" ", "").split("\n")
} catch(FileNotFoundException e){
    Logger.error("Arquivo não encontrado! -->> $e")
}

for(pInfo in procsInfo){   
    try {
        println("dispatcher =>")         
        manager.dispatch(pInfo)
    } catch(IllegalArgumentException e){
        Logger.warning("Processo inválido -->> ${e.message}\n")
    }
}

try {
    println "Inicialização Sistema de Arquivos =>"
    instructionsIndex = manager.prepareFS(filesInfo)
} catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
    Logger.error("Não foi possível inicializar! -->> $e")
}
println manager.fs

for(int i = instructionsIndex; i < filesInfo.size(); i++){
    instructions << filesInfo[i]
}



