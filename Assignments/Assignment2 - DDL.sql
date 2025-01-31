create table user (
	user_id int not null auto_increment,
	name varchar(50) not null,
	phone_number varchar(50) not null,
	address varchar(50) not null,
	tag_id int not null,
	primary key (user_id),
	constraint fk_user_tag foreign key (tag_id) references tag (tag_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

create table tag (
	tag_id int not null,
	tag_name varchar(50) not null,
	primary key (tag_id)
);