/* P5) 각 카테고리에서 가장 많이 대여된 영화의 순위
 * 중간 단계: 각 카테고리에서 가장 많이 대여된 영화
 */
with x as (
	select c.category_id,
		   c.name as category_name,
		   i.film_id,
		   f.title,
		   count(r.rental_id) as total_rentals
	  from category c
	  join film_category fc
	    on c.category_id = fc.category_id
	  join film f
	    on fc.film_id = f.film_id
	  join inventory i
	    on f.film_id = i.film_id
	  join rental r
	    on i.inventory_id = r.inventory_id
	group by i.film_id, category_id
)
select category_id, category_name, film_id, title, total_rentals,
	   row_number() over(partition by category_id order by total_rentals desc) as rank_num
  from x
;

commit;