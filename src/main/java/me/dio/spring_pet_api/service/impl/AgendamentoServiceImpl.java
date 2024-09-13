package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.model.Agendamento;
import me.dio.spring_pet_api.domain.repository.AgendamentoRepository;
import me.dio.spring_pet_api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Override
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    @Override
    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("agendamento nao encontrado!"));
    }

    @Override
    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    @Override
    public Agendamento atualizar(Long id, Agendamento agendamentoAtualizado) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setPet(agendamentoAtualizado.getPet());
        agendamento.setVeterinario(agendamentoAtualizado.getVeterinario());
        agendamento.setDataHora(agendamentoAtualizado.getDataHora());

        return agendamento;
    }

    @Override
    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
