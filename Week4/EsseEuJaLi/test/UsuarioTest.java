import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario("Renato", "coxinha123");
    }

    @Test
    public void verificarSenhaCorreta() {
        boolean resultado = usuario.verificarSenha("coxinha123");
        Assertions.assertTrue(resultado);
    }

    @Test
    public void verificarSenhaIncorreta() {
        boolean resultado = usuario.verificarSenha("empadinha456");
        Assertions.assertFalse(resultado);
    }

    @Test
    public void adicionarTrofeuComSucesso() {
        usuario.adicionarTrofeu("Campeão");
        List<String> trofeus = usuario.getTrofeus();
        Assertions.assertTrue(trofeus.contains("Campeão"));
    }

    @Test
    public void adicionarTrofeuExistente() {
        usuario.adicionarTrofeu("Campeão");
        usuario.adicionarTrofeu("Campeão");
        List<String> trofeus = usuario.getTrofeus();
        Assertions.assertEquals(1, trofeus.size());
    }

    @Test
    public void removerTrofeuExistente() {
        usuario.adicionarTrofeu("Campeão");
        usuario.removerTrofeu("Campeão");
        List<String> trofeus = usuario.getTrofeus();
        Assertions.assertFalse(trofeus.contains("Campeão"));
    }

    @Test
    public void removerTrofeuInexistente() {
        usuario.adicionarTrofeu("Campeão");
        usuario.removerTrofeu("Vice-Campeão");
        List<String> trofeus = usuario.getTrofeus();
        Assertions.assertTrue(trofeus.contains("Campeão"));
    }

    @Test
    public void getSetPontos() {
        usuario.setPontos(100);
        int pontos = usuario.getPontos();
        Assertions.assertEquals(100, pontos);
    }

    @Test
    public void InicializacaoLivrosVazia() {
        Map<String, Boolean> livrosStatus = usuario.getLivrosLidosStatus();
        Assertions.assertTrue(livrosStatus.isEmpty());
    }
}

