select
  /*%expand*/*
from
  mst_partner
where
  partner_id = /* partnerId */'a'
  and is_delete = 0
