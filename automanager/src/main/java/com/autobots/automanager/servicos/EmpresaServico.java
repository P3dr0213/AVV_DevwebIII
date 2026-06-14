package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Empresa.EmpresaAtualizadorDTO;
import com.autobots.automanager.dto.Empresa.EmpresaCadastrarDTO;
import com.autobots.automanager.dto.Empresa.EmpresaExibirDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@Service
public class EmpresaServico {

    @Autowired
    private EmpresaRepositorio repositorio;

    public List<EmpresaExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public EmpresaExibirDTO buscarPorIdDTO(Long id) {
        Empresa empresa = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa no encontrada"));
        return converterParaExibirDTO(empresa);
    }

    public EmpresaExibirDTO cadastrar(EmpresaCadastrarDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setCadastro(new Date());
        empresa = repositorio.save(empresa);
        return converterParaExibirDTO(empresa);
    }

    public void atualizar(EmpresaAtualizadorDTO dto) {
        Empresa empresa = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa no encontrada"));
        
        if (dto.getRazaoSocial() != null) empresa.setRazaoSocial(dto.getRazaoSocial());
        if (dto.getNomeFantasia() != null) empresa.setNomeFantasia(dto.getNomeFantasia());
        
        repositorio.save(empresa);
    }

    public void excluir(Long id) {
        Empresa empresa = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa no encontrada"));
        repositorio.delete(empresa);
    }

    private EmpresaExibirDTO converterParaExibirDTO(Empresa entidade) {
        EmpresaExibirDTO dto = new EmpresaExibirDTO();
        dto.setId(entidade.getId());
        dto.setRazaoSocial(entidade.getRazaoSocial());
        dto.setNomeFantasia(entidade.getNomeFantasia());
        dto.setCadastro(entidade.getCadastro());
        // Nota: Mapeamento de colees (telefones, endereos) deve ser feito via modelador ou servio especfico
        return dto;
    }
}