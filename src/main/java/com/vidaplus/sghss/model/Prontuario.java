package com.vidaplus.sghss.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "prontuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Prontuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRegistro;
    @Column(length = 2000)
    private String descricao;
    private String diagnostico;
    private String prescricao;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @JsonBackReference
    private Paciente paciente;

}
