package br.com.casasbahia.crud_h2.service;

import br.com.casasbahia.crud_h2.exception.ProductNotFoundException;
import br.com.casasbahia.crud_h2.exception.ProductUnavailableException;
import br.com.casasbahia.crud_h2.exception.RateLimitExceededException;
import br.com.casasbahia.crud_h2.model.Produto;
import br.com.casasbahia.crud_h2.repository.ProdutoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private Random random = new Random();

    public Produto criar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @RateLimiter(name = "produto-list", fallbackMethod = "fallbackListarProdutos")
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public List<Produto> fallbackListarProdutos(RequestNotPermitted ex) {
        throw new RateLimitExceededException("Limite de requisições atingido para listagem de produtos.", ex);
    }

    @Retry(name = "produto-service")
    @CircuitBreaker(name = "produto-service", fallbackMethod = "fallbackBuscarPorId")
    public Produto buscarPorId(Long id) {
        if (random.nextInt() % 2 == 0) {
            throw new RuntimeException("Simulando falha no serviço de produto");
        }
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o ID: " + id));
    }

    public Produto fallbackBuscarPorId(Long id, Throwable ex) {
        throw new ProductUnavailableException(id, ex);
    }

    public Produto atualizar(Produto produto) {
        if (!produtoRepository.existsById(produto.getId())) {
            throw new ProductNotFoundException("Produto não encontrado com o ID: " + produto.getId());
        }
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

}
