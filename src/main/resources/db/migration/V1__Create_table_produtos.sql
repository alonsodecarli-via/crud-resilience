CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ncm VARCHAR(20),
    descricao_ncm VARCHAR(255),
    preco DECIMAL(10,2),
    quantidade INT
);