package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.model.Servico;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl implements ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    @Override
    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet com esse id nao encontrado!"));
    }

    @Override
    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
    public Servico atualizar(Long id, Servico servicoAtualizado) {
        Servico servico = buscarPorId(id);
        servico.setDescricao(servicoAtualizado.getDescricao());
        servico.setPets(servicoAtualizado.getPets());
        servico.setPreco(servicoAtualizado.getPreco());

        return servico;
    }

    @Override
    public void deletar(Long id) {
        servicoRepository.deleteById(id);
    }
}
