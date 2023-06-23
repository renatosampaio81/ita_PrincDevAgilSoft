import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class LivroTest {
	
	@Test
	public void testConstrutorLivro() {
	    Livro livro = new Livro("Título", "Autor", 200, "Sinopse", "Estilo");
	    
	    assertEquals("Título", livro.getTitulo());
	    assertEquals("Autor", livro.getAutor());
	    assertEquals(200, livro.getPaginas());
	    assertEquals("Sinopse", livro.getSinopse());
	    assertEquals("Estilo", livro.getEstilo());
	}
	
	@Test
	public void testGetSetTitulo() {
	    Livro livro = new Livro("Título", "Autor", 200, "Sinopse", "Estilo");
	    
	    assertEquals("Título", livro.getTitulo());
	    
	    livro.setTitulo("Novo Título");
	    assertEquals("Novo Título", livro.getTitulo());
	}
	
	@Test
	public void testGetSetAutor() {
	    Livro livro = new Livro("Título", "Autor", 200, "Sinopse", "Estilo");
	    
	    assertEquals("Autor", livro.getAutor());
	    
	    livro.setAutor("Novo Autor");
	    assertEquals("Novo Autor", livro.getAutor());
	}
	
	@Test
	public void testGetSetPaginas() {
	    Livro livro = new Livro("Título", "Autor", 200, "Sinopse", "Estilo");
	    
	    assertEquals(200, livro.getPaginas());
	    
	    livro.setPaginas(300);
	    assertEquals(300, livro.getPaginas());
	}
	
	@Test
	public void testGetSetSinopse() {
	    Livro livro = new Livro("Título", "Autor", 200, "Sinopse", "Estilo");
	    
	    assertEquals("Sinopse", livro.getSinopse());
	    
	    livro.setSinopse("Nova Sinopse");
	    assertEquals("Nova Sinopse", livro.getSinopse());
	}
	
	@Test
	public void testGetSetEstilo() {
	    Livro livro = new Livro("Título", "Autor", 200, "Sinopse", "Estilo");
	    
	    assertEquals("Estilo", livro.getEstilo());
	    
	    livro.setEstilo("Novo Estilo");
	    assertEquals("Novo Estilo", livro.getEstilo());
	}

}
