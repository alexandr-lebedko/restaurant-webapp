DROP TABLE IF EXISTS invoices;
DROP PROCEDURE IF EXISTS update_order_items_sp;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS users;


CREATE TABLE dishes (
  d_id          INT AUTO_INCREMENT PRIMARY KEY,
  d_category    ENUM ('MAIN', 'SIDE', 'SALAD', 'SOUP', 'STARTER', 'DESSERT') NOT NULL,
  d_title       VARCHAR(50)                                                  NOT NULL,
  d_description VARCHAR(520)                                                 NOT NULL,
  CONSTRAINT UNIQUE (d_category, d_title)
);

CREATE TABLE users (
  u_id            INT                  AUTO_INCREMENT PRIMARY KEY,
  u_role          ENUM ('CLIENT', 'ADMIN'),
  u_email         VARCHAR(30) NOT NULL UNIQUE,
  u_first_name    VARCHAR(20) NOT NULL,
  u_last_name     VARCHAR(20) NOT NULL,
  u_password_hash CHAR(32)    NOT NULL);

CREATE TABLE menu_items (
  m_id        INT PRIMARY KEY                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            AUTO_INCREMENT,
  m_price     DECIMAL(10, 2)       NOT NULL                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       DEFAULT 0.00 CHECK (
    m_price >= 0),
  m_is_active BOOLEAN                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       DEFAULT TRUE,
  m_dish_id   INT UNIQUE           NOT NULL,
  CONSTRAINT FOREIGN KEY (m_dish_id) REFERENCES dishes (d_id)
    ON DELETE CASCADE
);

CREATE TABLE orders (
  o_id      INT PRIMARY KEY                                AUTO_INCREMENT,
  o_user_id INT                                   NOT NULL,
  o_status  ENUM ('ACTIVE', 'FINISHED', 'CLOSED') NOT NULL DEFAULT 'ACTIVE',
  CONSTRAINT FOREIGN KEY (o_user_id) REFERENCES users (u_id)
    ON DELETE CASCADE
);

CREATE TABLE order_items (
  oi_id           INT PRIMARY KEY                                                                                                                                                                                                                                                                                                                        AUTO_INCREMENT,
  oi_order_id INT          NOT NULL,
  oi_processed BOOLEAN NOT NULL                                                                                                                                                                                                                                                                                                                       DEFAULT FALSE,
  oi_menu_item_id INT  NOT NULL,
  oi_quantity     SMALLINT NOT NULL                                                                                                                                                                                                                                                                                                                      DEFAULT 1,
  CONSTRAINT FOREIGN KEY (oi_order_id) REFERENCES orders (o_id)
    ON DELETE CASCADE,
  CONSTRAINT FOREIGN KEY (oi_menu_item_id) REFERENCES menu_items (m_id)
    ON DELETE NO ACTION,
  CONSTRAINT UNIQUE KEY (oi_order_id, oi_menu_item_id, oi_processed)
);


CREATE TABLE invoices (
  i_id                 INT PRIMARY KEY  AUTO_INCREMENT,
  i_order_id INT                 NOT NULL UNIQUE,
  i_paid     BOOLEAN NOT NULL DEFAULT FALSE,
  i_price    DOUBLE(10, 2) NOT NULL,
  i_creation_timestamp TIMESTAMP NOT NULL
);
DELIMITER ##
CREATE PROCEDURE update_order_items_sp(IN in_oi_id       INT, IN in_oi_order_id INT, IN in_oi_menu_item_id INT,
                                       IN in_oi_quantity INT, IN in_oi_processed INT)
  BEGIN
    SET @rowCount := (SELECT count(*)
                      FROM order_items
                      WHERE
                        oi_id <> in_oi_id AND oi_order_id = in_oi_order_id AND oi_menu_item_id = in_oi_menu_item_id AND
                        oi_processed = in_oi_processed);
    IF @rowCount = 0
    THEN UPDATE order_items
    SET oi_order_id = in_oi_order_id, oi_menu_item_id = in_oi_menu_item_id, oi_processed = in_oi_processed,
      oi_quantity   = in_oi_quantity
    WHERE oi_id = in_oi_id;
    ELSE
      DELETE FROM order_items
      WHERE oi_id = in_oi_id;
      INSERT INTO order_items (oi_order_id, oi_menu_item_id, oi_processed, oi_quantity)
      VALUES (in_oi_order_id, in_oi_menu_item_id, in_oi_processed, in_oi_quantity)
      ON DUPLICATE KEY UPDATE oi_quantity = oi_quantity + VALUES(oi_quantity);
    END IF;
  END ##