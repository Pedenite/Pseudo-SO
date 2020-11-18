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
def instructionsFS = []

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

int processosEspera = watchedProcesses.size()

try {
    println "=== Inicialização Sistema de Arquivos ==="
    instructionsIndex = manager.prepareFS(filesInfo)
} catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
    Logger.error("Não foi possível inicializar! -->> $e")
}
println("${manager.fs}\n")

for(int i = instructionsIndex; i < filesInfo.size(); i++){
    instructionsFS << filesInfo[i]
}

/******** Executando ********/
def finished = false
for(int elapsedTime = 0; !finished; elapsedTime++){
    def processosNow = watchedProcesses.findAll{ processo -> processo.tempoInicio == elapsedTime }
    for(proc in processosNow){
        manager.dispatch(proc)
        processosEspera--
    }
    
    def processosRodando = manager.verificaProcessosProntos()
    if(!processosRodando.isEmpty()){
        println("\n===== Dispatcher =====")
        for(process in processosRodando){
            def proc = watchedProcesses.find{ processo -> processo.pid == process }
            println "PID: ${proc.pid}\noffset: ${proc.offset}\nblocks: ${proc.blocks}\npriority: ${proc.prioridade}\ntime: ${proc.tempoUsado}\nprinters: ${proc.impressora != 0}\nscanners: ${proc.scanner != 0}\nmodems: ${proc.modem != 0}\ndrives: ${proc.drivers != 0}\n"
        }
    }

    manager.organizaProcessos()
    finished = manager.atribuiQuantum() && processosEspera == 0
    
    // debug(manager)
    
    sleep(1000)
}

/******** Instrucoes do Sistema de Arquivos ********/
for(instrucao in instructionsFS){

}

/******** Finalizando ********/
println("\n===== Dispatcher =====")
Logger.success("Exited successfully!")

def debug(manager){
    println("\n${manager.escalonador}\n")
    println("${manager.memoria}\n")
}
