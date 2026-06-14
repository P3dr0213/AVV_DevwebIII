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

import com.autobots.automanager.dto.Telefone.TelefoneAtualizadorDTO;
import com.autobots.automanager.dto.Telefone.TelefoneCadastroDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.modeladores.TelefoneModelador;
import com.autobots.automanager.servicos.TelefoneServico;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE')")
@RestController
@RequestMapping("/telefones")
public class TelefoneControle {

    @Autowired
    private TelefoneServico servico;

    @Autowired
    private TelefoneModelador modelador;

    @PostMapping
    public ResponseEntity<TelefoneExibirDTO> criar(@Valid @RequestBody TelefoneCadastroDTO dto) {
        TelefoneExibirDTO telefoneCriado = servico.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(telefoneCriado));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<TelefoneExibirDTO>> listar() {
        List<TelefoneExibirDTO> telefones = servico.buscarTodos();
        List<TelefoneExibirDTO> modelos = telefones.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(TelefoneControle.class).listar()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefoneExibirDTO> obterPorId(@PathVariable Long id) {
        TelefoneExibirDTO telefone = servico.buscarPorId(id);
        return ResponseEntity.ok(modelador.toModel(telefone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefoneExibirDTO> editar(@PathVariable Long id, @Valid @RequestBody TelefoneAtualizadorDTO dto) {
        dto.setId(id);
        servico.atualizar(dto);
        TelefoneExibirDTO atualizado = servico.buscarPorId(id);
        return ResponseEntity.ok(modelador.toModel(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servico.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}