package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class MercadoriaModelador implements RepresentationModelAssembler<MercadoriaExibirDTO, MercadoriaExibirDTO> {

    @Autowired
    private AdicionadorLink<MercadoriaExibirDTO> adicionadorLink;

    @Override
    public MercadoriaExibirDTO toModel(MercadoriaExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        return dto;
    }
}