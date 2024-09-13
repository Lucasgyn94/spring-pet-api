package me.dio.spring_pet_api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especialidade;

    @OneToMany(mappedBy = "veterinario")
    private List<Agendamento> agendamentos = new ArrayList<>();
}
