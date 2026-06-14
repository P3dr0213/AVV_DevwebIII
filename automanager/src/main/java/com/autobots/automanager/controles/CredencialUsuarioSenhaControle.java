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

import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaAtualizadorDTO;
import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaCadastrarDTO;
import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaExibirDTO;
import com.autobots.automanager.servicos.CredencialUsuarioSenhaServico;
import com.autobots.automanager.modeladores.CredencialUsuarioSenhaModelador;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RestController
@RequestMapping("/credenciais-usuario")
public class CredencialUsuarioSenhaControle {

    @Autowired
    private CredencialUsuarioSenhaServico servico;

    @Autowired
    private CredencialUsuarioSenhaModelador modelador;

    @GetMapping
    public ResponseEntity<CollectionModel<CredencialUsuarioSenhaExibirDTO>> obterTodos() {
        List<CredencialUsuarioSenhaExibirDTO> credenciais = servico.buscarTodos();
        List<CredencialUsuarioSenhaExibirDTO> modelos = credenciais.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(CredencialUsuarioSenhaControle.class).obterTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredencialUsuarioSenhaExibirDTO> obterPorId(@PathVariable Long id) {
        CredencialUsuarioSenhaExibirDTO dto = servico.buscarPorIdDTO(id);
        return ResponseEntity.ok(modelador.toModel(dto));
    }

    @PostMapping
    public ResponseEntity<CredencialUsuarioSenhaExibirDTO> cadastrar(@Valid @RequestBody CredencialUsuarioSenhaCadastrarDTO dto) {
        CredencialUsuarioSenhaExibirDTO criado = servico.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody CredencialUsuarioSenhaAtualizadorDTO dto) {
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