package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaAtualizadorDTO;
import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaCadastrarDTO;
import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaExibirDTO;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepositorio;

@Service
public class CredencialUsuarioSenhaServico {

    @Autowired
    private CredencialUsuarioSenhaRepositorio repositorio;

    public List<CredencialUsuarioSenhaExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public CredencialUsuarioSenhaExibirDTO buscarPorIdDTO(Long id) {
        CredencialUsuarioSenha credencial = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Credencial de usurio no encontrada"));
        return converterParaExibirDTO(credencial);
    }

    public CredencialUsuarioSenhaExibirDTO cadastrar(CredencialUsuarioSenhaCadastrarDTO dto) {
        CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
        credencial.setNomeUsuario(dto.getNomeUsuario());
        credencial.setSenha(dto.getSenha());
        credencial.setCriacao(new Date()); // Campo herdado de Credencial
        credencial.setInativo(false);
        credencial = repositorio.save(credencial);
        return converterParaExibirDTO(credencial);
    }

    public void atualizar(CredencialUsuarioSenhaAtualizadorDTO dto) {
        CredencialUsuarioSenha credencial = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Credencial no encontrada"));
        
        if (dto.getNomeUsuario() != null) credencial.setNomeUsuario(dto.getNomeUsuario());
        if (dto.getSenha() != null) credencial.setSenha(dto.getSenha());
        if (dto.getInativo() != null) credencial.setInativo(dto.getInativo());
        
        repositorio.save(credencial);
    }

    public void excluir(Long id) {
        CredencialUsuarioSenha credencial = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Credencial no encontrada"));
        repositorio.delete(credencial);
    }

    private CredencialUsuarioSenhaExibirDTO converterParaExibirDTO(CredencialUsuarioSenha entidade) {
        CredencialUsuarioSenhaExibirDTO dto = new CredencialUsuarioSenhaExibirDTO();
        dto.setId(entidade.getId());
        dto.setNomeUsuario(entidade.getNomeUsuario());
        dto.setInativo(entidade.isInativo());
        return dto;
    }
}