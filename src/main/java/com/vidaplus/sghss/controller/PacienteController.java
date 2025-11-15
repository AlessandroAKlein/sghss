package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteRepository repo;
    public PacienteController(PacienteRepository repo){ this.repo = repo; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Paciente p){
        if (p.getCpf() != null && repo.existsByCpf(p.getCpf()))
            return ResponseEntity.badRequest().body("CPF já cadastrado");
        return ResponseEntity.ok(repo.save(p));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> list(){ return ResponseEntity.ok(repo.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Paciente p){
        return repo.findById(id).map(existing -> {
            existing.setNome(p.getNome());
            existing.setCpf(p.getCpf());
            existing.setEmail(p.getEmail());
            existing.setTelefone(p.getTelefone());
            existing.setDataNascimento(p.getDataNascimento());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
