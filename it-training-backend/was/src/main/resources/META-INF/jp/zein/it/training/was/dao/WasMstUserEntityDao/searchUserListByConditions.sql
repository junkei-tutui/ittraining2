SELECT /*%expand*/*
FROM mst_user
WHERE
 /*%if likeUserName != null && likeUserName.length() > 0 */
  user_name like /*'%' + likeUserName + '%'*/'%管理%' AND
 /*%end*/
  is_delete = false
 ORDER BY user_name
