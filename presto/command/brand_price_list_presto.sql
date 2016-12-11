select brand,sum(price) as totalPrice  
from record join brand_dimension on record.bid=brand_dimension.bid
group by brand_dimension.brand
order by totalPrice desc
