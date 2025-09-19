ALTER TABLE produtos
    ADD CONSTRAINT uk_produtos_ncm UNIQUE (ncm);

CREATE INDEX idx_produtos_nome ON produtos (nome);