package com.vidaplus.sghss.repository;
import java.util.List;


import com.vidaplus.sghss.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteId(Long pacienteId);
    List<Consulta> findByProfissionalId(Long profissionalId);

}
