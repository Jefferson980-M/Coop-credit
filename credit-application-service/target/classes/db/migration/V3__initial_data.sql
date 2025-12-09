-- Password is 'password' (encoded)
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$Dow.dO/h.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w'); 
INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_ADMIN');

INSERT INTO users (username, password) VALUES ('analyst', '$2a$10$Dow.dO/h.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w');
INSERT INTO user_roles (user_id, role) VALUES (2, 'ROLE_ANALISTA');

INSERT INTO users (username, password) VALUES ('affiliate', '$2a$10$Dow.dO/h.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w.sE/7w');
INSERT INTO user_roles (user_id, role) VALUES (3, 'ROLE_AFILIADO');

INSERT INTO affiliates (name, email, document, salary, active, created_at) 
VALUES ('Test Affiliate', 'test@mail.com', '12345678', 5000.00, true, NOW());
