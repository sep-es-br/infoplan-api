package br.gov.es.infoplan.exception;

public class UsuarioSemPermissaoException extends RuntimeException {

    public UsuarioSemPermissaoException() {
        super("Usuário sem permissão.");
    }
}
