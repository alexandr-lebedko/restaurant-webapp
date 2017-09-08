drop table if exists menu_items_demo;
drop table if exists categories;


drop table if exists invoices;
drop procedure if exists update_order_items_sp;
drop table if exists order_items;
drop table if exists orders;
drop table if exists menu_items;
drop table if exists dishes;
drop table if exists users;



create table categories(
c_id int auto_increment primary key,
c_ukr_title varchar(50) unique not null,
c_en_title varchar(50) unique not null,
c_ru_title varchar(50) unique not null,
c_image_id varchar(50) unique not null
);

create table menu_items_demo (
mi_id int auto_increment primary key,
mi_ukr_title varchar(50) not null,
mi_en_title varchar(50) not null,
mi_ru_title varchar(50) not null,
mi_ukr_description varchar(255) not null,
mi_en_description varchar(255) not null,
mi_ru_description varchar(255) not null,
mi_state enum('ACTIVE','INACTIVE') not null,
mi_price float not null,
mi_category int NOT NULL,
mi_image_id VARCHAR(50) UNICODE NOT NULL,
constraint foreign key(mi_category) references categories(c_id) on delete cascade,
constraint unique(mi_ukr_title,mi_category),
constraint unique(mi_ru_title,mi_category),
constraint unique(mi_en_title,mi_category)
);


create table dishes(
d_id int auto_increment primary key,
d_category enum('MAIN', 'SIDE', 'SALAD', 'SOUP', 'STARTER', 'DESSERT') not null,
d_title varchar(50) not null,
d_description varchar(520) not null,
d_dishId varchar(50) not null,
constraint unique(d_category, d_title)
);

create table users(
u_id int auto_increment primary key,
u_role enum('CLIENT', 'ADMIN'),
u_email varchar(30) not null unique,
u_first_name varchar(20) not null,
u_last_name varchar(20) not null,
u_password_hash char(32) not null);

create table menu_items(
m_id int primary key auto_increment,
m_price decimal(10,2) not null default 0.00 check(m_price>=0),
m_is_active boolean default true,
m_dish_id int not null,
constraint foreign key(m_dish_id) references dishes(d_id)  on delete cascade
);

CREATE TABLE orders(
o_id INT PRIMARY KEY auto_increment,
o_user_id INT NOT NULL,
o_status ENUM('ACTIVE', 'FINISHED', 'CLOSED')  NOT NULL DEFAULT 'ACTIVE', 
CONSTRAINT FOREIGN KEY(o_user_id) REFERENCES users(u_id) ON DELETE CASCADE
);

create table order_items(
oi_id int primary key auto_increment,
oi_order_id int not null,
oi_processed boolean not null default false,
oi_menu_item_id int not null,
oi_quantity smallint not null default 1 check(oi_quantity>=1),
constraint foreign key(oi_order_id) references orders(o_id) on delete cascade,
constraint foreign key(oi_menu_item_id) references menu_items(m_id) on delete no action,
constraint unique key(oi_order_id, oi_menu_item_id, oi_processed)
);


create table invoices(
i_id int primary key,
i_order_id int not null unique,
i_paid boolean  not null default false,
i_price double(10,2) not null check(price>=0.0),
i_creation_timestamp timestamp not null
);

DELIMITER //
CREATE PROCEDURE update_order_items_sp(IN in_oi_id        INT, IN in_oi_order_id INT, IN in_oi_menu_item_id INT,
                                       IN in_oi_processed INT, IN in_oi_quantity INT)
  BEGIN
    SET @rowCount := (SELECT count(*)
                      FROM order_items
                      WHERE
                        oi_id <> in_oi_id AND oi_order_id = in_oi_order_id AND oi_menu_item_id = in_oi_menu_item_id AND
                        oi_processed = in_oi_processed);
    IF @rowCount = 0
    THEN UPDATE order_items
    SET oi_order_id  = in_oi_order_id, oi_menu_item_id = in_oi_menu_item_id, oi_processed = in_oi_processed,
      oi_quantity = in_oi_quantity
    WHERE oi_id = in_oi_id;
    ELSE
      DELETE FROM order_items
      WHERE oi_id = in_oi_id;
      INSERT INTO order_items (oi_order_id, oi_menu_item_id, oi_processed, oi_quantity)
      VALUES (in_order_id, in_oi_menu_item_id, in_oi_processed, in_oi_quantity)
      ON DUPLICATE KEY UPDATE oi_quantity = oi_quantity + VALUES(in_oi_quantity);
    END IF;
  END //
DELIMITER ;