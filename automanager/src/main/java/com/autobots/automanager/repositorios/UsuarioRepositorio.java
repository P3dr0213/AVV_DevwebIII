package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u, CredencialUsuarioSenha c WHERE c MEMBER OF u.credenciais AND c.nomeUsuario = :nomeUsuario")
    Optional<Usuario> findByCredencialNomeUsuario(@Param("nomeUsuario") String nomeUsuario);
}