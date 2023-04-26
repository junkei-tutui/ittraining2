SELECT /*%expand*/*
FROM mst_partner
WHERE
 /*%if likePartnerName != null && likePartnerName.length() > 0 */
  partner_name like /*'%' + likePartnerName + '%'*/'%会社%' AND
 /*%end*/
  is_delete = false
 ORDER BY partner_name