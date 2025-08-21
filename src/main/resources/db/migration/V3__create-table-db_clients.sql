CREATE TABLE tb_client(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    gender VARCHAR(15) NOT NULL,
    status VARCHAR(15) NOT NULL,
    street VARCHAR(80),
    number VARCHAR(5),
    district VARCHAR(50),
    city VARCHAR(50),
    state VARCHAR(50),
    country VARCHAR(50),
    cep VARCHAR(9)
);