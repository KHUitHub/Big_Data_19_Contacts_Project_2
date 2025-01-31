/* P7) 각 고객의 평균 대여 기간과 순위 계산 */
select c.customer_id,
	   concat(c.first_name, ' ', c.last_name) as customer_name,
	   avg(datediff(r.return_date, r.rental_date)) as avg_rental_duration,
	   rank() over(order by avg(datediff(r.return_date, r.rental_date)) desc) as rank_num
  from customer c
  join rental r
    on c.customer_id = r.customer_id
group by c.customer_id
;

commit;