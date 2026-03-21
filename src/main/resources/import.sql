-- user_types
INSERT INTO user_types (name, description) VALUES ('CLIENTE', 'Usuário que pode consultar restaurantes, fazer pedidos e deixar avaliações');
INSERT INTO user_types (name, description) VALUES ('DONO_RESTAURANTE', 'Usuário responsável pela gestão de um ou mais restaurantes');

-- users
INSERT INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES ('João Silva', 'joao.silva@email.com', 'joaosilva', '$2a$12$Ixa4t6E3o6owd03V3dVTlO0qxZSy4t1/p9fseXxfU6zYBHd4aGkBK', NOW(), NOW(), 1, 'Rua A, 123');
INSERT INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES ('Maria Oliveira', 'maria.oliveira@email.com', 'mariaoliveira', '$2a$12$QDFXgFJ/4NtHu3RbOFP7xeOK0e.GBDvaeJIKCdt2NzD49eA02/5R2', NOW(), NOW(), 2, 'Rua B, 456');
INSERT INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES ('Carlos Souza', 'carlos.souza@email.com', 'carlossouza', '$2a$12$MbYCSPWuExE1l4bRZvc0repEfWanPMkJycboFXyg5yGJV7McaF7z2', NOW(), NOW(), 1, 'Rua C, 789');
INSERT INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES ('Delaine Silva', 'delaine@email.com', 'delaine', '$2a$12$QDFXgFJ/4NtHu3RbOFP7xeOK0e.GBDvaeJIKCdt2NzD49eA02/5R2', NOW(), NOW(), 2, 'Rua D, 111');

-- restaurants
INSERT INTO restaurants (name, address, type_kitchen, opening_hours, owner_id) VALUES ('Restaurante Brasil', 'Av. Central, 100', 'Brasileira', '08:00-22:00', 2);
INSERT INTO restaurants (name, address, type_kitchen, opening_hours, owner_id) VALUES ('Pizza Itália', 'Rua das Flores, 200', 'Italiana', '10:00-23:00', 4);

-- menu (corresponde à entidade domain.entity.Menu)
INSERT INTO menu (restaurant_id, restaurant_item_number, name, description, price, image_path, available_only_at_restaurant) VALUES (1, 1, 'Feijoada', 'Feijoada completa', 39.90, '/images/feijoada.jpg', TRUE);
INSERT INTO menu (restaurant_id, restaurant_item_number, name, description, price, image_path, available_only_at_restaurant) VALUES (1, 2, 'Bife à Cavalo', 'Bife com ovo', 29.90, '/images/bife.jpg', FALSE);
INSERT INTO menu (restaurant_id, restaurant_item_number, name, description, price, image_path, available_only_at_restaurant) VALUES (2, 1, 'Pizza Margherita', 'Pizza tradicional italiana', 49.90, '/images/margherita.jpg', FALSE);
INSERT INTO menu (restaurant_id, restaurant_item_number, name, description, price, image_path, available_only_at_restaurant) VALUES (2, 2, 'Pizza Calabresa', 'Pizza de calabresa', 44.90, '/images/calabresa.jpg', TRUE);
