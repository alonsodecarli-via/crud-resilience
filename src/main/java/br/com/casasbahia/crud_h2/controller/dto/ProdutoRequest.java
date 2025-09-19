package br.com.casasbahia.crud_h2.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Objeto utilizado pela API para o cadastro e atualização de produtos")
public record ProdutoRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        @Schema(description = "Nome do produto", example = "Notebook Dell")
        String nome,

        @NotBlank(message = "O NCM é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O NCM deve conter exatamente 8 dígitos")
        @Schema(description = "Código da Nomenclatura Comum do Mercosul (NCM) do produto", example = "123456")
        String ncm,

        @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
        @Schema(description = "Descrição da Nomenclatura Comum do Mercosul (NCM)", example = "Notebook com processador Intel Core i7")
        String descricaoNcm,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        @Schema(description = "Preço do produto", example = "22.95")
        BigDecimal preco,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 0, message = "A quantidade não pode ser negativa")
        @Schema(description = "Quantidade em estoque", example = "10")
        Integer quantidade
) {}