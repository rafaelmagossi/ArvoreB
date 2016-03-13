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
    int posChave;
    int pos;
    int chaves[];     // chaves
    NoArvoreB noAuxBusca;
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

    public void remover(int chave,NoArvoreB noRaiz) {
        NoArvoreB noAux;
        NoArvoreB noAnterior;
        int posChaveBusca, posRemove, chaveAux, totalChavesConcatenacao;

        noAux = buscar(chave, noRaiz, false);

        if (noAux != null) {
            posChaveBusca = noAux.posChave;
            System.out.println("pos: " + pos);
            System.out.println("Chave a ser Removida: " + noAux.chaves[posChaveBusca]);

            noAux.chaves[posChaveBusca] = 0;
            noAux.totalChaves--;

                if (noAux.naoExtrapolou()) {

                    concatena(chave,noAux);


                }else if(noAux.extrapolou()){

                }

        }else{
            System.out.println("Chave nao Existe");
        }


    }


    public NoArvoreB buscar(int chave, NoArvoreB no, boolean achouChave) {



        int pos, count = 0;

        while (count < no.totalChaves && achouChave != true) {
            System.out.println("Chaves: " + no.chaves[count]);
            if (no.chaves[count] == chave) {
                System.out.println("Chamar Funcao de Remocao - Chave encontrada");
                noAuxBusca = no;
                noAuxBusca.posChave = count;
                achouChave = true;


            } else if (count+1  == no.totalChaves && achouChave == false) {
                pos = buscaNoChave(chave, no);
                if (no.filhos[pos] != null) {
                    //System.out.println("\nPosicao no Nó: " + pos);
                    buscar(chave, no.filhos[pos], achouChave);
                }else{
                    //System.out.println("Chave nao Existe");
                    noAuxBusca = null;
                }
            }
            count++;
        }
        //System.out.println("teste: "+noAux.chaves[posChave]);
        return noAuxBusca;
    }

    public void concatena(int chave,NoArvoreB noFolha){

        NoArvoreB no;
        NoArvoreB filhoConcatenacao = null;
        int posRemove, chaveAux, totalChavesSomaConcatenacao, totalFolhaResultante;
        int k = 0;



        posRemove = buscaNoChave(chave,noFolha.pai);


        if(posRemove ==0){
            filhoConcatenacao = noFolha.pai.filhos[posRemove+1];
        }else if(posRemove == noFolha.pai.totalChaves){
            filhoConcatenacao = noFolha.pai.filhos[posRemove-1];
        }else if(noFolha.pai.filhos[posRemove+1] != null && noFolha.pai.filhos[posRemove-1] != null ){
            if(noFolha.pai.filhos[posRemove+1].totalChaves < noFolha.pai.filhos[posRemove-1].totalChaves){
                filhoConcatenacao = noFolha.pai.filhos[posRemove+1];
            }else{
                filhoConcatenacao = noFolha.pai.filhos[posRemove-1];

            }
        }

        chaveAux = noFolha.pai.chaves[posRemove-1];
        noFolha.insereFolha(chaveAux);
        totalChavesSomaConcatenacao = filhoConcatenacao.totalChaves + noFolha.totalChaves;
        totalFolhaResultante = filhoConcatenacao.totalChaves;


        while(filhoConcatenacao.totalChaves < totalChavesSomaConcatenacao){

            System.out.println(noFolha.chaves[k]);
            filhoConcatenacao.chaves[totalFolhaResultante] = noFolha.chaves[k];
            filhoConcatenacao.totalChaves++;
            noFolha.chaves[k] = 0;
            noFolha.totalChaves --;
            k++;
            totalFolhaResultante++;
        }

        noFolha.pai.chaves[posRemove-1] = 0;

        if(filhoConcatenacao.totalChaves == 5){
            //filhoConcatenacao.cisao();

            int chaveCentral, posicaoCentral;
            int k1 =0;


            chaveCentral = filhoConcatenacao.elementoCentral();
            noFolha.pai.chaves[posRemove-1] = chaveCentral;
            posicaoCentral = filhoConcatenacao.totalChaves/2;
            filhoConcatenacao.chaves[posicaoCentral] = 0;
            //System.out.println("posicaoCentral: "+filhoConcatenacao.totalChaves);

           while(k1 < 2 ){

               noFolha.chaves[k1]= filhoConcatenacao.chaves[posicaoCentral+1];
               noFolha.totalChaves++;
               System.out.println("Nofolha: "+noFolha.chaves[k1]);
               filhoConcatenacao.chaves[posicaoCentral+1] = 0;
               posicaoCentral++;
               filhoConcatenacao.totalChaves --;
               k1++;
            }
            filhoConcatenacao.totalChaves --;
        }else if(noFolha.pai.chaves[noFolha.pai.totalChaves] == 0){
            noFolha.pai.totalChaves--;
        }


        if(noFolha.pai.naoExtrapolou()){
            System.out.println("Chave Pai: "+noFolha.pai.chaves[0]);
            //concatena(noFolha.pai.chaves[0],noFolha.pai);
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

    private NoArvoreB insereFolha(int chave) {
        int posicao = buscaPosicao(chave);
        abreEspaco(posicao);
        chaves[posicao]=chave;
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

    private boolean naoExtrapolou() {
        if (totalChaves < ordem) {
            return true;
        }else{
            return false;
        }
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
