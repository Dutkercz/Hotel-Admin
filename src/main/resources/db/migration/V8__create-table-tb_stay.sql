CREATE TABLE tb_stay(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,

    CONSTRAINT fk_stay__client FOREIGN KEY (client_id) REFERENCES tb_client(id),
    CONSTRAINT fk_stay__room FOREIGN KEY (room_id) REFERENCES tb_room(id)
);

CREATE TABLE tb_stay_guest(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    stay_id BIGINT NOT NULL,

    CONSTRAINT fk_stay_guest__stay FOREIGN KEY (stay_id) REFERENCES tb_stay(id) ON DELETE CASCADE
);