package io.github.andreluiz19.rest.controller;

import io.github.andreluiz19.domain.entity.Produto;
import io.github.andreluiz19.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("{id}")
    public Produto getById(@PathVariable("id") Integer id) {
        return produtoRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado!");
                });
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody @Valid Produto produto ) {
        return produtoRepository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        produtoRepository.findById(id)
                .map(cliente -> {
                    produtoRepository.delete(cliente);
                    return Void.TYPE;
                }).orElseThrow(() -> {
                    throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado!");
                });
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable("id") Integer id,
                       @RequestBody @Valid Produto produto) {
        produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtoRepository.save(produto);
                    return Void.TYPE;
                }).orElseThrow(() -> {
                    throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado!");
                });
    }

    @GetMapping
    public List<Produto> find(Produto produto) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(produto, matcher);

        return produtoRepository.findAll(example);
    }
}
