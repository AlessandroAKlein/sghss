package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Prontuario;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.ProntuarioRepository;
import com.vidaplus.sghss.repository.PacienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {
    private final ProntuarioRepository repo;
    private final PacienteRepository pacienteRepo;

    public ProntuarioController(ProntuarioRepository repo, PacienteRepository pacienteRepo){
        this.repo = repo;
        this.pacienteRepo = pacienteRepo;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Prontuario p){
        Paciente paciente = pacienteRepo.findById(p.getPaciente().getId()).orElse(null);
        if (paciente == null) return ResponseEntity.badRequest().body("Paciente inválido");
        p.setPaciente(paciente);
        return ResponseEntity.ok(repo.save(p));
    }

    @GetMapping
    public ResponseEntity<List<Prontuario>> list(){ return ResponseEntity.ok(repo.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
