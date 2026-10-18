CREATE DATABASE IF NOT EXISTS inotes_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE inotes_db;

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS note_type;

CREATE TABLE note_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE notes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type_id INT NOT NULL,
    CONSTRAINT fk_notes_type
        FOREIGN KEY (type_id)
        REFERENCES note_type(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

INSERT INTO note_type (name, description)
VALUES
('Cá nhân', 'Các ghi chú cá nhân'),
('Công việc', 'Các ghi chú liên quan đến công việc'),
('Học tập', 'Các ghi chú phục vụ học tập');

INSERT INTO notes (title, content, type_id)
VALUES
('Họp giao ban đầu tuần',
 'Chuẩn bị nội dung cho cuộc họp giao ban đầu tuần.',
 2),
('Ôn tập Design Pattern Servlet',
 'Ôn tập Strategy Pattern và các kiến thức Servlet.',
 3),
('Mua sách',
 'Mua sách học Java và Design Pattern.',
 1);
