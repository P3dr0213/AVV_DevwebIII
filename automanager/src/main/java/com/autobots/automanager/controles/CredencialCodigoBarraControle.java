package com.autobots.automanager.controles;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRAtualizadorDTO;
import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRCadastrarDTO;
import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.servicos.CredencialCodigoBarraServico;
import com.autobots.automanager.modeladores.CredencialCodigoBarraModelador;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RestController
@RequestMapping("/credenciais-codigo-barra")
public class CredencialCodigoBarraControle {

    @Autowired
    private CredencialCodigoBarraServico servico;

    @Autowired
    private CredencialCodigoBarraModelador modelador;

    @GetMapping
    public ResponseEntity<CollectionModel<CredencialQRExibirDTO>> obterTodos() {
        List<CredencialQRExibirDTO> credenciais = servico.buscarTodos();
        List<CredencialQRExibirDTO> modelos = credenciais.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(CredencialCodigoBarraControle.class).obterTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredencialQRExibirDTO> obterPorId(@PathVariable Long id) {
        CredencialQRExibirDTO dto = servico.buscarPorIdDTO(id);
        return ResponseEntity.ok(modelador.toModel(dto));
    }

    @PostMapping
    public ResponseEntity<CredencialQRExibirDTO> cadastrar(@Valid @RequestBody CredencialQRCadastrarDTO dto) {
        CredencialQRExibirDTO criado = servico.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody CredencialQRAtualizadorDTO dto) {
        dto.setId(id);
        servico.atualizar(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        servico.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}