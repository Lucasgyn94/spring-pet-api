package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.dto.VeterinarioDTO;
import me.dio.spring_pet_api.domain.model.Veterinario;

import java.util.List;

public interface VeterinarioService {
    List<VeterinarioDTO> listarTodos();

    VeterinarioDTO buscarPorId(Long id);

    VeterinarioDTO salvar(VeterinarioDTO veterinario);

    VeterinarioDTO atualizar(Long id, VeterinarioDTO veterinarioDtoAtualizado);

    void deletar(Long id);

}
