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

import com.autobots.automanager.dto.Empresa.EmpresaAtualizadorDTO;
import com.autobots.automanager.dto.Empresa.EmpresaCadastrarDTO;
import com.autobots.automanager.dto.Empresa.EmpresaExibirDTO;
import com.autobots.automanager.servicos.EmpresaServico;
import com.autobots.automanager.modeladores.EmpresaModelador;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RestController
@RequestMapping("/empresas")
public class EmpresaControle {

    @Autowired
    private EmpresaServico servico;

    @Autowired
    private EmpresaModelador modelador;

    @GetMapping
    public ResponseEntity<CollectionModel<EmpresaExibirDTO>> obterTodos() {
        List<EmpresaExibirDTO> empresas = servico.buscarTodos();
        List<EmpresaExibirDTO> modelos = empresas.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(EmpresaControle.class).obterTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaExibirDTO> obterPorId(@PathVariable Long id) {
        EmpresaExibirDTO dto = servico.buscarPorIdDTO(id);
        return ResponseEntity.ok(modelador.toModel(dto));
    }

    @PostMapping
    public ResponseEntity<EmpresaExibirDTO> cadastrar(@Valid @RequestBody EmpresaCadastrarDTO dto) {
        EmpresaExibirDTO criado = servico.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody EmpresaAtualizadorDTO dto) {
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