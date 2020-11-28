package pso.module

class Recurso {
  def recursos = [
    "scanner":new int[1],
    "impressora":new int[2],
    "modem":new int[1],
    "sata":new int[2]
  ]

  int alocarRecurso(nomeRecurso, pidProcesso) {
    try {
      int indexAlocado = -1

      if (!recursos[nomeRecurso]) {
        throw new Exception("Não foi possível acessar dados do recurso.")
      }

      for (int i = 0; i < recursos[nomeRecurso].length; ++i) {
        if (recursos[nomeRecurso][i] == pidProcesso) {
          break
        }

        if (recursos[nomeRecurso][i] == 0) {
          recursos[nomeRecurso][i] = pidProcesso
          indexAlocado = i

          break
        } 
      }

      return indexAlocado
    } catch (Exception e) {
      println(e.getMessage())

      return -1
    }
  }

  boolean desalocarRecurso(nomeRecurso, index) {
    try {
      if (!recursos[nomeRecurso]) {
        throw new Exception("Não foi possível acessar dados do recurso.")
      }
      
      if (recursos[nomeRecurso][index] != 1) {
        throw new Exception("Não é possível desalocar um recurso não utilizado.")
      }

      recursos[nomeRecurso][index] = 0

      return true
    } catch (Exception e) {
      println(e.getMessage())

      return false
    }
  }

  // desaloca todos os recursos de um processo
  boolean desalocarTudo(pid){
    recursos.each { nomeRecurso, recurso ->
      for(int i = 0; i < recurso.size(); i++){
        if(recurso[i] == pid){
          recurso[i] = 0
        }
      }
    }
  }

  @Override
  String toString(){
    return ("Scanner: " + recursos["scanner"].join(', ') + '\n'
      + "Impressora: " + recursos["impressora"].join(', ') + '\n'
      + "modem: " + recursos["modem"].join(', ') + '\n'
      + "sata: " + recursos["sata"].join(', ') + '\n')
  }
}