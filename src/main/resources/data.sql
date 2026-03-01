-- user_types
INSERT INTO user_types (name) VALUES
('CLIENTE'),
('DONO_RESTAURANTE');

-- users
INSERT INTO users (name, email, login, password, created_at, last_update, user_type_id, address) VALUES
('João Silva', 'joao.silva@email.com', 'joaosilva', 'senha123', NOW(), NOW(), 1, 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@email.com', 'mariaoliveira', 'senha456', NOW(), NOW(), 2, 'Rua B, 456'),
('Carlos Souza', 'carlos.souza@email.com', 'carlossouza', 'senha789', NOW(), NOW(), 1, 'Rua C, 789'),
('Delaine Silva', 'delaine@email.com', 'delaine', 'senha456', NOW(), NOW(), 2, 'Rua D, 111');

--restaurants
INSERT INTO restaurant (name, address, type_kitchen, opening_hours, owner_id) VALUES
                                                                                  ('Restaurante Saboroso', 'Rua Principal, 100', 'Brasileira', '08:00-22:00', 2),
                                                                                  ('Restaurante da Dede', 'Rua Dede, 100', 'Lanches', '08:00-12:00', 4);

-- menus
INSERT INTO menu (menu_id, restaurant_id) VALUES (1, 1), (2, 2);

-- items menu
INSERT INTO item_menu (menu_id, name, description, price, photo_path, available_only_at_restaurant) VALUES
                                                                                                        (1, 'Pizza Margherita', 'Pizza com molho de tomate, mussarela e manjericão.', 25.50, '/images/pizza-margherita.jpg', true),
                                                                                                        (1, 'Lasanha de Carne', 'Lasanha de carne com molho branco.', 30.00, '/images/lasanha.jpg', true),
                                                                                                        (1, 'Lasanha de Carne', 'Lasanha de carne com molho branco.', 30.00, '/images/lasanha.jpg', true),
                                                                                                        (1, 'Salada Caesar', 'Salada com alface, croutons e molho Caesar.', 20.00, '/images/salada-caesar.jpg', true),
                                                                                                        (1, 'Refrigerante', 'Refrigerante lata.', 5.00, '/images/refrigerante.jpg', true),
                                                                                                        (2, 'X-Burguer', 'Hambúrguer com queijo, presunto e ovo.', 18.00, '/images/x-burguer.jpg', false),
                                                                                                        (2, 'X-Salada', 'Hambúrguer com queijo, alface, tomate e maionese.', 20.00, '/images/x-salada.jpg', false),
                                                                                                        (2, 'X-Bacon', 'Hambúrguer com queijo, bacon e ovo.', 22.00, '/images/x-bacon.jpg', false),
                                                                                                        (2, 'Hambúrguer Clássico', 'Hambúrguer com queijo, alface e tomate.', 15.00, '/images/hamburguer.jpg', false),
                                                                                                        (2, 'Batata Frita', 'Porção de batata frita crocante.', 10.00, '/images/batata-frita.jpg', false);