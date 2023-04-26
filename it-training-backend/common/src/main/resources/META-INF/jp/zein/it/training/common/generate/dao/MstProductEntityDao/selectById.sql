select
  /*%expand*/*
from
  mst_product
where
  product_code = /* productCode */'a'
  and is_delete = 0
