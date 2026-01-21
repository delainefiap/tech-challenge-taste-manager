-- Criar banco de dados se não existir
CREATE DATABASE IF NOT EXISTS tastemanager;
USE tastemanager;

-- Criar tabela user_types
CREATE TABLE IF NOT EXISTS user_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- Criar tabela users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NULL,
    user_type_id BIGINT NOT NULL,
    address VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT FKp0utx8kvsuc78nb39dgyg6oko FOREIGN KEY (user_type_id) REFERENCES user_types(id),
    INDEX idx_user_type (user_type_id)
);

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
