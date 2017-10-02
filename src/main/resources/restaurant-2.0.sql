drop table if exists order_items_demo;
drop table if exists orders_demo;
drop table if exists menu_items_demo;
drop table if exists categories;


drop table if exists invoices;
drop table if exists order_items;
drop table if exists orders;
drop table if exists menu_items;
drop table if exists dishes;
drop table if exists users;


create table orders_demo(
o_id int auto_increment primary key,
o_state enum('NEW') not null,
o_creation timestamp not null
);

create table order_items_demo(
oi_order_id int not null,
oi_item_id int not null,
oi_item_number int not null check(oi_item_number>0),
constraint foreign key(oi_order_id) references orders_demo(o_id),
constraint foreign key(oi_item_id) references  menu_items_demo(mi_id) 
);

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
