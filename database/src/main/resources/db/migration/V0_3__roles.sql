CREATE TABLE roles (
  name      VARCHAR(100),
  enabled   BIT                DEFAULT 1,
  createdAt TIMESTAMP NOT NULL DEFAULT 0,
  updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (name, enabled)
);

INSERT INTO roles (name, createdAt) VALUES
  ('MANAGE_USERS', NULL);

CREATE TABLE user_roles (
  user_id   VARCHAR(40),
  role_name VARCHAR(100),
  PRIMARY KEY (user_id, role_name)
);