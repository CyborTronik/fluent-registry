CREATE TABLE companies (
  id           VARCHAR(40),
  name  VARCHAR(100),
  logoPath        VARCHAR(40),
  description VARCHAR(100),
  enabled BIT default 1,
  createdAt    TIMESTAMP NOT NULL DEFAULT 0,
  updatedAt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);