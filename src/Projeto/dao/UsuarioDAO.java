package Projeto.dao;

import Projeto.model.Usuario;
import java.sql.SQLException;

public interface UsuarioDAO {
    boolean cadastrarUsuario(Usuario var1) throws SQLException;

    Usuario buscarUsuarioPorEmail(String var1) throws SQLException;

    boolean atualizarUsuario(Usuario var1) throws SQLException;

    boolean emailJaCadastrado(String var1) throws SQLException;
}