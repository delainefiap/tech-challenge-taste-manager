-- user_types
INSERT IGNORE INTO user_types (name) VALUES
('CLIENTE'),
('DONO_RESTAURANTE');

-- users
INSERT IGNORE INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES
('João Silva', 'joao.silva@email.com', 'joaosilva', 'senha123', NOW(), NOW(), 1, 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@email.com', 'mariaoliveira', 'senha456', NOW(), NOW(), 2, 'Rua B, 456'),
('Carlos Souza', 'carlos.souza@email.com', 'carlossouza', 'senha789', NOW(), NOW(), 1, 'Rua C, 789'),
('Delaine Silva', 'delaine@email.com', 'delaine', 'senha456', NOW(), NOW(), 2, 'Rua D, 111');

-- restaurants
INSERT IGNORE INTO restaurants (name, address, type_kitchen, opening_hours, owner_id) VALUES
('Restaurante Brasil', 'Av. Central, 100', 'Brasileira', '08:00-22:00', 2),
('Pizza Itália', 'Rua das Flores, 200', 'Italiana', '10:00-23:00', 2);

-- menu_items
INSERT IGNORE INTO menu_items (restaurant_id, name, description, price, image_path, available_only_at_restaurant) VALUES
(1, 'Feijoada', 'Feijoada completa', 39.90, '/images/feijoada.jpg', TRUE),
(1, 'Bife à Cavalo', 'Bife com ovo', 29.90, '/images/bife.jpg', FALSE),
(2, 'Pizza Margherita', 'Pizza tradicional italiana', 49.90, '/images/margherita.jpg', FALSE),
(2, 'Pizza Calabresa', 'Pizza de calabresa', 44.90, '/images/calabresa.jpg', TRUE);
