DO $$
BEGIN
    INSERT INTO auth.roles(name, active) VALUES ('ROLE_ADMIN', 1);
    INSERT INTO auth.roles(name, active) VALUES ('ROLE_INSTITUTION', 1);
    INSERT INTO auth.roles(name, active) VALUES ('ROLE_CLIENT', 1);

    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual a sua cor predileta?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual foi o seu livro predileto?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual o nome da rua em que você cresceu?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual o nome do seu bicho de estimação predileto?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual a sua comida predileta?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual cidade você nasceu?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual é o seu país preferido?', 1);
    INSERT INTO auth.forgotpassword_questions(question, active) VALUES ('Qual é a sua marca de carro predileto?', 1);
END $$;
