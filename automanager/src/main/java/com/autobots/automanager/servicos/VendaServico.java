package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Venda.VendaAtualizadorDTO;
import com.autobots.automanager.dto.Venda.VendaCadastrarDTO;
import com.autobots.automanager.dto.Venda.VendaExibirDTO;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaServico {

    @Autowired
    private VendaRepositorio repositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;
    @Autowired
    private ServicoRepositorio servicoRepositorio;

    public List<VendaExibirDTO> buscarTodos() {
        return repositorio.findAll().stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public VendaExibirDTO buscarPorIdDTO(Long id) {
        Venda venda = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Venda no encontrada"));
        return converterParaExibirDTO(venda);
    }

    public VendaExibirDTO cadastrar(VendaCadastrarDTO dto) {
        Venda venda = new Venda();
        venda.setIdentificacao(dto.getIdentificacao());
        venda.setCadastro(new Date());

        venda.setCliente(usuarioRepositorio.findById(dto.getClienteId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado")));
        
        venda.setFuncionario(usuarioRepositorio.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionrio no encontrado")));

        if (dto.getVeiculoId() != null) {
            venda.setVeiculo(veiculoRepositorio.findById(dto.getVeiculoId()).orElse(null));
        }

        if (dto.getMercadoriasIds() != null) {
            venda.getMercadorias().addAll(mercadoriaRepositorio.findAllById(dto.getMercadoriasIds()));
        }

        if (dto.getServicosIds() != null) {
            venda.getServicos().addAll(servicoRepositorio.findAllById(dto.getServicosIds()));
        }

        venda = repositorio.save(venda);

        venda.getCliente().getVendas().add(venda);
        usuarioRepositorio.save(venda.getCliente());

        venda.getFuncionario().getVendas().add(venda);
        usuarioRepositorio.save(venda.getFuncionario());

        return converterParaExibirDTO(venda);
    }

    public void atualizar(VendaAtualizadorDTO dto) {
        Venda venda = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Venda no encontrada"));

        if (dto.getIdentificacao() != null) venda.setIdentificacao(dto.getIdentificacao());

        if (dto.getClienteId() != null) {
            venda.setCliente(usuarioRepositorio.findById(dto.getClienteId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado")));
        }

        if (dto.getFuncionarioId() != null) {
            venda.setFuncionario(usuarioRepositorio.findById(dto.getFuncionarioId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionrio no encontrado")));
        }

        if (dto.getVeiculoId() != null) {
            venda.setVeiculo(veiculoRepositorio.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Veculo no encontrado")));
        }

        if (dto.getMercadoriasIds() != null) {
            venda.setMercadorias(new HashSet<>(mercadoriaRepositorio.findAllById(dto.getMercadoriasIds())));
        }

        if (dto.getServicosIds() != null) {
            venda.setServicos(new HashSet<>(servicoRepositorio.findAllById(dto.getServicosIds())));
        }

        repositorio.save(venda);
    }

    public void excluir(Long id) {
        Venda venda = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Venda no encontrada"));
        repositorio.delete(venda);
    }

    private VendaExibirDTO converterParaExibirDTO(Venda entidade) {
        VendaExibirDTO dto = new VendaExibirDTO();
        dto.setId(entidade.getId());
        dto.setIdentificacao(entidade.getIdentificacao());
        dto.setCadastro(entidade.getCadastro());
        // Os objetos completos (Cliente, Funcionario, etc) sero processados pelo Modelador
        return dto;
    }
}