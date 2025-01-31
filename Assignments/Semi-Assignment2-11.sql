/* P 11) 대여 횟수가 가장 많은 고객을 나라별로 구분하여 순위 계산 */
select ctr.country, c.customer_id,
	   concat(c.first_name, ' ', c.last_name) as customer_name,
	   count(r.rental_id) as total_rentals,
	   rank() over(partition by ctr.country order by count(r.rental_id) desc)
  from rental r
  join customer c
    on r.customer_id = c.customer_id
  join address a
    on c.address_id = a.address_id
  join city ct
    on a.city_id = ct.city_id
  join country ctr
    on ct.country_id = ctr.country_id
group by c.customer_id
;

commit;