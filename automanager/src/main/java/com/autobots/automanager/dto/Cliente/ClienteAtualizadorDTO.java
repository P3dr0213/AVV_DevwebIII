package com.autobots.automanager.dto.Cliente;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteAtualizadorDTO {
    @NotNull(message = "O id do cliente  obrigatrio")
    private Long id;

    @NotBlank(message = "O nome do cliente  obrigatrio")
    private String nome;

    @NotBlank(message = "O nome social  obrigatrio")
    private String nomeSocial;

    @NotNull(message = "A data de nascimento  obrigatria")
    private Date dataNascimento;
}
