select
  /*%expand*/*
from
  mst_color
where
  color_code = /* colorCode */'a'
  and is_delete = 0
