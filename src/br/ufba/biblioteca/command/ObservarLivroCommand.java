package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.observer.Observador; // Importa a interface Observador
import br.ufba.biblioteca.repository.Repositorio;

public class ObservarLivroCommand implements Command {
    private Repositorio repositorio;

    public ObservarLivroCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: obs <codigoUsuarioObservador> <codigoLivro>");
            return;
        }

        int codigoUsuarioObservador = Integer.parseInt(args[0]);
        int codigoLivro = Integer.parseInt(args[1]);

        Usuario usuario = repositorio.buscarUsuarioPorCodigo(codigoUsuarioObservador);
        Livro livro = repositorio.buscarLivroPorCodigo(codigoLivro);

        if (usuario == null) {
            System.out.println("Erro: Usuário com código " + codigoUsuarioObservador + " não encontrado.");
            return;
        }
        if (livro == null) {
            System.out.println("Erro: Livro com código " + codigoLivro + " não encontrado.");
            return;
        }

        // verifica se o usuario implementa a interface Observador
        if (!(usuario instanceof Observador)) {
             System.out.println("Erro: O usuário com código " + codigoUsuarioObservador + " não pode observar livros.");
             return;
        }

        Observador observador = (Observador) usuario;
        livro.adicionarObservador(observador);
        
        System.out.println("Usuário " + usuario.getNome() + " (Código: " + usuario.getCodigo() + ") registrado como observador do livro '" + livro.getTitulo() + "' com sucesso.");
    }
}