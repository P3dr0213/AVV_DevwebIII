package com.autobots.automanager.dto.Mercadoria;

import java.util.Date;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MercadoriaExibirDTO extends RepresentationModel<MercadoriaExibirDTO> {
    private Long id;
    private String nome;
    private String descricao;
    private long quantidade;
    private double valor;
    private Date validade;
    private Date fabricao;
    private Date cadastro;
}