package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.model.Servico;

import java.util.List;

public interface ServicoService {
    List<Servico> listarTodos();

    Servico buscarPorId(Long id);

    Servico salvar(Servico servico);

    Servico atualizar(Long id, Servico servicoAtualizado);

    void deletar(Long id);
}
