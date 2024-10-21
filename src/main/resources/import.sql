
INSERT INTO UsuarioModel (id, telefone, senha, perfil)
VALUES (1, '63984398131', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', 'USER');
INSERT INTO UsuarioModel (id, telefone, senha, perfil)
VALUES (2, '63984398131', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', 'USER');

-- Tabela para a classe pai Pessoa
INSERT INTO pessoamodel (id, usuario_id, nome, telefone, email) VALUES (1, 1, 'Alice Silva', '123456789', 'alice.silva@example.com');
INSERT INTO pessoamodel (id, usuario_id, nome, telefone, email) VALUES (2, 2,'Empresa XYZ', '987654321', 'contato@empresaxyz.com');

-- Tabela para PessoaFisicaModel
INSERT INTO pessoafisicamodel (id, cpf) VALUES (1, '123.456.789-00');

-- Tabela para PessoaJuridicaModel
INSERT INTO pessoajuridicamodel (id, cnpj) VALUES (2, '12.345.678/0001-95');
