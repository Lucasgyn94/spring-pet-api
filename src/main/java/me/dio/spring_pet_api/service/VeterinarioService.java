package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.model.Veterinario;

import java.util.List;

public interface VeterinarioService {
    List<Veterinario> listarTodos();

    Veterinario buscarPorId(Long id);

    Veterinario salvar(Veterinario veterinario);

    Veterinario atualizar(Long id, Veterinario veterinarioAtualizado);

    void deletar(Long id);

}
