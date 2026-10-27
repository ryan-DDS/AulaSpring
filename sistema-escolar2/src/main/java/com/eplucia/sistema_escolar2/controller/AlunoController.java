
package com.eplucia.sistema_escolar2.controller;

import com.eplucia.sistema_escolar2.model.Aluno;
import com.eplucia.sistema_escolar2.repository.AlunoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    // O Spring injeta o Repository automaticamente.
    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    // READ: lista todos os alunos.
    @GetMapping
    public List<Aluno> listar() {
        return repository.findAll();
    }

    // READ: busca um aluno pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE: cadastra um aluno.
    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno aluno) {
        aluno.setId(null);

        Aluno alunoSalvo = repository.save(aluno);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alunoSalvo);
    }

    // UPDATE: atualiza os dados de um aluno existente.
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(
            @PathVariable Long id,
            @RequestBody Aluno dados) {

        return repository.findById(id)
                .map(aluno -> {
                    aluno.setNome(dados.getNome());
                    aluno.setEmail(dados.getEmail());

                    Aluno atualizado = repository.save(aluno);

                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: exclui um aluno pelo ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
