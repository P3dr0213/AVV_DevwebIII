package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRAtualizadorDTO;
import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRCadastrarDTO;
import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.CredencialCodigoBarraRepositorio;

@Service
public class CredencialCodigoBarraServico {

    @Autowired
    private CredencialCodigoBarraRepositorio repositorio;

    public List<CredencialQRExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public CredencialQRExibirDTO buscarPorIdDTO(Long id) {
        CredencialCodigoBarra credencial = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Credencial no encontrada"));
        return converterParaExibirDTO(credencial);
    }

    public CredencialQRExibirDTO cadastrar(CredencialQRCadastrarDTO dto) {
        CredencialCodigoBarra credencial = new CredencialCodigoBarra();
        credencial.setCodigo(dto.getCodigo());
        credencial.setInativo(dto.getInativo());
        credencial.setCriacao(new Date()); // Campo herdado de Credencial
        credencial = repositorio.save(credencial);
        return converterParaExibirDTO(credencial);
    }

    public void atualizar(CredencialQRAtualizadorDTO dto) {
        CredencialCodigoBarra credencial = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Credencial no encontrada"));
        credencial.setCodigo(dto.getCodigo());
        credencial.setInativo(dto.getInativo());
        repositorio.save(credencial);
    }

    public void excluir(Long id) {
        CredencialCodigoBarra credencial = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Credencial no encontrada"));
        repositorio.delete(credencial);
    }

    private CredencialQRExibirDTO converterParaExibirDTO(CredencialCodigoBarra entidade) {
        CredencialQRExibirDTO dto = new CredencialQRExibirDTO();
        dto.setId(entidade.getId());
        dto.setCodigo(entidade.getCodigo());
        dto.setInativo(entidade.isInativo());
        return dto;
    }
}