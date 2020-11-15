package pso

import pso.controller.Manager
import pso.module.Processo
import pso.util.Logger

if(args.size() < 2){
    Logger.error("Não foram passados argumentos suficientes!")
}
if(args.size() > 2){
    Logger.error("Devem ser passados apenas 2 arquivos para a execução!")
}

/******** Preparacao do ambiente ********/
def dispatcher = new Processo(64)
def manager = new Manager(dispatcher)
def procsInfo
def filesInfo
def instructionsIndex
def watchedProcesses = []
def instructions = []

try {
    procsInfo = new File(args[0]).text.replace(" ", "").split("\n")*.split(",")
    filesInfo = new File(args[1]).text.replace(" ", "").split("\n")
} catch(FileNotFoundException e){
    Logger.error("Arquivo não encontrado! -->> $e")
}

for(pInfo in procsInfo){   
    try {
        watchedProcesses << manager.prepareProcess(pInfo)
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

/******** Executando ********/
def finished = false
for(int elapsedTime = 0; !finished; elapsedTime++){
    def processosNow = watchedProcesses.findAll{processo -> processo.tempoInicio == elapsedTime}
    println(processosNow)
    
    if(elapsedTime == 25){ //temporario
        finished = true
    }
    sleep(1000)
}

/******** Finalizando ********/
println("dispatcher =>")
// remover processo 0 da fila
Logger.success("Exited successfully!")
