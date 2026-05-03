-- Initial roles
INSERT INTO rol (nombre) SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'USER');
INSERT INTO rol (nombre) SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ADMIN');

-- Note: In this project, passwords must be BCrypt encoded.
-- For testing purposes, we can create users via the /auth/register and /auth/registerAdmin endpoints,
-- which will handle the encryption correctly.
-- However, if we wanted to seed them here, we'd need the BCrypt hashes.
-- '1234' hash: $2a$10$7zB1pZzF.wH9O.YFzH7G9eWlJ.OqY3XgY0Yf9Z0XgY0Yf9Z0XgY0Y
