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

import com.autobots.automanager.dto.Documento.DocumentoAtualizadorDTO;
import com.autobots.automanager.dto.Documento.DocumentoCadastroDTO;
import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.modeladores.DocumentoModelador;
import com.autobots.automanager.servicos.DocumentoServico;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE')")
@RestController
@RequestMapping("/documentos")
public class DocumentoControle {

    @Autowired
    private DocumentoServico servico;

    @Autowired
    private DocumentoModelador modelador;

    @PostMapping
    public ResponseEntity<DocumentoExibirDTO> cadastrar(@Valid @RequestBody DocumentoCadastroDTO dto) {
        DocumentoExibirDTO documentoCriado = servico.cadastrarPorId(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelador.toModel(documentoCriado));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<DocumentoExibirDTO>> obterDocumentos() {
        List<DocumentoExibirDTO> documentos = servico.buscarTodos();
        List<DocumentoExibirDTO> modelos = documentos.stream()
                .map(modelador::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelos, 
                linkTo(methodOn(DocumentoControle.class).obterDocumentos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoExibirDTO> obterDocumento(@PathVariable Long id) {
        DocumentoExibirDTO documento = servico.buscarPorId(id);
        return ResponseEntity.ok(modelador.toModel(documento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoExibirDTO> atualizarDocumento(@PathVariable Long id, @Valid @RequestBody DocumentoAtualizadorDTO dto) {
        dto.setId(id);
        servico.atualizarPorId(dto);
        DocumentoExibirDTO atualizado = servico.buscarPorId(id);
        return ResponseEntity.ok(modelador.toModel(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDocumento(@PathVariable Long id) {
        servico.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}