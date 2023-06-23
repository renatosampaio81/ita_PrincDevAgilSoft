import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GereLivros {
    private List<Livro> livros;
    private String arquivoLivros;
    
    public GereLivros(String arquivoLivros) {
        this.arquivoLivros = arquivoLivros;
        livros = new ArrayList<>();
        carregarLivros();
    }
    
    private void carregarLivros() {
        File arquivo = new File(arquivoLivros);
        if (arquivo.exists()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                livros = mapper.readValue(arquivo, new TypeReference<List<Livro>>() {});
            } catch (IOException e) {
                System.out.println("Erro ao carregar livros do arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("O arquivo de livros não foi encontrado.");
        }
    }
    
    private void salvarLivros() {
        try {
            File arquivo = new File(arquivoLivros);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, livros);
        } catch (IOException e) {
            System.out.println("Erro ao salvar livros no arquivo: " + e.getMessage());
        }
    }
    
    public boolean adicionarLivro(String titulo, String autor, int paginas, String sinopse, String estilo) {
        if (buscarLivroPorTitulo(titulo) != null) {
            return false;
        }
        Livro novoLivro = new Livro(titulo, autor, paginas, sinopse, estilo);
        livros.add(novoLivro);
        salvarLivros();
        return true;
    }
       
    public List<Livro> getLivros() {
        return new ArrayList<>(livros);
    }
    
    public Livro buscarLivroPorTitulo(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(titulo)) {
                return livro;
            }
        }
        return null;
    }


}