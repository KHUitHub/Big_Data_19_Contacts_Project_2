/* P2) 각 영화의 총 대여 횟수와 해당 영화의 순위 계산 - 동 순위 처리 필요
 * 중간 단계: 각 영화의 총 대여 횟수 계산
 */
with film_total_rentals as (
	select f.film_id, f.title, count(r.rental_id) as total_rentals
	  from film f
	  right join inventory i
	    on f.film_id = i.film_id
	  join rental r
	    on i.inventory_id = r.inventory_id
	group by f.film_id
)
select film_id, title, total_rentals,
	   dense_rank() over(order by total_rentals desc) as rank_num
  from film_total_rentals
;

commit;