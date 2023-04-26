select
  /*%expand*/*
from
  sys_config
where
  variable = /* variable */'a'
  and is_delete = 0
