-- Clear existing data to ensure a clean state for each test class
DELETE FROM menu;
DELETE FROM restaurants;
DELETE FROM users;
DELETE FROM user_types;

-- Insert User Types
INSERT INTO user_types (id, name, description) VALUES
(1, 'Admin', 'Administrator with full access'),
(2, 'Restaurant Owner', 'Owner of one or more restaurants'),
(3, 'Client', 'Regular customer');

-- Insert a default user to be used as an owner in tests
-- users.created_at and users.last_update are NOT NULL in the schema
INSERT INTO users (id, name, email, login, password, created_at, last_update, user_type_id, address) VALUES
(1, 'Test Owner', 'owner@test.com', 'testowner', 'hashed_password', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '123 Test St');

-- You can add more predefined data if other tests need it.
-- For example, a pre-existing restaurant for update/delete tests that don't depend on creation.
INSERT INTO restaurants (id, name, address, type_kitchen, opening_hours, owner_id) VALUES
(99, 'Pre-existing Restaurant', '99 Old Street', 'Legacy Food', '09:00-17:00', 1);
