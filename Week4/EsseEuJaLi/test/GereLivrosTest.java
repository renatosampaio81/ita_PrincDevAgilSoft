import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GereLivrosTest {
    private GereLivros gereLivros;
    private String arquivoLivros = "data/livrosTest.json";

    @BeforeEach
    public void setUp() {
        limparArquivoLivros();
        gereLivros = new GereLivros(arquivoLivros);
    }

    private void limparArquivoLivros() {
        try {
            List<Livro> livrosIniciais = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(arquivoLivros), livrosIniciais);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdicionarLivro() {
        boolean resultado = gereLivros.adicionarLivro("Dom Quixote", "Miguel de Cervantes", 863, "Um clássico da literatura.", "Romance");
        Assertions.assertTrue(resultado);
        List<Livro> livros = gereLivros.getLivros();
        Assertions.assertEquals(1, livros.size());
        Livro livroAdicionado = livros.get(0);
        Assertions.assertEquals("Dom Quixote", livroAdicionado.getTitulo());
        Assertions.assertEquals("Miguel de Cervantes", livroAdicionado.getAutor());
        Assertions.assertEquals(863, livroAdicionado.getPaginas());
        Assertions.assertEquals("Um clássico da literatura.", livroAdicionado.getSinopse());
        Assertions.assertEquals("Romance", livroAdicionado.getEstilo());
    }

    @Test
    public void testAdicionarLivroExistente() {
        boolean resultado = gereLivros.adicionarLivro("Dom Quixote", "Miguel de Cervantes", 863, "Um clássico da literatura.", "Romance");
        Assertions.assertTrue(resultado);
        resultado = gereLivros.adicionarLivro("Dom Quixote", "Miguel de Cervantes", 863, "Um clássico da literatura.", "Romance");
        Assertions.assertFalse(resultado);
        List<Livro> livros = gereLivros.getLivros();
        Assertions.assertEquals(1, livros.size());
    }

    @Test
    public void testListarLivros() {
        gereLivros.adicionarLivro("Dom Quixote", "Miguel de Cervantes", 863, "Um clássico da literatura.", "Romance");
        gereLivros.adicionarLivro("1984", "George Orwell", 328, "Uma distopia futurista.", "Ficção Científica");

        List<Livro> livros = gereLivros.getLivros();

        StringBuilder listaLivros = new StringBuilder("Livros:\n");
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            listaLivros.append(String.format("%d. Título: %s\n", i + 1, livro.getTitulo()));
            listaLivros.append(String.format("   Autor: %s\n", livro.getAutor()));
            listaLivros.append(String.format("   Páginas: %d\n", livro.getPaginas()));
            listaLivros.append(String.format("   Sinopse: %s\n", livro.getSinopse()));
            listaLivros.append(String.format("   Estilo: %s\n", livro.getEstilo()));
        }

        String expected = "Livros:\n" +
                "1. Título: Dom Quixote\n" +
                "   Autor: Miguel de Cervantes\n" +
                "   Páginas: 863\n" +
                "   Sinopse: Um clássico da literatura.\n" +
                "   Estilo: Romance\n" +
                "2. Título: 1984\n" +
                "   Autor: George Orwell\n" +
                "   Páginas: 328\n" +
                "   Sinopse: Uma distopia futurista.\n" +
                "   Estilo: Ficção Científica";

        Assertions.assertEquals(expected, listaLivros.toString().trim());
    }
}
