/* 문제 1: 매장별 월별 매출 분석
 * P) 매장의 월별 매출 조회.
 * S) 1. 매장별, 월별 매출 데이터를 집계:
 * 		payment 테이블에서 결제 금액을 집계하고, 결제 날짜를 기준으로 월별 데이터를 그룹화
 */
select s.store_id as si,
	   DATE_FORMAT(p.payment_date, '%Y-%m') as df,	-- 결제 날짜 형식 변경
	   SUM(p.amount) as total_revenue	-- 총매출 집계
  from staff s	-- staff 위치 고정 -> inventory 테이블과 달리 판매 시 매장 데이터 정확
  join rental r
  	on s.staff_id = r.staff_id
  join payment p	-- 결제 정보까지 join
  	on p.rental_id = r.rental_id
group by s.store_id, df
order by si, df
;


/* 문제 2. 특정 배우가 출연한 영화의 매출 기여도 분석
 * P) 특정 배우(예: "Tim HACKMAN")가 출연한 영화가 총 매출에 얼마나 기여했는지 조회
 * S)	1. 배우 ID 조회: actor 테이블에서 해당 배우의 ID를 조회.
 * 		2. 영화 목록 조회: film_actor와 film 테이블을 연결하여 해당 배우가 출연한 영화 목록을 확인.
 */
select a.first_name,
	   a.last_name,
	   f.title as film_title,
	   SUM(p.amount) as total_revenue	-- 총 매출 계산
-- 여기부터 where절 전 까지 actor -> payment 연결
  from actor a
  join film_actor fa
	on fa.actor_id = a.actor_id
  join film f
	on f.film_id = fa.film_id
  join inventory i
	on i.film_id = f.film_id
  join rental r
	on r.inventory_id = i.inventory_id
  join payment p
	on p.rental_id = r.rental_id
 where a.first_name = 'TIM'	-- 이름 조건
 group by first_name, last_name, film_title	-- film_title 중복 제거
order by total_revenue desc	-- 매출 기준 내림차순
;


/* 문제 3. 가장 신속한 대여 및 반환 서비스 제공 매장 분석
 * P) 어떤 매장이 가장 신속하게 대여 및 반환 처리를 하는지 조회
 * S)	1. 대여 및 반환 간의 시간 차이 계산: AVG(TIMESTAMPDIFF(대여일, 반환일)) 사용용
 * 			- rental_date와 return_date를 사용해 반환까지 걸린 시간을 평균으로 계산.
 */
select i.store_id,
       AVG(TIMESTAMPDIFF(Hour, r.rental_date, r.return_date)) as avg_return_time
  from inventory i			-- 대여 및 반환 매커니즘은 재고관리와 직결되므로 inventory table 사용 -> 스토어 id column으로 활용
  join rental r				-- 대여/반환 
    on r.inventory_id = i.inventory_id
group by i.store_id			-- store_id 중복 값 그루핑 
order by i.store_id desc	-- store_id 내림차순
;


/* 문제 4. 대여되지 않은 영화 목록 찾기
 * P) 대여되지 않은 영화 조회
 * S) 1. 대여된 영화와 대여되지 않은 영화 비교
 * 		- inventory Table과 rental Table을 join하여 대여되지 않은 영화를 찾음.
 * 		- 결과 화면 43건 조회되어야 함.
 */
select f.title
  from rental r
  right join inventory i	-- 대여되지 않은 재고까지 조회
    on r.inventory_id = i.inventory_id
  right join film f	-- 모든 영화 제목 조회
    on f.film_id = i.film_id
  where r.rental_id is null	-- 대여 정보가 없는 row만 추출
;


/*
 * 문제 5. 고객의 활동성 분석
 * P) 고객의 대여 횟수와 평균 결제 금액을 기반으로 상위 5명의 VIP 고객을 식별 조회.
 * S) 1. 고객별 대여 횟수와 평균 결제 금액 계산:
 * 		- rental과 payment Table을 join하여 고객별 데이터를 집계
 */
select c.customer_id,
	   Concat(c.first_name, ' ', c.last_name) as customer_name,	-- 이름과 성을 합치는 함수
	   Count(r.rental_id) as rental_count,	-- 
	   AVG(p.amount) as avg_payment
  from customer c
  join rental r
    on c.customer_id = r.customer_id	-- 대여 고객 조회
  join payment p
    on r.rental_id = p.rental_id	-- 결제를 진행한 대여 건 조회
group by c.customer_id	-- 중복 데이터(고객) 한 개로 통일
order by rental_count desc, avg_payment desc	--  rental_count 1순위 내림차순 정렬, 중복되는 숫자가 있을 경우 avg_payment로 내림차순 정렬
limit 5
;
-- GPT 답변
SELECT c.customer_id, 
       CONCAT(c.first_name, ' ', c.last_name) AS customer_name,	
       COUNT(r.rental_id) AS rental_count,	
       AVG(p.amount) AS avg_payment
FROM customer c
JOIN rental r ON c.customer_id = r.customer_id
JOIN payment p ON r.rental_id = p.rental_id
GROUP BY c.customer_id	
ORDER BY rental_count DESC, avg_payment desc	
LIMIT 5
;


/* 문제 6. 특정 카테고리의 대여 트렌드 분석
 * P) 특정 카테고리(예: "Action"에 대해)의 대여 트렌드를 분석하여 월별 대여 횟수 조회
 * S)
 * 	1. 카테고리 ID 조회:
 * 		- category Table에서 해당 카테고리 ID를 조회.
 * 	2. 월별 대여 횟수 집계
 * 		- film_category와 rental을 연결하여 대여 데이터를 분석.
 */
select DATE_FORMAT(r.rental_date, '%Y-%m') as month,	-- 대여 날짜 형식 변경
	   count(r.rental_id) as rental_count	-- 월별 대여 횟수 조회
-- 여기부터 where절 전 까지 rental -> category 연결
  from rental r
  join inventory i
	on r.inventory_id = i.inventory_id
  join film f
	on i.film_id = f.film_id
  join film_category fc
	on f.film_id = fc.film_id
  join category c
    on fc.category_id = c.category_id
 where c.name = 'Action'	-- '액션' 카테고리 조회
group by month
order by month
;


/* 문제 7.특정 기간 동안 대여된 영화 목록
 * S) 특정 기간(예: 2005년 5월 1일부터 2005년 5월 31일) 동안 5회 대여된 영화 목록과 대여 건수 조회
 * P) 
 * 	1. 기간 조건 추가:
 * 		- rental_date를 기준으로 특정 기간에 해당하는 대여 데이터를 필터링.
 * 	2. 인라인 뷰 사용
 */
With iv as (	-- 인라인 뷰
	select f.title as film_title,
		   count(f.title) as rental_count
	  from rental r
	  join inventory i
	    on r.inventory_id = i.inventory_id	-- 대여 내역이 있는 재고만 조회
	  join film f
	    on i.film_id = f.film_id	-- 영화 제목 표기를 위한 join
	 where r.rental_date between '2005-05-01' and '2005-05-31'	-- 특정 기간 설정
	 group by f.title	-- 제목 중복 제거
) Select *
    From iv
   Where rental_count = 5
;

  
/* 문제 8. 고객의 평균 대여 간격 분석
 * P) 고객들이 평균적으로 얼마나 자주 영화를 대여하는지 분석하여 평균 대여 시간 가장 짧은 고객 5명 조회
 * S)
 * 	1. 대여 간격 계산
 * 		- 동일 고객의 대여 날짜(rental_date)를 기준으로 대여 간격을 계산
 * 	2. 인라인 뷰 사용
 */
with iv as (	-- 인라인 뷰
	select c.customer_id,
		   CONCAT(c.first_name, ' ', c.last_name) as customer_name,	-- 이름 통합
		   DateDiff(LEAD(r.rental_date)	-- 회원별 대여 간격 계산(Lead 함수: 해당 행의 다음 값 반환)
	   over (Partition by customer_id	-- 회원 별 구분
	   order by r.rental_date),	-- 날짜별 오름차순
	   r.rental_date) as rental_date_sum	-- Lead로 반환된 값의 이전 값
	  from rental r
	  join customer c
	    on r.customer_id = c.customer_id
)
select customer_id,
	   customer_name,
	   Avg(rental_date_sum) as avg_rental_interval	-- 대여 간격 평균 계산
  from iv
group by customer_id
order by avg_rental_interval
limit 5
;



WITH rental_intervals AS (
    SELECT r.customer_id,
           DATEDIFF(LEAD(r.rental_date)
           OVER (PARTITION BY r.customer_id
           ORDER BY r.rental_date),
           r.rental_date) AS interval_days
    FROM rental r
)
SELECT c.customer_id,
       CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
       AVG(ri.interval_days) AS avg_interval_days
FROM rental_intervals ri
JOIN customer c ON ri.customer_id = c.customer_id
WHERE ri.interval_days IS NOT NULL
GROUP BY c.customer_id
ORDER BY avg_interval_days asc
LIMIT 5;


commit;