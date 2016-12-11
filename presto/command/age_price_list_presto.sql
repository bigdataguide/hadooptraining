select cast((year(CURRENT_DATE)-year(birth)) as integer) as age,sum(price) as totalPrice
from record join user_dimension on record.uid=user_dimension.uid
group by cast((year(CURRENT_DATE)-year(birth)) as integer)
order by totalPrice desc
