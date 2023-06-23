import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
    private String nome;
    private String senha;
    private int pontos;
    private List<String> trofeus;
    private Map<String, Boolean> livrosLidosStatus;
    
    public Usuario() {
        // Construtor padrão necessário para a desserialização do JSON
    	this.trofeus = new ArrayList<>();
    }

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.pontos = 0;
        this.trofeus = new ArrayList<>();
        this.livrosLidosStatus = new HashMap<>();
    }

    public boolean verificarSenha(String senha) {
    	return this.senha.equals(senha);
    }

    public String getNome() {
    	return new String(nome);
    }
    
    public String getSenha() {
    	return new String(senha);
    }
    
    public Map<String, Boolean> getLivrosLidosStatus() {
        return livrosLidosStatus;
    }
    
    public int getPontos() {
        return pontos;
    }
    
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    
    public List<String> getTrofeus() {
        return trofeus;
    }
    
    /*
    public void setTrofeus(List<String> trofeus) {
        this.trofeus = trofeus;
    }
    */
    
    public void adicionarTrofeu(String trofeu) {
    	if (!trofeus.contains(trofeu)) {
            trofeus.add(trofeu);
        }
    }
    
    public void removerTrofeu(String trofeu) {
    	trofeus.remove(trofeu);
    }
 
}