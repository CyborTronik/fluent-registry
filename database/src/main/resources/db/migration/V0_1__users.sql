CREATE TABLE users (
  id           VARCHAR(40),
  displayName  VARCHAR(100),
  email        VARCHAR(40),
  passwordHash VARCHAR(100),
  enabled BIT default 1,
  createdAt    TIMESTAMP NOT NULL DEFAULT 0,
  updatedAt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);