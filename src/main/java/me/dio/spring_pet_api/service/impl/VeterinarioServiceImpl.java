package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.model.Veterinario;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
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
    public List<Veterinario> listarTodos() {
        return veterinarioRepository.findAll();
    }

    @Override
    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Veterinario nao encontrado com esse id"));
    }

    @Override
    public Veterinario salvar(Veterinario veterinario) {
        return veterinarioRepository.save(veterinario);
    }

    @Override
    public Veterinario atualizar(Long id, Veterinario veterinarioAtualizado) {
        Veterinario veterinario = buscarPorId(id);
        veterinario.setAgendamentos(veterinarioAtualizado.getAgendamentos());
        veterinario.setNome(veterinarioAtualizado.getNome());
        veterinario.setEspecialidade(veterinarioAtualizado.getEspecialidade());
        return veterinario;
    }

    @Override
    public void deletar(Long id) {
        veterinarioRepository.deleteById(id);
    }
}
