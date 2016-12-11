select province,sum(price) as totalPrice
from record join user_dimension on record.uid=user_dimension.uid
group by user_dimension.province
order by totalPrice desc;
