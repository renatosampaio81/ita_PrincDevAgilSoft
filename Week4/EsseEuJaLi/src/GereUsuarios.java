import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GereUsuarios {
    private List<Usuario> usuarios;
    private String arquivoUsuarios;
    private Usuario usuarioLogado;
    private GereLivros gereLivros;

    public GereUsuarios(String arquivoUsuarios, GereLivros gereLivros) {
        this.arquivoUsuarios = arquivoUsuarios;
        this.gereLivros = gereLivros;
        usuarios = new ArrayList<>();
        carregarUsuarios();
    }
    
    private void carregarUsuarios() {
        File arquivo = new File(arquivoUsuarios);
        if (arquivo.exists()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                usuarios = mapper.readValue(arquivo, new TypeReference<List<Usuario>>() {});
            } catch (IOException e) {
                System.out.println("Erro ao carregar usuários do arquivo.");
            }
        }
    }

    private void salvarUsuarios() {
        File arquivo = new File(arquivoUsuarios);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, usuarios);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários no arquivo.");
        }
    }

    public boolean adicionarUsuario(String nome, String senha) {
        if (usuarios.stream().anyMatch(u -> u.getNome().equals(nome))) {
            return false;
        }
        Usuario novoUsuario = new Usuario(nome, senha);
        usuarios.add(novoUsuario);
        salvarUsuarios();
        return true;
    }

    public boolean fazerLogin(String nomeUsuario, String senha) {
        Optional<Usuario> usuarioEncontrado = usuarios.stream()
                .filter(usuario -> usuario.getNome().equals(nomeUsuario))
                .findFirst();

        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().verificarSenha(senha)) {
            usuarioLogado = usuarioEncontrado.get();
            return true;
        }
        return false;
    }
    
    public boolean verificarLivroLido(Livro livro) {
        return usuarioLogado.getLivrosLidosStatus().getOrDefault(livro.getTitulo(), false);
    }
    
    public List<Usuario> getUsuarios() {
    	return new ArrayList<>(usuarios);
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
     
    public void marcarLivroComoLido(Livro livro) {
        if (livro != null) {
        	usuarioLogado.getLivrosLidosStatus().put(livro.getTitulo(), true);
        	atualizarPontuacaoUsuario(usuarioLogado);
        	
        	String trofeuEstilo = "Leitor de " + livro.getEstilo();
        	if (contarLivrosLidosPorEstilo(usuarioLogado, livro.getEstilo()) >= 5) {
        		usuarioLogado.adicionarTrofeu(trofeuEstilo);            	
    		}
            salvarUsuarios();
        } else {
            System.out.println("Livro não encontrado");
        }
    }
    
    public void desmarcarLivroComoLido(Livro livro) {
        if (livro != null) {
            usuarioLogado.getLivrosLidosStatus().put(livro.getTitulo(), false);
            atualizarPontuacaoUsuario(usuarioLogado);

            String trofeuEstilo = "Leitor de " + livro.getEstilo();
            usuarioLogado.getTrofeus().removeIf(trofeu -> trofeu.equals(trofeuEstilo) && contarLivrosLidosPorEstilo(usuarioLogado, livro.getEstilo()) < 5);
            salvarUsuarios();
        } else {
            System.out.println("Livro não encontrado");
        }
    }
    
    private int contarLivrosLidosPorEstilo(Usuario usuario, String estilo) {
        return (int) usuario.getLivrosLidosStatus().entrySet().stream()
                .filter(entry -> entry.getValue() && gereLivros.buscarLivroPorTitulo(entry.getKey()).getEstilo().equals(estilo))
                .count();
    }
    
    public int contarLivrosLidos(Usuario usuarioLogado) {
        int livrosLidos = 0;
        for (Map.Entry<String, Boolean> entry : usuarioLogado.getLivrosLidosStatus().entrySet()) {
        	boolean livroLido = entry.getValue();
        	if (livroLido) {
                livrosLidos += 1;
            }
        }
        return livrosLidos;
    }
    
    public void atualizarPontuacaoUsuario(Usuario usuarioLogado) {
        int totalPontos = usuarioLogado.getLivrosLidosStatus().entrySet().stream()
                .filter(Map.Entry::getValue)
                .mapToInt(entry -> calcularPontosLivro(gereLivros.buscarLivroPorTitulo(entry.getKey())))
                .sum();
        usuarioLogado.setPontos(totalPontos);
    }


    private int calcularPontosLivro(Livro livro) {
        int pontosBase = 1;
        int pontosPorPaginas = livro.getPaginas() / 100;
        return pontosBase + pontosPorPaginas;
    }

}