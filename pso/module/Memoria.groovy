package pso.module

class Memoria {
  def espaco = [
    "TempoReal":new int[64],
    "Usuario":new int[960]
  ]

  int verificarOffsetDisponivel(tamanhoProcesso, tempoReal) {
    int espacosDisponiveisConsecutivos = 0
    int indexDisponivel = -1
    int[] espacoTotal

    if(tamanhoProcesso < 1){
      return -1
    }

    if(tempoReal){
      espacoTotal = espaco["TempoReal"]
    } else {
      espacoTotal = espaco["Usuario"]
    }

    for (int i = 0; i < espacoTotal.length; i++) {
      if (((i - espacosDisponiveisConsecutivos) + tamanhoProcesso) > espacoTotal.length) {
        break
      }

      if (espacoTotal[i] == 0) {
        espacosDisponiveisConsecutivos++
      } else {
        espacosDisponiveisConsecutivos = 0
      }

      if (espacosDisponiveisConsecutivos == tamanhoProcesso) {
        indexDisponivel = 1 + (i - tamanhoProcesso)

        break
      }
    }

    return indexDisponivel
  }

  void alocarProcesso(offset, tamanhoProcesso, tempoReal) {
    int limitOffset = tamanhoProcesso + offset
    int[] espacoTotal
    if(tempoReal){
      espacoTotal = espaco["TempoReal"]
    } else {
      espacoTotal = espaco["Usuario"]
    }

    while(offset < limitOffset) {
      espacoTotal[offset] = 1;

      ++offset;
    }
  }

  void desalocarProcesso(offset, tamanhoProcesso, tempoReal) {
    int limitOffset = tamanhoProcesso + offset
    int[] espacoTotal
    if(tempoReal){
      espacoTotal = espaco["TempoReal"]
    } else {
      espacoTotal = espaco["Usuario"]
    }

    while(offset < limitOffset) {
      espacoTotal[offset] = 0;

      ++offset;
    }
  }

  @Override
  String toString(){
    return "===== Ocupação da Memória =====\n\nEspaço tempo real: " + espaco["TempoReal"].join(', ') + "\n\n" + "Espaço usuário: " + espaco["Usuario"].join(', ');
  }
}
