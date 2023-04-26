select
  /*%expand*/*
from
  tbl_order
where
  order_no = /* orderNo */1
  and is_delete = 0
