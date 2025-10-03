CREATE TABLE tb_extras(

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    soda_extra_price DECIMAL(8,2),
    water_extra_price DECIMAL(8,2),
    coffee_extra_price DECIMAL(8,2),
    companion_extra_price DECIMAL(8,2),
    stay_id BIGINT NOT NULL,

    CONSTRAINT fk_extras__stay FOREIGN KEY (stay_id) REFERENCES tb_stay(id)
);