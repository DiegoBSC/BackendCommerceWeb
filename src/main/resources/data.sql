

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO sec_roles (id, name, status) VALUES
  (uuid_generate_v4(),'ROLE_ADMIN', 'ACT'),
  (uuid_generate_v4(),'ROLE_USER', 'ACT'),
  (uuid_generate_v4(),'ROLE_CUSTOMER', 'ACT'),
  (uuid_generate_v4(),'ROLE_SUPER', 'ACT');