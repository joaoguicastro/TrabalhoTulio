import java.io.*;
import Entidades.TabelaHashGenerico;
import Entidades.ArvoreBinaria;
import Entidades.ListaSimplesmenteEncadeada;

public class App {
    private TabelaHashGenerico<ListaSimplesmenteEncadeada<Integer>> tabelaHash;
    private ArvoreBinaria<String> arvoreBinaria;
    private ListaSimplesmenteEncadeada<String> palavrasChave;

    public App() {
        tabelaHash = new TabelaHashGenerico<>(128);
        arvoreBinaria = new ArvoreBinaria<>();
        palavrasChave = new ListaSimplesmenteEncadeada<>();
    }

    public void lerPalavrasChave(String arquivoPalavrasChave) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivoPalavrasChave));
        String linha;
        while ((linha = reader.readLine()) != null) {
            String palavra = linha.trim().toLowerCase();
            palavrasChave.insereFinal(palavra);
            tabelaHash.insere(palavra, new ListaSimplesmenteEncadeada<>());
            arvoreBinaria.insere(palavra);
        }
        reader.close();
    }

    public void processarTexto(String arquivoTexto) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivoTexto));
        String linha;
        int numeroLinha = 1;
        while ((linha = reader.readLine()) != null) {
            String[] palavras = linha.split("\\W+");
            for (String palavra : palavras) {
                palavra = palavra.toLowerCase();
                if (palavrasChave.contem(palavra)) {
                    ListaSimplesmenteEncadeada<Integer> lista = tabelaHash.busca(palavra);
                    if (lista != null) {
                        lista.insereFinal(numeroLinha);
                    }
                }
            }
            numeroLinha++;
        }
        reader.close();
    }

    public void gerarIndiceRemissivo(String arquivoSaida) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoSaida));
        ListaSimplesmenteEncadeada<String>.Nodo palavraNodo = palavrasChave.primeiro;
        while (palavraNodo != null) {
            String palavra = palavraNodo.valor;
            ListaSimplesmenteEncadeada<Integer> lista = tabelaHash.busca(palavra);
            writer.write(palavra + " ");
            ListaSimplesmenteEncadeada<Integer>.Nodo temp = lista.primeiro;
            while (temp != null) {
                writer.write(temp.valor + " ");
                temp = temp.proximo;
            }
            writer.newLine();
            palavraNodo = palavraNodo.proximo;
        }
        writer.close();
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("para rodar o codigo use java -cp bin App palavras-chave.txt texto.txt resultado.txt");
            return;
        }

        App indice = new App();
        try {
            indice.lerPalavrasChave(args[0]);
            indice.processarTexto(args[1]);
            indice.gerarIndiceRemissivo(args[2]);
            System.out.println("Indice remissivo gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }
}
