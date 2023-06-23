import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GereUsuariosTest {
    private GereUsuarios gereUsuarios;
    private String arquivoUsuarios = "data/usuariosTest.json";
    private GereLivros gereLivros;
    private String arquivoLivros = "data/livrosTest.json";

    @BeforeEach
    public void setUp() {
        limparArquivoUsuarios();
        gereLivros = new GereLivros(arquivoLivros);
        gereUsuarios = new GereUsuarios(arquivoUsuarios, gereLivros);
    }

    private void limparArquivoUsuarios() {
        try {
            List<Usuario> usuariosIniciais = new ArrayList<>();
            usuariosIniciais.add(new Usuario("usuario1", "senha1"));
            usuariosIniciais.add(new Usuario("usuario2", "senha2"));

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(arquivoUsuarios), usuariosIniciais);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdicionarUsuario() {
        boolean resultado = gereUsuarios.adicionarUsuario("novoUsuario", "senhaNova");
        Assertions.assertTrue(resultado);
        Assertions.assertTrue(gereUsuarios.fazerLogin("novoUsuario", "senhaNova"));
    }

    @Test
    public void testAdicionarUsuarioExistente() {
        boolean resultado = gereUsuarios.adicionarUsuario("usuario1", "senha1");
        Assertions.assertFalse(resultado);
    }

    @Test
    public void testFazerLoginUsuarioExistente() {
        boolean resultado = gereUsuarios.fazerLogin("usuario2", "senha2");
        Assertions.assertTrue(resultado);
    }

    @Test
    public void testFazerLoginUsuarioInexistente() {
        boolean resultado = gereUsuarios.fazerLogin("usuario3", "senha3");
        Assertions.assertFalse(resultado);
    }

    @Test
    public void testCarregarUsuariosArquivoExistente() {
        gereUsuarios = new GereUsuarios(arquivoUsuarios, gereLivros);
        List<Usuario> usuarios = gereUsuarios.getUsuarios();
        Assertions.assertEquals(2, usuarios.size());
        Assertions.assertEquals("usuario1", usuarios.get(0).getNome());
        Assertions.assertEquals("usuario2", usuarios.get(1).getNome());
    }

    @Test
    public void testCarregarUsuariosArquivoInexistente() {
        gereUsuarios = new GereUsuarios("usuarios_inexistentes.json", gereLivros);
        List<Usuario> usuarios = gereUsuarios.getUsuarios();
        Assertions.assertTrue(usuarios.isEmpty());
    }
}
