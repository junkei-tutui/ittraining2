select
  /*%expand*/*
from
  mst_generic_code_detail
where
  generic_code_type_id = /* genericCodeTypeId */'a'
  and
  generic_code = /* genericCode */'a'
  and is_delete = 0
