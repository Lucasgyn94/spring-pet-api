package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.domain.repository.ClienteRepository;
import me.dio.spring_pet_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setPets(clienteAtualizado.getPets());
        cliente.setTelefone(clienteAtualizado.getTelefone());

        return cliente;
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
