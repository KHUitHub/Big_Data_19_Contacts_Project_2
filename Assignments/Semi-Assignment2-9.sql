/* P9) 각 영화의 평균 대여 기간과 순위 계산 */
select f.film_id,
	   f.title,
	   avg(datediff(r.return_date, r.rental_date)) as avg_rental_duration,
	   rank() over(order by avg(datediff(r.return_date, r.rental_date)) desc)
  from rental r
  join inventory i
    on r.inventory_id = i.inventory_id
  join film f
    on i.film_id = f.film_id
group by f.film_id, f.title
;

commit;