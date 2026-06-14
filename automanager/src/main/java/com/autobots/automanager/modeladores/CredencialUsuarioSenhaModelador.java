package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class CredencialUsuarioSenhaModelador implements RepresentationModelAssembler<CredencialUsuarioSenhaExibirDTO, CredencialUsuarioSenhaExibirDTO> {

    @Autowired
    private AdicionadorLink<CredencialUsuarioSenhaExibirDTO> adicionadorLink;

    @Override
    public CredencialUsuarioSenhaExibirDTO toModel(CredencialUsuarioSenhaExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        return dto;
    }
}