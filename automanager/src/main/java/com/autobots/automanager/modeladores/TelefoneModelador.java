package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;
import com.autobots.automanager.modelo.Telefone.AdicionadorLinkTelefone;

@Component
public class TelefoneModelador implements RepresentationModelAssembler<TelefoneExibirDTO, TelefoneExibirDTO> {

    @Autowired
    private AdicionadorLink<TelefoneExibirDTO> adicionadorLink;

    @Override
    public TelefoneExibirDTO toModel(TelefoneExibirDTO telefoneDto) {
        adicionadorLink.adicionarLink(telefoneDto);
        return telefoneDto;
    }
}
