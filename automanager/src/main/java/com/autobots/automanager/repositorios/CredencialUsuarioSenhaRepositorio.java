package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;

public interface CredencialUsuarioSenhaRepositorio extends JpaRepository<CredencialUsuarioSenha, Long> {

    java.util.Optional<CredencialUsuarioSenha> findByNomeUsuario(String nomeUsuario);
}