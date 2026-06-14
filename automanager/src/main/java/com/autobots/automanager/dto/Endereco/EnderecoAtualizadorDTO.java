package com.autobots.automanager.dto.Endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class EnderecoAtualizadorDTO {
    private Long id;

    @NotBlank(message = "O estado  obrigatrio")
    @Size(max = 100, message = "O estado deve ter no mximo 100 caracteres")
    private String estado;

    @NotBlank(message = "A cidade  obrigatria")
    private String cidade;

    @NotBlank(message = "O bairro  obrigatrio")
    private String bairro;

    @NotBlank(message = "A rua  obrigatria")
    private String rua;

    @NotBlank(message = "O nmero  obrigatrio")
    private String numero;

    @NotBlank(message = "O CEP  obrigatrio")
    private String cep;

    private String informacoesAdicionais;
}
