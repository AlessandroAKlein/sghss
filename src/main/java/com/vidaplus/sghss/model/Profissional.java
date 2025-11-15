package com.vidaplus.sghss.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profissionais")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Profissional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String crm;
    private String especialidade;
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
