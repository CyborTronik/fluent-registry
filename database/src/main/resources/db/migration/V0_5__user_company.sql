ALTER TABLE users
    ADD COLUMN companyId VARCHAR(40) NULL;

ALTER TABLE users
  ADD CONSTRAINT fk_userCompany
  FOREIGN KEY (companyId)
  REFERENCES companies(id);