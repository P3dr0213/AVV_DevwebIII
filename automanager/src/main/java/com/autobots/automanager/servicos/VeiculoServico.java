package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoAtualizadorDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoCadastrarDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

@Service
public class VeiculoServico {

    @Autowired
    private VeiculoRepositorio repositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<VeiculoExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public VeiculoExibirDTO buscarPorIdDTO(Long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Veculo no encontrado"));
        return converterParaExibirDTO(veiculo);
    }

    public VeiculoExibirDTO cadastrar(VeiculoCadastrarDTO dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setTipo(dto.getTipo());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPlaca(dto.getPlaca());

        if (dto.getProprietarioId() != null) {
            Usuario proprietario = usuarioRepositorio.findById(dto.getProprietarioId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Proprietrio no encontrado"));
            veiculo.setProprietario(proprietario);
            veiculo = repositorio.save(veiculo);
            proprietario.getVeiculos().add(veiculo);
            usuarioRepositorio.save(proprietario);
        } else {
            veiculo = repositorio.save(veiculo);
        }

        return converterParaExibirDTO(veiculo);
    }

    public void atualizar(VeiculoAtualizadorDTO dto) {
        Veiculo veiculo = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Veculo no encontrado"));

        if (dto.getTipo() != null) veiculo.setTipo(dto.getTipo());
        if (dto.getModelo() != null) veiculo.setModelo(dto.getModelo());
        if (dto.getPlaca() != null) veiculo.setPlaca(dto.getPlaca());
        
        if (dto.getProprietarioId() != null) {
            Usuario novoProprietario = usuarioRepositorio.findById(dto.getProprietarioId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Proprietrio no encontrado"));

            Usuario proprietarioAnterior = veiculo.getProprietario();
            if (proprietarioAnterior != null && !proprietarioAnterior.getId().equals(novoProprietario.getId())) {
                proprietarioAnterior.getVeiculos().remove(veiculo);
                usuarioRepositorio.save(proprietarioAnterior);
            }

            veiculo.setProprietario(novoProprietario);
            Veiculo veiculoSalvo = repositorio.save(veiculo);
            novoProprietario.getVeiculos().add(veiculoSalvo);
            usuarioRepositorio.save(novoProprietario);
        } else {
            repositorio.save(veiculo);
        }
    }

    public void excluir(Long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Veculo no encontrado"));
        repositorio.delete(veiculo);
    }

    private VeiculoExibirDTO converterParaExibirDTO(Veiculo entidade) {
        VeiculoExibirDTO dto = new VeiculoExibirDTO();
        dto.setId(entidade.getId());
        dto.setTipo(entidade.getTipo());
        dto.setModelo(entidade.getModelo());
        dto.setPlaca(entidade.getPlaca());

        if (entidade.getProprietario() != null) {
            UsuarioExibirDTO proprietarioDTO = new UsuarioExibirDTO();
            proprietarioDTO.setId(entidade.getProprietario().getId());
            proprietarioDTO.setNome(entidade.getProprietario().getNome());
            proprietarioDTO.setNomeSocial(entidade.getProprietario().getNomeSocial());
            proprietarioDTO.setPerfis(entidade.getProprietario().getPerfis());
            dto.setProprietario(proprietarioDTO);
        }

        return dto;
    }
}