/* P3) 각 고객의 대여 횟수와 고객의 순위 계산
 * 중간 단계: 각 고객의 대여 횟수 계산
 * */
with film_total_rentals as (
	select c.customer_id,
	CONCAT(c.first_name, ' ', c.last_name) as customer_name,
	COUNT(r.rental_id) as total_rentals
	  from film f
	  right join inventory i
	    on f.film_id = i.film_id
	  right join rental r
	    on i.inventory_id = r.inventory_id
	  right join customer c
	    on r.customer_id = c.customer_id
	group by c.customer_id
)
select customer_id, customer_name, total_rentals,
	   rank() over(order by total_rentals desc) as rank_num
  from film_total_rentals
;

commit;