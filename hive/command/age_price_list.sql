select cast(DATEDIFF(CURRENT_DATE, birth)/365 as int) as age,
sum(price) as totalPrice
from record join user_dimension on record.uid=user_dimension.uid
group by cast(DATEDIFF(CURRENT_DATE, birth)/365 as int)
order by totalPrice desc;
