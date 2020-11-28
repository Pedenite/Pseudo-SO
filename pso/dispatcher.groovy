package pso

import pso.controller.Manager
import pso.module.Processo
import pso.util.Logger

final DEBUG_MODE = false

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
            println "PID: ${proc.pid}\noffset: ${proc.offset}\nblocks: ${proc.blocks}\npriority: ${proc.prioridade}\ntime: ${proc.tempoUsado}\nprinters: ${proc.impressora != 0}\nscanners: ${proc.scanner != 0}\nmodems: ${proc.modem != 0}\nsata: ${proc.sata != 0}\n"
        }
    }

    manager.organizaProcessos()
    finished = manager.atribuiQuantum() && processosEspera == 0
    
    DEBUG_MODE ? debug(manager) : null
    DEBUG_MODE ? System.console().readLine("Enter para continuar...") : sleep(1000)
}

/******** Instrucoes do Sistema de Arquivos ********/
println("\n===== Sistema de Arquivos =====")
for(instrucao in instructionsFS){
    def info = instrucao.split(",")
    if(info[1] == "0" && info.size() != 4 || info[1] == "1" && info.size() != 3){
        Logger.warning("Instrução do sistema de arquivo ${info} inválida")
        continue
    }
    
    def processo
    try {
        processo = watchedProcesses.find { proc -> proc.pid == info[0].toInteger() }
        if(!processo){
            if(info[0] == "0"){
                processo = dispatcher
            } else {
                throw new Exception()
            }
        }
    } catch(Exception e){
        Logger.warning("Processo ${info[0]} inexistente")
        continue
    }

    def blocos = 0
    try{
        if(info[1] == "0"){
            blocos = info[3].toInteger()
            if(blocos < 1){
                throw new Exception()
            }
        }
    } catch(Exception e){
        Logger.warning("Quantidade de blocos inválida: ${blocos}")
        continue
    }
    manager.chamaSistemaArquivos(processo, info[1], info[2], blocos)
    println()
}

println("${manager.fs}\n")

/******** Finalizando ********/
println("\n===== Dispatcher =====")
Logger.success("Exited successfully!")

def debug(manager){
    println("\n${manager.escalonador}\n")
    println("${manager.memoria}\n")
    println("${manager.recurso}\n")
}
