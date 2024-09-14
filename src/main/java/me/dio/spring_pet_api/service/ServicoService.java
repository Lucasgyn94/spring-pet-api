package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.dto.ServicoDTO;
import me.dio.spring_pet_api.domain.model.Servico;

import java.util.List;

public interface ServicoService {
    List<ServicoDTO> listarTodos();

    ServicoDTO buscarPorId(Long id);

    ServicoDTO salvar(ServicoDTO servicoDTO);

    ServicoDTO atualizar(Long id, ServicoDTO servicoDtoAtualizado);

    void deletar(Long id);
}
