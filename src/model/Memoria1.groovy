package model

class Memoria {
  int[] espacoTempoReal = new int[64]
  int[] espacoUsuario = new int[20]

  int verificarOffsetDisponivel(tamanhoProcesso) {    
    int espacosDisponiveisConsecutivos = 0
    int indexDisponivel = -1

    for (int i = 0; i < espacoUsuario.length; i++) {
      if (((i - espacosDisponiveisConsecutivos) + tamanhoProcesso) > espacoUsuario.length) {
        break
      }

      if (espacoUsuario[i] == 0) {
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

  void alocarProcesso(offset, tamanhoProcesso) {
    int limitOffset = tamanhoProcesso + offset

    while(offset < limitOffset) {
      espacoUsuario[offset] = 1;

      ++offset;
    }
  }

  void desalocarProcesso(offset, tamanhoProcesso) {
    int limitOffset = tamanhoProcesso + offset

    while(offset < limitOffset) {
      espacoUsuario[offset] = 0;

      ++offset;
    }
  }

  @Override
  String toString(){
    return "Espaço usuário: " + espacoUsuario.join(', ') + "\n\n" 
      // "Espaço tempo real: " + espacoTempoReal.join;
  }
}

def mem = new Memoria()


mem.alocarProcesso(0, 3)
mem.alocarProcesso(3, 3)
mem.alocarProcesso(6, 14)

int teste1 = mem.verificarOffsetDisponivel(21)
int teste2 = mem.verificarOffsetDisponivel(14)
int teste3 = mem.verificarOffsetDisponivel(20)

println("${mem} ${teste1}, ${teste2}, ${teste3}")