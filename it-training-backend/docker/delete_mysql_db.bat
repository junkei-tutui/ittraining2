echo off
echo "MySQLのデータベースを削除します"

docker-compose down
docker-compose rm

rmdir /S /Q .\mysql\data

echo MySQLのデータベースを削除しました
