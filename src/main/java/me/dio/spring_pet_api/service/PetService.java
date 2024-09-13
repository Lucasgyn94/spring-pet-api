package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.model.Pet;

import java.util.List;

public interface PetService {
    List<Pet> listarTodos();

    Pet buscarPorId(Long id);

    Pet salvar(Pet pet);

    Pet atualizar(Long id, Pet petAtualizado);

    void deletar(Long id);
}
