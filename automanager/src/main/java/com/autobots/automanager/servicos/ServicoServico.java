package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Servico.ServicoAtualizadorDTO;
import com.autobots.automanager.dto.Servico.ServicoCadastrarDTO;
import com.autobots.automanager.dto.Servico.ServicoExibirDTO;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.ServicoRepositorio;

@Service
public class ServicoServico {

    @Autowired
    private ServicoRepositorio repositorio;

    public List<ServicoExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public ServicoExibirDTO buscarPorIdDTO(Long id) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Servio no encontrado"));
        return converterParaExibirDTO(servico);
    }

    public ServicoExibirDTO cadastrar(ServicoCadastrarDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setValor(dto.getValor());
        
        servico = repositorio.save(servico);
        return converterParaExibirDTO(servico);
    }

    public void atualizar(ServicoAtualizadorDTO dto) {
        Servico servico = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Servio no encontrado"));
        
        if (dto.getNome() != null) servico.setNome(dto.getNome());
        if (dto.getDescricao() != null) servico.setDescricao(dto.getDescricao());
        if (dto.getValor() != null) servico.setValor(dto.getValor());
        
        repositorio.save(servico);
    }

    public void excluir(Long id) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Servio no encontrado"));
        repositorio.delete(servico);
    }

    private ServicoExibirDTO converterParaExibirDTO(Servico entidade) {
        ServicoExibirDTO dto = new ServicoExibirDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDescricao(entidade.getDescricao());
        dto.setValor(entidade.getValor());
        return dto;
    }
}