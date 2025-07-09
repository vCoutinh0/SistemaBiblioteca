package br.ufba.biblioteca.command;

public class SairCommand implements Command {

    @Override
    public void execute(String[] args) {
        System.out.println("Saindo do Sistema de Biblioteca.");
    }
}