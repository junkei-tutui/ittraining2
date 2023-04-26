select
  /*%expand*/*
from
  tbl_order_detail
where
  order_no = /* orderNo */1
  and
  line_no = /* lineNo */1
  and is_delete = 0
