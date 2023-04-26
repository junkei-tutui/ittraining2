select
  /*%expand*/*
from
  mst_user
where
  user_id = /* userId */'a'
  and is_delete = 0
