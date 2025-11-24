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

    public PacienteController(PacienteRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Paciente p) {
        if (p.getCpf() != null && repo.existsByCpf(p.getCpf())) {
            return ResponseEntity.badRequest().body("CPF já cadastrado");
        }
        return ResponseEntity.ok(repo.save(p));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Paciente p) {
        return repo.findById(id).map(existing -> {

            if (p.getNome() != null) existing.setNome(p.getNome());
            if (p.getCpf() != null) existing.setCpf(p.getCpf());
            if (p.getEmail() != null) existing.setEmail(p.getEmail());
            if (p.getTelefone() != null) existing.setTelefone(p.getTelefone());
            if (p.getDataNascimento() != null) existing.setDataNascimento(p.getDataNascimento());

            return ResponseEntity.ok(repo.save(existing));

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
