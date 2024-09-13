package me.dio.spring_pet_api.service;


import me.dio.spring_pet_api.domain.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> listarTodos();

    Cliente buscarPorId(Long id);

    Cliente salvar(Cliente cliente);

    Cliente atualizar(Long id, Cliente clienteAtualizado);

    void deletar(Long id);
}
