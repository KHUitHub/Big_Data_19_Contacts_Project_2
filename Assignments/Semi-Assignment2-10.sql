/* P 10) 각 카테고리별 평균 대여 횟수의 순위 계산 */
select c.category_id,
	   c.name as category_name,
	   count(r.rental_id)/count(distinct i.film_id) as avg_rentals,
	   rank() over(order by count(r.rental_id)/count(distinct i.film_id) desc)
  from rental r
  join inventory i
    on r.inventory_id = i.inventory_id
  join film f
    on i.film_id = f.film_id
  join film_category fc
    on f.film_id = fc.film_id
  join category c
  	on fc.category_id = c.category_id
group by c.category_id
;