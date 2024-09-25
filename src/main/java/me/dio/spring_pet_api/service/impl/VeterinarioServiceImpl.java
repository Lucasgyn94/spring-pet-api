package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.VeterinarioDTO;
import me.dio.spring_pet_api.domain.model.Veterinario;
import me.dio.spring_pet_api.domain.repository.VeterinarioRepository;
import me.dio.spring_pet_api.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VeterinarioDTO> listarTodos() {
        return veterinarioRepository.findAll().stream()
                .map(this::convertVeterinarioToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VeterinarioDTO buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do veterinário não pode ser nulo");
        }
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado com o ID: " + id));
        return convertVeterinarioToDto(veterinario);
    }

    @Override
    @Transactional
    public VeterinarioDTO salvar(VeterinarioDTO veterinarioDTO) {
        if (veterinarioDTO == null) {
            throw new IllegalArgumentException("VeterinarioDTO não pode ser nulo");
        }
        Veterinario veterinario = convertVeterinarioToEntity(veterinarioDTO);
        try {
            Veterinario veterinarioSalvo = veterinarioRepository.save(veterinario);
            return convertVeterinarioToDto(veterinarioSalvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o veterinário", e);
        }
    }

    @Override
    @Transactional
    public VeterinarioDTO atualizar(Long id, VeterinarioDTO veterinarioDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID do veterinário não pode ser nulo");
        }
        if (veterinarioDTO == null) {
            throw new IllegalArgumentException("VeterinarioDTO não pode ser nulo");
        }
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado com o ID: " + id));

        veterinario.setNome(veterinarioDTO.nome());
        veterinario.setEspecialidade(veterinarioDTO.especialidade());

        try {
            Veterinario veterinarioAtualizado = veterinarioRepository.save(veterinario);
            return convertVeterinarioToDto(veterinarioAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o veterinário", e);
        }
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do veterinário não pode ser nulo");
        }
        if (!veterinarioRepository.existsById(id)) {
            throw new RuntimeException("Veterinário não encontrado com o ID: " + id);
        }
        try {
            veterinarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o veterinário", e);
        }
    }

    private VeterinarioDTO convertVeterinarioToDto(Veterinario veterinario) {
        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNome(),
                veterinario.getEspecialidade()
        );
    }

    private Veterinario convertVeterinarioToEntity(VeterinarioDTO veterinarioDTO) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(veterinarioDTO.nome());
        veterinario.setEspecialidade(veterinarioDTO.especialidade());
        return veterinario;
    }
}
