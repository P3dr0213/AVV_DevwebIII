package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Email.EmailExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class EmailModelador implements RepresentationModelAssembler<EmailExibirDTO, EmailExibirDTO> {

    @Autowired
    private AdicionadorLink<EmailExibirDTO> adicionadorLink;

    @Override
    public EmailExibirDTO toModel(EmailExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        return dto;
    }
}