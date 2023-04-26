select
  /*%expand*/*
from
  mst_size
where
  size_code = /* sizeCode */'a'
  and is_delete = 0
