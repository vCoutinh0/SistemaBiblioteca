package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Exemplar;
import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Reserva;
import br.ufba.biblioteca.model.StatusExemplar;
import br.ufba.biblioteca.repository.Repositorio;
import br.ufba.biblioteca.util.DateUtil;

public class ConsultarLivroCommand implements Command {
    private Repositorio repositorio;

    public ConsultarLivroCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: liv <codigoLivro>");
            return;
        }

        int codigoLivro = Integer.parseInt(args[0]);
        Livro livro = repositorio.buscarLivroPorCodigo(codigoLivro);

        if (livro == null) {
            System.out.println("Erro: Livro com código " + codigoLivro + " não encontrado.");
            return;
        }

        System.out.println("--- Informações do Livro ---");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Editora: " + livro.getEditora());
        System.out.println("Autores: " + String.join(", ", livro.getAutores()));
        System.out.println("Edição: " + livro.getEdicao());
        System.out.println("Ano de Publicação: " + livro.getAnoPublicacao());
        System.out.println("---------------------------");

        System.out.println("Quantidade de Reservas: " + livro.getQuantidadeReservas());
        if (!livro.getReservas().isEmpty()) {
            System.out.println("Reservas:");
            for (Reserva reserva : livro.getReservas()) {
                System.out.println("  - " + reserva.getUsuario().getNome() + " (Código: " + reserva.getUsuario().getCodigo() + ") em " + DateUtil.format(reserva.getDataReserva()));
            }
        }
        System.out.println("---------------------------");

        System.out.println("Exemplares:");
        if (livro.getExemplares().isEmpty()) {
            System.out.println("  Nenhum exemplar cadastrado para este livro.");
        } else {
            for (Exemplar exemplar : livro.getExemplares()) {
                System.out.println("  Código Exemplar: " + exemplar.getCodigoExemplar());
                System.out.println("  Status: " + (exemplar.getStatus() == StatusExemplar.DISPONIVEL ? "Disponível" : "Emprestado"));
                if (exemplar.getStatus() == StatusExemplar.EMPRESTADO && exemplar.getEmprestimoCorrente() != null) {
                    System.out.println("    Emprestado para: " + exemplar.getEmprestimoCorrente().getUsuario().getNome() + " (Código: " + exemplar.getEmprestimoCorrente().getUsuario().getCodigo() + ")");
                    System.out.println("    Data Empréstimo: " + DateUtil.format(exemplar.getEmprestimoCorrente().getDataEmprestimo()));
                    System.out.println("    Previsão Devolução: " + DateUtil.format(exemplar.getEmprestimoCorrente().getDataPrevistaDevolucao()));
                }
            }
        }
        System.out.println("---------------------------");
    }
}