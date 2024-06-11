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

    public void lerPalavrasChave(String arquivoPalavrasChave) throws Exception {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(arquivoPalavrasChave));
        String linha;
        while ((linha = reader.readLine()) != null) {
            String palavra = normalizar(linha.trim().toLowerCase());
            palavrasChave.insereFinal(palavra);
            tabelaHash.insere(palavra, new ListaSimplesmenteEncadeada<>());
            arvoreBinaria.insere(palavra);
        }
        reader.close();
    }

    public void processarTexto(String arquivoTexto) throws Exception {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(arquivoTexto));
        String linha;
        int numeroLinha = 1;
        while ((linha = reader.readLine()) != null) {
            String[] palavras = linha.split("[^\\p{L}-]+");
            for (String palavra : palavras) {
                palavra = normalizar(palavra.toLowerCase());
                if (palavrasChave.contem(palavra) || palavrasChave.contem(removerPlural(palavra))) {
                    String chave = palavrasChave.contem(palavra) ? palavra : removerPlural(palavra);
                    ListaSimplesmenteEncadeada<Integer> lista = tabelaHash.busca(chave);
                    if (lista != null) {
                        lista.insereFinal(numeroLinha);
                    }
                }
            }
            numeroLinha++;
        }
        reader.close();
    }

    public void gerarIndiceRemissivo(String arquivoSaida) throws Exception {
        java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(arquivoSaida));
        arvoreBinaria.imprimeEmOrdemComIndices(tabelaHash, writer);
        writer.close();
    }

    private String normalizar(String palavra) {
        char[] acentos = { 'á', 'é', 'í', 'ó', 'ú', 'à', 'è', 'ì', 'ò', 'ù', 'ã', 'õ', 'â', 'ê', 'î', 'ô', 'û', 'ç' };
        char[] semAcentos = { 'a', 'e', 'i', 'o', 'u', 'a', 'e', 'i', 'o', 'u', 'a', 'o', 'a', 'e', 'i', 'o', 'u',
                'c' };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palavra.length(); i++) {
            char c = palavra.charAt(i);
            boolean found = false;
            for (int j = 0; j < acentos.length; j++) {
                if (c == acentos[j]) {
                    sb.append(semAcentos[j]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String removerPlural(String palavra) {
        if (palavra.endsWith("s")) {
            return palavra.substring(0, palavra.length() - 1);
        }
        return palavra;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java App <arquivo_palavras_chave> <arquivo_texto> <arquivo_saida>");
            return;
        }

        App indice = new App();
        try {
            indice.lerPalavrasChave(args[0]);
            indice.processarTexto(args[1]);
            indice.gerarIndiceRemissivo(args[2]);
            System.out.println("Índice remissivo gerado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }
}
