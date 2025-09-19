ALTER TABLE produtos ADD COLUMN categoria VARCHAR(60);

UPDATE produtos
SET categoria = 'INFORMATICA'
WHERE ncm = '123456';