package com.autobots.automanager.dto.CredencialUsuarioSenha;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredencialUsuarioSenhaCadastrarDTO {
    @NotBlank(message = "O nome de usurio  obrigatrio")
    private String nomeUsuario;

    @NotBlank(message = "A senha  obrigatria")
    private String senha;
}