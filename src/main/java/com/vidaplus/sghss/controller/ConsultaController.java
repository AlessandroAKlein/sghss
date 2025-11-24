package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Consulta;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.model.Profissional;
import com.vidaplus.sghss.repository.ConsultaRepository;
import com.vidaplus.sghss.repository.PacienteRepository;
import com.vidaplus.sghss.repository.ProfissionalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaRepository consultaRepo;
    private final PacienteRepository pacienteRepo;
    private final ProfissionalRepository profRepo;

    public ConsultaController(ConsultaRepository consultaRepo,
                              PacienteRepository pacienteRepo,
                              ProfissionalRepository profRepo){
        this.consultaRepo = consultaRepo;
        this.pacienteRepo = pacienteRepo;
        this.profRepo = profRepo;
    }

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody Consulta c){
        Paciente paciente = pacienteRepo.findById(c.getPaciente().getId()).orElse(null);
        Profissional prof = profRepo.findById(c.getProfissional().getId()).orElse(null);
        if (paciente == null || prof == null) return ResponseEntity.badRequest().body("Paciente ou Profissional inválido");
        c.setPaciente(paciente);
        c.setProfissional(prof);
        if (c.getStatus() == null) c.setStatus("AGENDADA");
        Consulta saved = consultaRepo.save(c);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> list(){ return ResponseEntity.ok(consultaRepo.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){ return consultaRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Consulta c){
        return consultaRepo.findById(id).map(existing -> {
            existing.setDataHora(c.getDataHora());
            existing.setStatus(c.getStatus());
            consultaRepo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!consultaRepo.existsById(id)) return ResponseEntity.notFound().build();
        consultaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id){
        return consultaRepo.findById(id).map(c -> {
            c.setStatus("CANCELADA");
            consultaRepo.save(c);
            return ResponseEntity.ok(c);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/realizar")
    public ResponseEntity<?> realizar(@PathVariable Long id){
        return consultaRepo.findById(id).map(c -> {
            c.setStatus("REALIZADA");
            consultaRepo.save(c);
            return ResponseEntity.ok(c);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<?> consultarPorPaciente(@PathVariable Long id){
        List<Consulta> consultas = consultaRepo.findByPacienteId(id);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/profissional/{id}")
    public ResponseEntity<?> consultarPorProfissional(@PathVariable Long id){
        List<Consulta> consultas = consultaRepo.findByProfissionalId(id);
        return ResponseEntity.ok(consultas);
    }





}
