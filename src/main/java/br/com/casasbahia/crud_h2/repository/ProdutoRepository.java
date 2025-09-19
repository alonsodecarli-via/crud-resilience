package br.com.casasbahia.crud_h2.repository;

import br.com.casasbahia.crud_h2.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
