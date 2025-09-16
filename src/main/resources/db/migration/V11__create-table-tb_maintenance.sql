CREATE TABLE tb_maintenance(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    start_maintenance DATETIME NOT NULL,
    end_maintenance DATETIME,
    room_id BIGINT NOT NULL,

    CONSTRAINT fk_maintenance__room FOREIGN KEY (room_id) REFERENCES tb_room(id)
);