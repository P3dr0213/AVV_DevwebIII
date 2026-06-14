package com.autobots.automanager.dto.Endereco;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnderecoExibirDTO extends RepresentationModel<EnderecoExibirDTO> {
    private long id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String cep;
    private String informacoesAdicionais;
    private Long idCliente;
}
