/* P6) 각 배우가 출연한 영화의 평균 대여 횟수와 순위
 * 중간 단계 1: 각 영화의 대여 횟수
 * 중간 단계 2: 각 배우가 출연한 영화의 평균 대여 횟수(출연한 영화 SUM/출연한 영화 N)
 */
with rental_count_by_films as (
	select i.film_id,
		   f.title,
		   count(r.rental_id) as rental_count
	  from rental r
	  join inventory i
	    on r.inventory_id = i.inventory_id
	  join film f
	    on i.film_id = f.film_id
	group by f.film_id
)
select a.actor_id,
	   concat(a.first_name, ' ', a.last_name) as actor_name,
	   avg(x.rental_count) as avg_film_rentals,
	   rank() over(order by avg(x.rental_count) desc) as rank_num
  from actor a
  join film_actor fa
    on a.actor_id = fa.actor_id
  join rental_count_by_films as x
    on fa.film_id = x.film_id
group by a.actor_id
order by avg_film_rentals desc
;

commit;