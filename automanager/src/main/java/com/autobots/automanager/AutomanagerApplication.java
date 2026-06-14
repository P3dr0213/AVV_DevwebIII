package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

@SpringBootApplication
public class AutomanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomanagerApplication.class, args);
    }

    @Component
    public static class Runner implements ApplicationRunner {

        @Autowired
        private UsuarioRepositorio usuarioRepositorio;

        @Autowired
        private VeiculoRepositorio veiculoRepositorio;

        @Override
        @Transactional
        public void run(ApplicationArguments args) throws Exception {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // --- ADMINISTRADOR ---
            Usuario admin = new Usuario();
            admin.setNome("Administrador do Sistema");
            admin.setNomeSocial("Admin");
            admin.getPerfis().add(PerfilUsuario.ADMINISTRADOR);

            CredencialUsuarioSenha credAdmin = new CredencialUsuarioSenha();
            credAdmin.setNomeUsuario("admin");
            credAdmin.setSenha(encoder.encode("admin123"));
            credAdmin.setInativo(false);
            credAdmin.setCriacao(new Date());
            admin.getCredenciais().add(credAdmin);

            usuarioRepositorio.save(admin);

            // --- GERENTE ---
            Usuario gerente = new Usuario();
            gerente.setNome("Isabel Cristina de Braganca");
            gerente.setNomeSocial("Princesa Isabel");
            gerente.getPerfis().add(PerfilUsuario.GERENTE);

            CredencialUsuarioSenha credGerente = new CredencialUsuarioSenha();
            credGerente.setNomeUsuario("gerente");
            credGerente.setSenha(encoder.encode("gerente123"));
            credGerente.setInativo(false);
            credGerente.setCriacao(new Date());
            gerente.getCredenciais().add(credGerente);

            usuarioRepositorio.save(gerente);

            // --- VENDEDOR ---
            Usuario vendedor = new Usuario();
            vendedor.setNome("Jose Bonifacio de Andrada");
            vendedor.setNomeSocial("Patriarca");
            vendedor.getPerfis().add(PerfilUsuario.VENDEDOR);

            CredencialUsuarioSenha credVendedor = new CredencialUsuarioSenha();
            credVendedor.setNomeUsuario("vendedor");
            credVendedor.setSenha(encoder.encode("vendedor123"));
            credVendedor.setInativo(false);
            credVendedor.setCriacao(new Date());
            vendedor.getCredenciais().add(credVendedor);

            usuarioRepositorio.save(vendedor);

            // --- CLIENTE ---
            Usuario cliente = new Usuario();
            cliente.setNome("Pedro Alcantara de Braganca e Bourbon");
            cliente.setNomeSocial("Dom Pedro");
            cliente.getPerfis().add(PerfilUsuario.CLIENTE);

            Telefone telCliente = new Telefone();
            telCliente.setDdd("21");
            telCliente.setNumero("981234576");
            cliente.getTelefones().add(telCliente);

            Endereco endCliente = new Endereco();
            endCliente.setEstado("Rio de Janeiro");
            endCliente.setCidade("Rio de Janeiro");
            endCliente.setBairro("Copacabana");
            endCliente.setRua("Avenida Atlntica");
            endCliente.setNumero("1702");
            endCliente.setCodigoPostal("22021001");
            endCliente.setInformacoesAdicionais("Hotel Copacabana Palace");
            cliente.setEndereco(endCliente);

            Documento rgCliente = new Documento();
            rgCliente.setTipo("RG");
            rgCliente.setNumero("1500");
            cliente.getDocumentos().add(rgCliente);

            Documento cpfCliente = new Documento();
            cpfCliente.setTipo("CPF");
            cpfCliente.setNumero("00000000001");
            cliente.getDocumentos().add(cpfCliente);

            Email emailCliente = new Email();
            emailCliente.setEndereco("dompedro@imperio.br");
            cliente.getEmails().add(emailCliente);

            CredencialUsuarioSenha credCliente = new CredencialUsuarioSenha();
            credCliente.setNomeUsuario("cliente");
            credCliente.setSenha(encoder.encode("cliente123"));
            credCliente.setInativo(false);
            credCliente.setCriacao(new Date());
            cliente.getCredenciais().add(credCliente);

            Usuario clienteSalvo = usuarioRepositorio.save(cliente);

            // Veculo vinculado ao cliente
            Veiculo veiculo = new Veiculo();
            veiculo.setTipo(TipoVeiculo.SEDA);
            veiculo.setModelo("Mercedes-Benz Classe S");
            veiculo.setPlaca("RIO1234");
            veiculo.setProprietario(clienteSalvo);

            Veiculo veiculoSalvo = veiculoRepositorio.save(veiculo);
            clienteSalvo.getVeiculos().add(veiculoSalvo);
            usuarioRepositorio.save(clienteSalvo);

        }
    }
}
