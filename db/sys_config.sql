DO $$
BEGIN
    INSERT INTO roles(name, active) VALUES ('ROLE_ADMIN', 1);
    INSERT INTO roles(name, active) VALUES ('ROLE_CLIENT', 1);
END $$;
