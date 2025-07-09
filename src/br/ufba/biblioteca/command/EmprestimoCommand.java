package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Emprestimo;
import br.ufba.biblioteca.model.Exemplar;
import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Reserva;
import br.ufba.biblioteca.model.StatusExemplar;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;
import br.ufba.biblioteca.util.DateUtil;

import java.time.LocalDate;
import java.util.Optional;

public class EmprestimoCommand implements Command {
    private Repositorio repositorio;

    public EmprestimoCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: emp <codigoUsuario> <codigoLivro>");
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

        String motivoRecusa = usuario.getRegraEmprestimo().podeRealizarEmprestimo(usuario, livro, repositorio);

        if (motivoRecusa != null) {
            System.out.println(motivoRecusa);
            return;
        }

        Optional<Exemplar> exemplarDisponivel = livro.getExemplares().stream()
                                                    .filter(e -> e.getStatus() == StatusExemplar.DISPONIVEL)
                                                    .findFirst();

        if (!exemplarDisponivel.isPresent()) {
            System.out.println("Não foi possível realizar o empréstimo, pois não há exemplares disponíveis deste livro no momento.");
            return;
        }

        Exemplar exemplarParaEmprestimo = exemplarDisponivel.get();
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataPrevistaDevolucao = dataEmprestimo.plusDays(usuario.getTempoEmprestimoDias());

        Emprestimo novoEmprestimo = new Emprestimo(usuario, exemplarParaEmprestimo, dataEmprestimo, dataPrevistaDevolucao);
        
        exemplarParaEmprestimo.setEmprestimoCorrente(novoEmprestimo);
        usuario.adicionarEmprestimoCorrente(novoEmprestimo);

        Reserva reservaDoUsuario = usuario.buscarReservaPorLivro(livro);
        if (reservaDoUsuario != null) {
            usuario.removerReserva(reservaDoUsuario);
            livro.removerReserva(reservaDoUsuario);
            System.out.println("Reserva do usuário " + usuario.getNome() + " para o livro '" + livro.getTitulo() + "' cancelada devido ao empréstimo.");
        }

        System.out.println("Empréstimo do livro '" + livro.getTitulo() + "' (Exemplar " + exemplarParaEmprestimo.getCodigoExemplar() + ") para " + usuario.getNome() + " realizado com sucesso.");
        System.out.println("Data de Empréstimo: " + DateUtil.format(dataEmprestimo));
        System.out.println("Data Prevista de Devolução: " + DateUtil.format(dataPrevistaDevolucao));
    }
}