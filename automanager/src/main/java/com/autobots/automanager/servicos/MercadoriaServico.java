package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Mercadoria.MercadoriaAtualizadorDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaCadastrarDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;

@Service
public class MercadoriaServico {

    @Autowired
    private MercadoriaRepositorio repositorio;

    public List<MercadoriaExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public MercadoriaExibirDTO buscarPorIdDTO(Long id) {
        Mercadoria mercadoria = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Mercadoria no encontrada"));
        return converterParaExibirDTO(mercadoria);
    }

    public MercadoriaExibirDTO cadastrar(MercadoriaCadastrarDTO dto) {
        Mercadoria mercadoria = new Mercadoria();
        mercadoria.setNome(dto.getNome());
        mercadoria.setDescricao(dto.getDescricao());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setValor(dto.getValor());
        mercadoria.setValidade(dto.getValidade());
        mercadoria.setFabricao(dto.getFabricao());
        mercadoria.setCadastro(new Date());
        
        mercadoria = repositorio.save(mercadoria);
        return converterParaExibirDTO(mercadoria);
    }

    public void atualizar(MercadoriaAtualizadorDTO dto) {
        Mercadoria mercadoria = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Mercadoria no encontrada"));
        
        if (dto.getNome() != null) mercadoria.setNome(dto.getNome());
        if (dto.getDescricao() != null) mercadoria.setDescricao(dto.getDescricao());
        if (dto.getQuantidade() != null) mercadoria.setQuantidade(dto.getQuantidade());
        if (dto.getValor() != null) mercadoria.setValor(dto.getValor());
        if (dto.getValidade() != null) mercadoria.setValidade(dto.getValidade());
        if (dto.getFabricao() != null) mercadoria.setFabricao(dto.getFabricao());
        
        repositorio.save(mercadoria);
    }

    public void excluir(Long id) {
        Mercadoria mercadoria = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Mercadoria no encontrada"));
        repositorio.delete(mercadoria);
    }

    private MercadoriaExibirDTO converterParaExibirDTO(Mercadoria entidade) {
        MercadoriaExibirDTO dto = new MercadoriaExibirDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDescricao(entidade.getDescricao());
        dto.setQuantidade(entidade.getQuantidade());
        dto.setValor(entidade.getValor());
        dto.setValidade(entidade.getValidade());
        dto.setFabricao(entidade.getFabricao());
        dto.setCadastro(entidade.getCadastro());
        return dto;
    }
}