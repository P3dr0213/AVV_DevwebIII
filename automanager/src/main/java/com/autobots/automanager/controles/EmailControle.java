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

import com.autobots.automanager.dto.Email.EmailAtualizadorDTO;
import com.autobots.automanager.dto.Email.EmailCadastroDTO;
import com.autobots.automanager.dto.Email.EmailExibirDTO;
import com.autobots.automanager.servicos.EmailServico;
import com.autobots.automanager.modeladores.EmailModelador;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE')")
@RestController
@RequestMapping("/emails")
public class EmailControle {

    @Autowired
    private EmailServico servico;

    @Autowired
    private EmailModelador modelador;

    @GetMapping
    public ResponseEntity<CollectionModel<EmailExibirDTO>> obterTodos() {
        List<EmailExibirDTO> emails = servico.buscarTodos();
        List<EmailExibirDTO> modelos = emails.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(EmailControle.class).obterTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailExibirDTO> obterPorId(@PathVariable Long id) {
        EmailExibirDTO dto = servico.buscarPorIdDTO(id);
        return ResponseEntity.ok(modelador.toModel(dto));
    }

    @PostMapping
    public ResponseEntity<EmailExibirDTO> cadastrar(@Valid @RequestBody EmailCadastroDTO dto) {
        EmailExibirDTO criado = servico.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody EmailAtualizadorDTO dto) {
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