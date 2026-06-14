package com.autobots.automanager.dto.Empresa;

import java.util.Set;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.dto.Servico.ServicoExibirDTO;
import lombok.Data;

@Data
public class VendasPeriodoExibirDTO {
    private Set<MercadoriaExibirDTO> pecasVendidas;
    private Set<ServicoExibirDTO> servicosVendidos;
}
