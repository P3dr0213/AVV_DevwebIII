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

import com.autobots.automanager.dto.Endereco.EnderecoAtualizadorDTO;
import com.autobots.automanager.dto.Endereco.EnderecoCadastroDTO;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.modeladores.EnderecoModelador;
import com.autobots.automanager.servicos.EnderecoServico;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE')")
@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {

    @Autowired
    private EnderecoServico servico;

    @Autowired
    private EnderecoModelador modelador;

    @PostMapping
    public ResponseEntity<EnderecoExibirDTO> criar(@Valid @RequestBody EnderecoCadastroDTO dto) {
        EnderecoExibirDTO enderecoCriado = servico.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(enderecoCriado));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EnderecoExibirDTO>> listar() {
        List<EnderecoExibirDTO> enderecos = servico.buscarTodos();
        List<EnderecoExibirDTO> modelos = enderecos.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(EnderecoControle.class).listar()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoExibirDTO> obterPorId(@PathVariable Long id) {
        EnderecoExibirDTO endereco = servico.buscarPorId(id);
        return ResponseEntity.ok(modelador.toModel(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoExibirDTO> editar(@PathVariable Long id, @Valid @RequestBody EnderecoAtualizadorDTO dto) {
        dto.setId(id);
        servico.atualizarporId(dto);
        EnderecoExibirDTO atualizado = servico.buscarPorId(id);
        return ResponseEntity.ok(modelador.toModel(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servico.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}