CREATE TABLE tb_extras(

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    soda_Quantity INTEGER,
    water_Quantity INTEGER,
    coffee_Quantity INTEGER,
    stay_id BIGINT NOT NULL,

    CONSTRAINT fk_extras__stay FOREIGN KEY (stay_id) REFERENCES tb_stay(id)
);