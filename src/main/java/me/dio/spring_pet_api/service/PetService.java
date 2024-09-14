package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.domain.model.Pet;

import java.util.List;

public interface PetService {
    List<PetDTO> listarTodos();

    PetDTO buscarPorId(Long id);

    PetDTO salvar(PetDTO petDTO);

    PetDTO atualizar(Long id, PetDTO petDtoAtualizado);

    void deletar(Long id);
}
