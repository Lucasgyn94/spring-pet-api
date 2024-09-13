package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.model.Agendamento;

import java.util.List;

public interface AgendamentoService {
    List<Agendamento> listarTodos();

    Agendamento buscarPorId(Long id);

    Agendamento salvar(Agendamento agendamento);

    Agendamento atualizar(Long id, Agendamento agendamentoAtualizado);

    void deletar(Long id);

}
