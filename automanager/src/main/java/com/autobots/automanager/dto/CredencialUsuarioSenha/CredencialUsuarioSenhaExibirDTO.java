package com.autobots.automanager.dto.CredencialUsuarioSenha;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CredencialUsuarioSenhaExibirDTO extends RepresentationModel<CredencialUsuarioSenhaExibirDTO> {
    private Long id;
    private String nomeUsuario;
    private boolean inativo;
}