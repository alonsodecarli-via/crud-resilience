package br.com.casasbahia.crud_h2.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Objeto retornado pela API após operações com produto")
public class ProdutoResponse {

    @Schema(description = "ID do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Notebook Dell")
    private String nome;

    @Schema(description = "Código da Nomenclatura Comum do Mercosul (NCM) do produto", example = "123456")
    private String ncm;

    @Schema(description = "Descrição da Nomenclatura Comum do Mercosul (NCM)", example = "Notebook com processador Intel Core i7")
    private String descricaoNcm;

    @Schema(description = "Preço do produto", example = "2999.99")
    private BigDecimal preco;

    @Schema(description = "Quantidade em estoque", example = "10")
    private Integer quantidade;
}
