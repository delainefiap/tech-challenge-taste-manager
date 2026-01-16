USE tastemanager;

-- Limpar dados existentes (se houver)
DELETE FROM users;
DELETE FROM user_types;

-- Inserir tipos de usuário
INSERT INTO user_types (name) VALUES ('CLIENTE'), ('DONO_RESTAURANTE');

-- Inserir usuários
INSERT INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES
('João Silva', 'joao.silva@email.com', 'joaosilva', 'senha123', NOW(), NOW(), 1, 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@email.com', 'mariaoliveira', 'senha456', NOW(), NOW(), 2, 'Rua B, 456'),
('Carlos Souza', 'carlos.souza@email.com', 'carlossouza', 'senha789', NOW(), NOW(), 1, 'Rua C, 789'),
('Delaine Silva', 'delaine@email.com', 'delaine', 'senha456', NOW(), NOW(), 2, 'Rua D, 111');

SELECT 'Data inserted successfully!' as status;
SELECT * FROM user_types;
SELECT * FROM users;
