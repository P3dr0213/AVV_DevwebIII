package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Email.EmailAtualizadorDTO;
import com.autobots.automanager.dto.Email.EmailCadastroDTO;
import com.autobots.automanager.dto.Email.EmailExibirDTO;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.EmailRepositorio;

@Service
public class EmailServico {

    @Autowired
    private EmailRepositorio repositorio;

    public List<EmailExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public EmailExibirDTO buscarPorIdDTO(Long id) {
        Email email = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("E-mail no encontrado"));
        return converterParaExibirDTO(email);
    }

    public EmailExibirDTO cadastrar(EmailCadastroDTO dto) {
        Email email = new Email();
        email.setEndereco(dto.getEndereco());
        email = repositorio.save(email);
        return converterParaExibirDTO(email);
    }

    public void atualizar(EmailAtualizadorDTO dto) {
        Email email = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("E-mail no encontrado"));
        if (dto.getEndereco() != null) {
            email.setEndereco(dto.getEndereco());
        }
        repositorio.save(email);
    }

    public void excluir(Long id) {
        Email email = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("E-mail no encontrado"));
        repositorio.delete(email);
    }

    private EmailExibirDTO converterParaExibirDTO(Email entidade) {
        EmailExibirDTO dto = new EmailExibirDTO();
        dto.setId(entidade.getId());
        dto.setEndereco(entidade.getEndereco());
        return dto;
    }
}