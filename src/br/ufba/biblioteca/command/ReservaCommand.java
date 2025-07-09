package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Reserva;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;
import br.ufba.biblioteca.util.DateUtil;

import java.time.LocalDate;

public class ReservaCommand implements Command {
    private Repositorio repositorio;

    public ReservaCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: res <codigoUsuario> <codigoLivro>");
            return;
        }

        int codigoUsuario = Integer.parseInt(args[0]);
        int codigoLivro = Integer.parseInt(args[1]);

        Usuario usuario = repositorio.buscarUsuarioPorCodigo(codigoUsuario);
        Livro livro = repositorio.buscarLivroPorCodigo(codigoLivro);

        if (usuario == null) {
            System.out.println("Erro: Usuário com código " + codigoUsuario + " não encontrado.");
            return;
        }
        if (livro == null) {
            System.out.println("Erro: Livro com código " + codigoLivro + " não encontrado.");
            return;
        }

        if (usuario.buscarReservaPorLivro(livro) != null) {
            System.out.println("O usuário " + usuario.getNome() + " já possui uma reserva para o livro '" + livro.getTitulo() + "'.");
            return;
        }
        
        if (usuario.temExemplarEmprestado(livro)) {
            System.out.println("Não foi possível realizar a reserva, pois o usuário já possui um exemplar deste livro emprestado.");
            return;
        }

        LocalDate dataReserva = LocalDate.now();
        Reserva novaReserva = new Reserva(usuario, livro, dataReserva);

        livro.adicionarReserva(novaReserva);
        usuario.adicionarReserva(novaReserva);

        System.out.println("Reserva do livro '" + livro.getTitulo() + "' por " + usuario.getNome() + " realizada com sucesso.");
        System.out.println("Data da Reserva: " + DateUtil.format(dataReserva));
    }
}