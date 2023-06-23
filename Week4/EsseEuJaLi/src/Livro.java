public class Livro {
    private String titulo;
    private String autor;
    private int paginas;
    private String sinopse;
    private String estilo;

    public Livro(String titulo, String autor, int paginas, String sinopse, String estilo) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.sinopse = sinopse;
        this.estilo = estilo;
    }
    
    public Livro() {
    	// Construtor padrão necessário para a desserialização do JSON
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getPaginas() {
        return paginas;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

}