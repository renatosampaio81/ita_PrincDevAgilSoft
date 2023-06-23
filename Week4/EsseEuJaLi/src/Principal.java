import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String PATCH_USUARIOS = "data/usuarios.json";
    private static final String PATCH_LIVROS = "data/livros.json";
    private GereUsuarios gereUsuarios;
    private GereLivros gereLivros;
    
    public Principal() {
        gereLivros = new GereLivros(PATCH_LIVROS);
        gereUsuarios = new GereUsuarios(PATCH_USUARIOS, gereLivros);
    }

    public void MenuAcesso() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Esse Eu Já Li ! ===");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Logar");
            System.out.println("3. Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarNovoUsuario(scanner);
                    break;
                case 2:
                	if (logar(scanner)) {
                        menuPrincipal(scanner);
                    }
                    break;
                case 3:
                    encerrar(scanner);
                    return;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
                    break;
            }
        }
    }

    public void cadastrarNovoUsuario(Scanner scanner) {
        System.out.println("\n=== Cadastro de Novo Usuário ===");
        System.out.print("Digite o nome de usuário [min:4 - max:16]: ");
        String nomeUsuario = scanner.nextLine();
        
        System.out.print("Digite a senha [min:4 - max:16]: ");
        String senha = scanner.nextLine();

        if (nomeUsuario.length() < 4 || nomeUsuario.length() > 16 || senha.length() < 4 || senha.length() > 16
                || nomeUsuario.contains(" ") || senha.contains(" ")) {
            System.out.println("\nNome de usuário ou senha inválidos. Tente novamente.");
            return;
        }

        if (gereUsuarios.adicionarUsuario(nomeUsuario, senha)) {
            System.out.println("\nUsuário cadastrado com sucesso!");
        } else {
            System.out.println("\nO nome de usuário já está em uso. Tente novamente.");
        }
    }

    public boolean logar(Scanner scanner) {
        System.out.println("\n=== Login ===");
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        if (gereUsuarios.fazerLogin(nomeUsuario, senha)) {
            System.out.println("\nLogin realizado com sucesso!");
            return true;
        } else {
            System.out.println("\nNome de usuário ou senha incorretos. Tente novamente.");
            return false;
        }
    }
    
    public void menuPrincipal(Scanner scanner) {
        while (true) {
        	gereUsuarios.atualizarPontuacaoUsuario(gereUsuarios.getUsuarioLogado());
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Visualizar Lista de Livros");
            System.out.println("2. Ranking");
            System.out.println("3. Minha Página Pessoal");
            System.out.println("4. Deslogar");
            System.out.print("\nEscolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                	visualizarListaLivros(scanner, gereUsuarios.getUsuarioLogado());
                    break;
                case 2:
                	exibirRanking(scanner);
                    break;
                case 3:
                	exibirPaginaPessoal(scanner);
                	break;
                case 4:
                    System.out.println("\nDeslogando...");
                    return;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
                    break;
            }
        }
    }
    
    public void visualizarListaLivros(Scanner scanner, Usuario usuario) {
        System.out.println("\n=== Lista de Livros ===");
        
        List<Livro> livros = gereLivros.getLivros();
        
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            String statusLido = gereUsuarios.verificarLivroLido(livro)? "[Lido]" : "[Não Lido]";
            System.out.println((i + 1) + ". " + statusLido + " " + livro.getTitulo() + " [" + livro.getEstilo() + "]");
        }
        
        System.out.print("\nDigite o número do livro para visualizar detalhes (0 para voltar): ");
        int opcaoLivro = scanner.nextInt();
        scanner.nextLine();
        
        if (opcaoLivro == 0) {
            return;
        }
        
        int indiceLivro = opcaoLivro - 1;
        
        if (indiceLivro >= 0 && indiceLivro < livros.size()) {
            Livro livroSelecionado = livros.get(indiceLivro);
            exibirDetalhesLivro(livroSelecionado, scanner);
        } else {
            System.out.println("\nOpção inválida. Tente novamente.");
        }
    }

    public void exibirDetalhesLivro(Livro livro, Scanner scanner) {
        System.out.println("\n=== Detalhes do Livro ===");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor());
        System.out.println("Número de Páginas: " + livro.getPaginas());
        System.out.println("Sinopse: " + livro.getSinopse());
        System.out.println("Estilo: " + livro.getEstilo());
        
        boolean livroLido = gereUsuarios.verificarLivroLido(livro);
        String statusLivro = livroLido ? "Lido" : "Não Lido";
        System.out.println("Status: " + statusLivro);

        String mensagem = livroLido ? "Deseja marcar o livro como NÃO LIDO? (S/N): " :
                                      "Deseja marcar o livro como LIDO? (S/N): ";
        System.out.print("\n" + mensagem);
        String opcao = scanner.nextLine();

        if (opcao.equalsIgnoreCase("S")) {
            if (livroLido) {
                gereUsuarios.desmarcarLivroComoLido(livro);
                System.out.println("\nLivro marcado como não lido.");
            } else {
                gereUsuarios.marcarLivroComoLido(livro);
                System.out.println("\nLivro marcado como lido.");
            }
        }
        
        visualizarListaLivros(scanner, gereUsuarios.getUsuarioLogado());
    }
    
    public void exibirRanking(Scanner scanner) {
        List<Usuario> usuarios = new ArrayList<>(gereUsuarios.getUsuarios());
        usuarios.sort(Comparator.comparingInt(Usuario::getPontos).reversed());

        System.out.println("\n=== Ranking ===");
        System.out.println("Pos.\tUsuário\t\tPontuação");
        System.out.println("----------------------------------------------");

        int posicao = 1;
        for (Usuario usuario : usuarios) {
            if (posicao > 10) {
                break; // Exit the loop after displaying the top 10 users
            }
            
            String nomeUsuario = usuario.getNome();
            // Verifica se é o usuário logado e destaca o nome
            if (usuario.equals(gereUsuarios.getUsuarioLogado())) {
                nomeUsuario = "*" + nomeUsuario + "*";
            }
            
            System.out.printf("%-8s%-16s%-10s%n", posicao, nomeUsuario, usuario.getPontos());
            posicao++;
        }

        System.out.println("----------------------------------------------");
        System.out.println("\nPressione 0 para retornar ao menu anterior");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 0) {
            return;
        }
    }

    
    public void exibirPaginaPessoal(Scanner scanner) {
        Usuario usuario = gereUsuarios.getUsuarioLogado();
        System.out.println("\n=== Minha Página Pessoal ===");
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Livros Lidos: " + gereUsuarios.contarLivrosLidos(gereUsuarios.getUsuarioLogado()));
        System.out.println("Pontuação total: " + usuario.getPontos());
        System.out.println("Troféus conquistados: " + usuario.getTrofeus());
        System.out.println("\nPressione 0 para retornar ao menu anterior");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 0) {
            return;
        }

    }

    public void encerrar(Scanner scanner) {
        System.out.println("\nEncerrando o programa...");
        scanner.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.MenuAcesso();
    }
}