/* P4) 대여가 가장 많은 직원과 그 순위 계산
 * 중간 단계: 대여가 가장 많은 직원 계산
 */
with staff_inline as (
	select s.staff_id,
	CONCAT(s.first_name, ' ', s.last_name) as staff_name,
	count(r.rental_id) as total_rentals
	  from staff s
	  join rental r
	    on s.staff_id = r.staff_id
	group by staff_id
)
select staff_id, staff_name, total_rentals,
	   rank() over(order by total_rentals desc) as rank_num
  from staff_inline
;

commit;