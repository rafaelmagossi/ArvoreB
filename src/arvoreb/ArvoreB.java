package arvoreb;

/** ArvoreB.java

  @author Marcos Alves (marcos@ucdb.br)


*/

/**

   Arvore B

   @author     Marcos Alves (marcos@ucdb.br)
   @created    20 de Março de 2013
*/


class ArvoreB {

    NoArvoreB raiz;

    ArvoreB() {
        raiz = new NoArvoreB();
    }

    public void insere( int chave ) {
        NoArvoreB no;
        no = raiz.insere(chave);
        if(no != null) raiz = no;
    }

    public void remove(int chave) {
        raiz.remove(chave,raiz,false);
    }

    public void setOrdem(int ordem) {
        raiz.setOrdem(ordem);
    }

    String mostra(int como) {
        return(raiz.mostra(como));
    }

}
