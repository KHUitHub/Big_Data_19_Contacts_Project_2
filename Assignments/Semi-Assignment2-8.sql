/*각 직원이 처리한 대여 건수의 누적 합계*/
select s.staff_id,
	   concat(s.first_name, ' ', s.last_name) as staff_name,
	   r.rental_date,
	   row_number() over(partition by staff_id order by r.rental_date asc) as cumulative_rentals
  from staff s
  join rental r
    on s.staff_id = r.staff_id
;

commit;