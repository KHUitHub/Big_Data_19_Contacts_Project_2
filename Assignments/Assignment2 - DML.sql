use project02;

select u.user_id,
	   u.name,
	   u.phone_number,
	   u.address,
	   t.tag_name
  from user u 
  join tag t
    on u.tag_id = t.tag_id
;


insert into user(name, phone_number, address, tag_id)
	 values ('me', '000', 'seoul', '3');
insert into user(name, phone_number, address, tag_id)
	 values ('you', '001', 'idk', '2');
insert into user(name, phone_number, address, tag_id)
	 values ('randomGuy', '002', 'states', '1');

select *
  from user