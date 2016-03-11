package arvoreb;

/** NoArvoreB.java

  @author Marcos Alves (marcos@ucdb.br)

*/

/**

   Arvore B

   @author     Marcos Alves (marcos@ucdb.br)
   @created    20 de Março de 2013
*/


// Cada nó da árvore será do tipo NoArvoreB
class NoArvoreB {

    boolean folha; // Indica se o nó é folha
    int ordem;     // ordem da árvore
    int totalChaves;  // total de chaves dentro do nó
    int chaves[];     // chaves
    NoArvoreB filhos[]; // filhos
    NoArvoreB pai;      // pai do nó

    NoArvoreB() {
        pai = null;
        totalChaves = 0;
    }


    public NoArvoreB insere( int chave ) {
        NoArvoreB novaRaiz = null;
        if (totalChaves == 0) {  // Está inserindo o primeiro nó da árvore
            inicializaVetores(); // aloca espaço para os vetores
            chaves[0] = chave;
            folha = true;
            ++totalChaves;
        } else {
            if (folha) {
                novaRaiz = insereDentroDoNo(chave,null);
            } else {
                NoArvoreB filho = buscaFilho(chave);
                novaRaiz = filho.insere(chave);
            }
        }
        return novaRaiz;
    }


    public void remove(int chave, NoArvoreB no) {

        NoArvoreB noAux;
        int count = 0,pos;


                if(no.filhos[count] == null ){
                    noAux = no.pai;

                    for(int i=0;i<noAux.totalChaves;i++) {
                        if (noAux.chaves[i] == chave) {
                            System.out.println("Chamar Funcao de Remocao - Chave no No!!");
                        }
                    }

                    pos = buscaNoChave(chave,noAux);
                    System.out.println("Posicao: "+pos);
                    buscaDentroNo(chave,noAux,pos);

                }else if (chave < no.chaves[count]) {
                    noAux = no.filhos[count];
                    remove(chave,noAux);
                } else if (chave > no.chaves[count]) {
                    noAux = no.filhos[count+1];
                    remove(chave,noAux);
                }else{
                    System.out.println("Chamar Funcao de Remocao - Chave no Raiz");
                }

                count++;
    }


    public void buscaDentroNo (int chave,  NoArvoreB no, int indice) {

        for (int i = indice; i < no.totalChaves+1 ; i++) {
            //System.out.println("Chave do No: "+no.chaves[1]);
            for (int k = 0; k < no.filhos[i].totalChaves; k++) {
                System.out.println(no.filhos[i].chaves[k]);
                if (no.filhos[i].chaves[k] == chave) {
                    System.out.println("Chamar Funcao de Remocao - Chave na Folha !!");

                    k = no.filhos[i].totalChaves+1;
                    i = no.totalChaves;
                }
            }
        }
    }



    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    private NoArvoreB insereDentroDoNo(int chave, NoArvoreB novoNo) {
        int posicao = buscaPosicao(chave);
        abreEspaco(posicao);
        chaves[posicao]=chave;
        filhos[posicao+1]=novoNo;
        ++totalChaves;
        if (extrapolou()) {
            return(cisao());
        } else return null;
    }



    private NoArvoreB cisao() {
        int chave = elementoCentral();
        NoArvoreB novoNo = criaNovoNo();
        novoNo.folha = folha; // Nó irmão tem o mesmo status
        novoNo.pai = pai; // E o mesmo pai
        NoArvoreB novaRaiz = null;

        transfereMetade(novoNo);
        limpaMetade();
        if (pai != null) {
            novaRaiz = pai.insereDentroDoNo(chave,novoNo);
        } else {
            pai = criaNovaRaiz(chave,this,novoNo);
            novoNo.pai = pai;
            novaRaiz = pai;
        }
        return novaRaiz;
    }


    private void inicializaVetores() {
        chaves = new int[2*ordem+1]; // Deixa espaço para que o nó ultrapasse o limite antes da cisão (por isso soma 1).
        filhos = new NoArvoreB[2*ordem+2];
    }

    private NoArvoreB buscaFilho(int chave) {
        int i;
        for (i=0;i<totalChaves;++i) {
            if (chave<chaves[i]) break;
        }
        return(filhos[i]);
    }

    private int buscaPosicao(int chave) {
        int i;
        for (i=0;i<totalChaves;++i) {
            if (chave<chaves[i]) break;
        }
        return(i);
    }

    private int buscaNoChave(int chave, NoArvoreB no) {
        int i;
        for (i=0;i<no.totalChaves;++i) {
            if (chave<no.chaves[i]) {
                break;
            }
        }
        return(i);
    }

    private void abreEspaco(int posicao) {
        for (int i=totalChaves;i>posicao;--i) {
            chaves[i]=chaves[i-1];
            filhos[i+1]=filhos[i];
        }
    }

    private boolean extrapolou() {
        if (totalChaves > 2*ordem) return true;
        else return false;
    }


    private int elementoCentral() {
        return(chaves[totalChaves/2]);
    }

    private NoArvoreB criaNovoNo() {
        NoArvoreB nova = new NoArvoreB();
        nova.ordem = ordem;
        nova.inicializaVetores();
        return(nova);
    }


    private void transfereMetade(NoArvoreB novoNo) {
        int i,j;
        for (i=ordem+1,j=0;i<totalChaves;++i,++j) {

            novoNo.chaves[j] = chaves[i];
            novoNo.filhos[j] = filhos[i];
            if (filhos[i]!=null) filhos[i].pai = novoNo;
        }
        novoNo.filhos[j] = filhos[i];
        if (filhos[i]!=null) filhos[i].pai = novoNo;
        novoNo.totalChaves = ordem;
    }

    private void limpaMetade() {
        totalChaves = ordem;
    }

    private NoArvoreB criaNovaRaiz(int chave,NoArvoreB FE, NoArvoreB FD) {
        NoArvoreB nova = criaNovoNo();
        nova.chaves[0] = chave;
        nova.filhos[0] = FE;
        nova.filhos[1] = FD;
        nova.totalChaves = 1;
        nova.folha = false;
        return(nova);
    }



    /** Devolve o contedo completo da árvore utilizando percurso em Pré-Ordem */
    String mostra(int como) {

        StringBuffer saida = new StringBuffer();

        if (como == 1) {
            percorrePreOrdem(saida,0);
        }

        return saida.toString();

    }


    private String espacos(int t)
    {
        StringBuffer string = new StringBuffer();
        for (int i=1;i<=t;++i) string.append(" ");
        return string.toString();
    }


    private void percorrePreOrdem(StringBuffer saida, int nivel) {
        saida.append(espacos(nivel*5));
        saida.append(listaChaves()+"\n");
        if (!folha) {
            for (int i=0;i<=totalChaves;++i) {
                filhos[i].percorrePreOrdem(saida,nivel+1);
            }
        }
    }


    private String listaChaves() {

        StringBuffer saida = new StringBuffer();


        int i,j;
        for (i=0;i<totalChaves;++i) {
            saida.append(chaves[i]+" ");
        }
        return saida.toString();
    }


}
