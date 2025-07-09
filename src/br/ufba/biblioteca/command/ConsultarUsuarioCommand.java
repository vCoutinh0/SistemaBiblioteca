package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Emprestimo;
import br.ufba.biblioteca.model.Reserva;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;
import br.ufba.biblioteca.util.DateUtil;

public class ConsultarUsuarioCommand implements Command {
    private Repositorio repositorio;

    public ConsultarUsuarioCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: usu <codigoUsuario>");
            return;
        }

        int codigoUsuario = Integer.parseInt(args[0]);
        Usuario usuario = repositorio.buscarUsuarioPorCodigo(codigoUsuario);

        if (usuario == null) {
            System.out.println("Erro: Usuário com código " + codigoUsuario + " não encontrado.");
            return;
        }

        System.out.println("--- Informações do Usuário ---");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Código: " + usuario.getCodigo());
        System.out.println("-----------------------------");

        System.out.println("Empréstimos Correntes (" + usuario.getEmprestimosCorrentes().size() + "):");
        if (usuario.getEmprestimosCorrentes().isEmpty()) {
            System.out.println("  Nenhum empréstimo em curso.");
        } else {
            for (Emprestimo e : usuario.getEmprestimosCorrentes()) {
                String statusAtraso = e.estaEmAtraso() ? " (ATRASADO)" : "";
                System.out.println("  - Livro: '" + e.getLivro().getTitulo() + "' (Exemplar " + e.getExemplar().getCodigoExemplar() + ")");
                System.out.println("    Data Empréstimo: " + DateUtil.format(e.getDataEmprestimo()));
                System.out.println("    Status: Em Curso" + statusAtraso);
                System.out.println("    Previsão Devolução: " + DateUtil.format(e.getDataPrevistaDevolucao()));
            }
        }
        System.out.println("-----------------------------");

        System.out.println("Histórico de Empréstimos (" + usuario.getHistoricoEmprestimos().size() + "):");
        if (usuario.getHistoricoEmprestimos().isEmpty()) {
            System.out.println("  Nenhum empréstimo finalizado.");
        } else {
            for (Emprestimo e : usuario.getHistoricoEmprestimos()) {
                System.out.println("  - Livro: '" + e.getLivro().getTitulo() + "' (Exemplar " + e.getExemplar().getCodigoExemplar() + ")");
                System.out.println("    Data Empréstimo: " + DateUtil.format(e.getDataEmprestimo()));
                System.out.println("    Status: Finalizado");
                System.out.println("    Data Devolução: " + DateUtil.format(e.getDataDevolucaoRealizada()));
            }
        }
        System.out.println("-----------------------------");

        System.out.println("Reservas (" + usuario.getReservas().size() + "):");
        if (usuario.getReservas().isEmpty()) {
            System.out.println("  Nenhuma reserva.");
        } else {
            for (Reserva r : usuario.getReservas()) {
                System.out.println("  - Livro: '" + r.getLivro().getTitulo() + "'");
                System.out.println("    Data da Reserva: " + DateUtil.format(r.getDataReserva()));
            }
        }
        System.out.println("-----------------------------");
    }
}