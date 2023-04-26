select
  /*%expand*/*
from
  mst_item
where
  item_code = /* itemCode */'a'
  and is_delete = 0
