select gender, brand,count(*) as purchase_count 
from record_orc join user_dimension_orc on record_orc.uid=user_dimension_orc.uid 
join brand_dimension_orc on record_orc.bid=brand_dimension_orc.bid 
group by gender, brand
order by gender, purchase_count DESC

