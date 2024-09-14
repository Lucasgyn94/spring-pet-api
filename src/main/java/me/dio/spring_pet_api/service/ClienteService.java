package me.dio.spring_pet_api.service;


import me.dio.spring_pet_api.domain.dto.ClienteDTO;
import me.dio.spring_pet_api.domain.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listarTodos();

    ClienteDTO buscarPorId(Long id);

    ClienteDTO salvar(ClienteDTO clienteDTO);

    ClienteDTO atualizar(Long id, ClienteDTO clienteDtoAtualizado);

    void deletar(Long id);
}
