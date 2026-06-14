package com.autobots.automanager.modeladores;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;
import com.autobots.automanager.dto.Venda.VendaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class UsuarioModelador implements RepresentationModelAssembler<UsuarioExibirDTO, UsuarioExibirDTO> {

    @Autowired
    private AdicionadorLink<UsuarioExibirDTO> adicionadorLink;

    @Autowired
    private EnderecoModelador enderecoModelador;

    @Autowired
    private TelefoneModelador telefoneModelador;

    @Autowired
    private DocumentoModelador documentoModelador;

    @Autowired
    private EmailModelador emailModelador;

    @Autowired
    private AdicionadorLink<VeiculoExibirDTO> adicionadorLinkVeiculo;

    @Autowired
    private AdicionadorLink<CredencialQRExibirDTO> adicionadorLinkCredencial;

    @Autowired
    private AdicionadorLink<MercadoriaExibirDTO> adicionadorLinkMercadoria;

    @Autowired
    private AdicionadorLink<VendaExibirDTO> adicionadorLinkVenda;

    @Override
    public UsuarioExibirDTO toModel(UsuarioExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);

        if (dto.getEndereco() != null) {
            dto.setEndereco(enderecoModelador.toModel(dto.getEndereco()));
        }

        if (dto.getTelefones() != null) {
            dto.setTelefones(dto.getTelefones().stream()
                .map(telefoneModelador::toModel)
                .collect(Collectors.toSet()));
        }

        if (dto.getDocumentos() != null) {
            dto.setDocumentos(dto.getDocumentos().stream()
                .map(documentoModelador::toModel)
                .collect(Collectors.toSet()));
        }

        if (dto.getEmails() != null) {
            dto.setEmails(dto.getEmails().stream()
                .map(emailModelador::toModel)
                .collect(Collectors.toSet()));
        }

        if (dto.getVeiculos() != null) {
            dto.getVeiculos().forEach(adicionadorLinkVeiculo::adicionarLink);
        }

        if (dto.getCredenciaisCodigoBarra() != null) {
            dto.getCredenciaisCodigoBarra().forEach(adicionadorLinkCredencial::adicionarLink);
        }

        if (dto.getMercadorias() != null) {
            dto.getMercadorias().forEach(adicionadorLinkMercadoria::adicionarLink);
        }

        if (dto.getVendas() != null) {
            dto.getVendas().forEach(adicionadorLinkVenda::adicionarLink);
        }

        return dto;
    }
}