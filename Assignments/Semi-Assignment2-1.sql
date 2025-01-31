/* P1) 각 배우의 총 출연 횟수와 순위를 계산하라
 * 중간 단계: 각 배우의 총 출연 횟수 계산
 */
with actor_film as(
	select fa.actor_id,
		   CONCAT(a.first_name, ' ', a.last_name) as actor_name,
		   count(f.film_id) as total_films
	  from actor a
	  join film_actor fa
	    on a.actor_id = fa.actor_id
      join film f
        on fa.film_id = f.film_id
    group by a.actor_id
--     order by total_films desc
)
select actor_id, actor_name, total_films,
	   rank() over(order by total_films desc)
  from actor_film actf
;

commit;