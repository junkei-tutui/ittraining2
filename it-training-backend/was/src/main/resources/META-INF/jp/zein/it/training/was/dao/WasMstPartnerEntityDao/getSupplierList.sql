SELECT /*%expand*/*
FROM mst_partner
WHERE
  partner_type IN (0,2) AND
  is_delete = false
 ORDER BY partner_name