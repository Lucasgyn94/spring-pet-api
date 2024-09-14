package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.VeterinarioDTO;
import me.dio.spring_pet_api.domain.repository.VeterinarioRepository;
import me.dio.spring_pet_api.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;


    @Override
    public List<VeterinarioDTO> listarTodos() {
        return List.of();
    }

    @Override
    public VeterinarioDTO buscarPorId(Long id) {
        return null;
    }

    @Override
    public VeterinarioDTO salvar(VeterinarioDTO veterinario) {
        return null;
    }

    @Override
    public VeterinarioDTO atualizar(Long id, VeterinarioDTO veterinarioDtoAtualizado) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
