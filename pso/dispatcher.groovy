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
    def processosNow = watchedProcesses.findAll{ processo -> processo.tempoInicio == elapsedTime }
    for(proc in processosNow){
        manager.dispatch(proc)
    }
    println("${manager.escalonador}\n")
    
    def processosRodando = manager.verificaProcessosProntos()
    if(!processosRodando.isEmpty()){
        println("dispatcher =>")
        for(process in processosRodando){
            def proc = watchedProcesses.find{ processo -> processo.pid == process }
            println "  PID: ${proc.pid}\n  offset: ${proc.offset}\n  blocks: ${proc.blocks}\n  priority: ${proc.prioridade}\n  time: ${proc.tempoUsado}\n  printers: ${proc.impressora != 0}\n  scanners: ${proc.scanner != 0}\n  modems: ${proc.modem != 0}\n  drives: ${proc.drivers != 0}\n"
        }
    }

    manager.organizaProcessos()
    manager.atribuiQuantum()
    
    if(elapsedTime == 25){ //temporario
        finished = true
    }
    sleep(1000)
}

/******** Finalizando ********/
println("dispatcher =>")
// liberar recursos do processo 0
Logger.success("Exited successfully!")
