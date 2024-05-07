-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into pessoa (id, nome, data_nascimento, cpf) values(0, 'Julio', '1992-03-23', '00000000001');
insert into endereco (id, logradouro, cep, numero, cidade, estado, principal, id_pessoa) values(0, 'Fit Icoaraci', 66810010, 472, 'Belém', 'Pará', 'true', 0);
