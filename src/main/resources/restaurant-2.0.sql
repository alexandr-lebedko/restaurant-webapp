DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS users;

CREATE TABLE categories (
  c_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  c_ukr_title VARCHAR(50) UNIQUE NOT NULL,
  c_en_title  VARCHAR(50) UNIQUE NOT NULL,
  c_ru_title  VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE items (
  i_id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  i_ukr_title       VARCHAR(50)                 NOT NULL,
  i_en_title        VARCHAR(50)                 NOT NULL,
  i_ru_title        VARCHAR(50)                 NOT NULL,
  i_ukr_description VARCHAR(255)                NOT NULL,
  i_en_description  VARCHAR(255)                NOT NULL,
  i_ru_description  VARCHAR(255)                NOT NULL,
  i_state           ENUM ('ACTIVE', 'INACTIVE') NOT NULL,
  i_price           FLOAT                       NOT NULL,
  i_category        BIGINT                      NOT NULL,
  i_image_id        VARCHAR(50) UNICODE         NOT NULL,
  CONSTRAINT FOREIGN KEY (i_category) REFERENCES categories (c_id)
    ON DELETE CASCADE,
  CONSTRAINT UNIQUE (i_ukr_title, i_category),
  CONSTRAINT UNIQUE (i_ru_title, i_category),
  CONSTRAINT UNIQUE (i_en_title, i_category)
);

CREATE TABLE orders (
  o_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  o_invoice  BIGINT       NOT NULL,
  o_state    ENUM ('NEW', 'PROCESSED', 'MODIFIED', 'REJECTED') NOT NULL,
  o_creation TIMESTAMP    NOT NULL,
  CONSTRAINT FOREIGN KEY (o_invoice) REFERENCES invoices (inv_id)
);

CREATE TABLE order_items (
  oi_id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  oi_order    BIGINT NOT NULL,
  oi_item     BIGINT NOT NULL,
  oi_item_number BIGINT NOT NULL CHECK (oi_item_number > 0),
  CONSTRAINT FOREIGN KEY (oi_order) REFERENCES orders (o_id) ON DELETE CASCADE,
  CONSTRAINT FOREIGN KEY (oi_item) REFERENCES items (i_id)
);

CREATE TABLE users (
  u_id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  u_role          ENUM ('CLIENT', 'ADMIN'),
  u_email         VARCHAR(30) NOT NULL UNIQUE,
  u_first_name    VARCHAR(20) NOT NULL,
  u_last_name     VARCHAR(20) NOT NULL,
  u_password_hash CHAR(32)    NOT NULL
);

CREATE TABLE invoices (
  inv_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  inv_user     BIGINT        NOT NULL,
  inv_price    DOUBLE(10, 2) NOT NULL CHECK (inv_price >= 0.0),
  inv_state    ENUM ('ACTIVE','CLOSED','PAID', 'UNPAID'),
  inv_creation TIMESTAMP     NOT NULL
);
