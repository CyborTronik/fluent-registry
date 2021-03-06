CREATE TABLE companies (
  id          VARCHAR(40),
  name        VARCHAR(255) UNIQUE,
  logoPath    VARCHAR(2000),
  description TEXT,
  details     TEXT,
  enabled     BIT                DEFAULT 1,
  createdAt   TIMESTAMP NOT NULL DEFAULT 0,
  updatedAt   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);