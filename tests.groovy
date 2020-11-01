import model.Processo

def p = new Processo()
p.pid = 11

println p.pid
if(!args.size())
    return println("\u001B[31mNão foram passados argumentos!\u001B[0m")

def procs = new ArrayList<Processo>()

for(arg in args){
    def processo
    try {
        def procsInfo = new File(arg).text.replace(" ", "")split("\n")*.split(",")
        println procsInfo
        for(info in procsInfo){
            processo = info
            if(info.size() < 8)
                throw new IllegalArgumentException()
            procs << new Processo(info[0] as int, info[1] as int, info[2] as int, info[3] as int, info[4] == 1, info[5] == 1, info[6] == 1) // nao sera assim no final (ver README)
        }
        println procs*.pid
    } catch(FileNotFoundException e){
        return println("\u001B[31mArquivo não encontrado! -->> $e\u001B[0m")
    } catch(IllegalArgumentException e){
        return println("\u001B[31mProcesso inválido! ($processo)\u001B[0m")
    }
}
