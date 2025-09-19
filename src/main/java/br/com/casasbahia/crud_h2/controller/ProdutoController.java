package br.com.casasbahia.crud_h2.controller;

import br.com.casasbahia.crud_h2.controller.dto.ProdutoRequest;
import br.com.casasbahia.crud_h2.controller.dto.ProdutoResponse;
import br.com.casasbahia.crud_h2.controller.mapper.ProdutoMapper;
import br.com.casasbahia.crud_h2.model.Produto;
import br.com.casasbahia.crud_h2.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;


    @Operation(summary = "Criar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito ao criar produto"),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@RequestBody @Valid ProdutoRequest produtoRequest) {
        Produto produto = produtoMapper.toEntity(produtoRequest);
        Produto savedProduto = produtoService.criar(produto);
        return ResponseEntity
                .created(URI.create("/api/produtos/" + savedProduto.getId()))
                .body(produtoMapper.toResponse(savedProduto));
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
        @ApiResponse(responseCode = "429", description = "Muitas requisições - limite de taxa excedido")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        List<ProdutoResponse> resposta = StreamSupport
                .stream(produtoService.listar().spliterator(), false)
                .map(produtoMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }


    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "503", description = "Serviço de produtos indisponível")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(
            @Parameter(description = "Código identificador do produto a ser buscado", required = true)
            @PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        ProdutoResponse response = produtoMapper.toResponse(produto);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Atualizar um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @Parameter(description = "Código identificador do produto a ser buscado", required = true)
            @PathVariable Long id,
            @RequestBody ProdutoRequest produtoRequest) {
        Produto produto = produtoMapper.toEntity(produtoRequest); // converte DTO de entrada para entidade
        produto.setId(id); // define o ID recebido na URL
        Produto atualizado = produtoService.atualizar(produto); // atualiza a entidade
        ProdutoResponse response = produtoMapper.toResponse(atualizado); // converte a entidade para DTO de saída
        return ResponseEntity.ok(response); // retorna a resposta com ID
    }


    @Operation(summary = "Deletar um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Código identificador do produto a ser buscado", required = true)
            @PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}