package com.autobots.automanager.dto.Mercadoria;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MercadoriaAtualizadorDTO {
    @NotNull(message = "O id da mercadoria  obrigatrio")
    private Long id;

    private String nome;
    private String descricao;
    private Long quantidade;
    private Double valor;
    private Date validade;
    private Date fabricao;
}