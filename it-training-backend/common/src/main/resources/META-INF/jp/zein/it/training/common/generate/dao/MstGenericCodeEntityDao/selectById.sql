select
  /*%expand*/*
from
  mst_generic_code
where
  generic_code_type_id = /* genericCodeTypeId */'a'
  and is_delete = 0
