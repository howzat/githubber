create user pact_db_user with encrypted password 'pact_password';
create database pactbroker_db with owner pact_db_user;
grant all privileges on database pactbroker_db to pact_db_user;