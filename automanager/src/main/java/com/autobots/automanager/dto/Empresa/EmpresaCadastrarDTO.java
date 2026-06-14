package com.autobots.automanager.dto.Empresa;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaCadastrarDTO {
    @NotBlank(message = "A razo social  obrigatria")
    private String razaoSocial;

    private String nomeFantasia;
}