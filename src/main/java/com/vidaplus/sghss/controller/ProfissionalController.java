package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Profissional;
import com.vidaplus.sghss.repository.ProfissionalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalRepository repo;

    public ProfissionalController(ProfissionalRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Profissional> create(@RequestBody Profissional p) {
        return ResponseEntity.ok(repo.save(p));
    }

    @GetMapping
    public ResponseEntity<List<Profissional>> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Profissional p) {
        return repo.findById(id).map(existing -> {

            if (p.getNome() != null) existing.setNome(p.getNome());
            if (p.getCrm() != null) existing.setCrm(p.getCrm());
            if (p.getEspecialidade() != null) existing.setEspecialidade(p.getEspecialidade());
            if (p.getEmail() != null) existing.setEmail(p.getEmail());

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
