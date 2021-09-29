

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO sec_roles (id, name, status) VALUES
  (uuid_generate_v4(),'ROLE_ADMIN', 'ACT'),
  (uuid_generate_v4(),'ROLE_USER', 'ACT'),
  (uuid_generate_v4(),'ROLE_CUSTOMER', 'ACT'),
  (uuid_generate_v4(),'ROLE_SUPER', 'ACT');

INSERT INTO sec_users (id, created_date, "name", nick, email, "password", status)
	   VALUES(uuid_generate_v4(), now(), 'Admin System', 'Admin', 'diego1503bsc@email.com',
	  '$2a$10$Iq/whWhDWGF4o0CJjRhXHe9y6p5m9ZprgFTPnJoplPELdSzHSFl32', 'ACT'::character varying);

INSERT INTO public.sec_user_rol (user_id, rol_id) values
			((select id from sec_users where nick = 'Admin'),
			 (select id from sec_roles where name = 'ROLE_ADMIN'));

