-- CREATE USER 'ユーザ名'@'ホスト名' IDENTIFIED BY 'パスワード';
CREATE USER 'training'@'%' IDENTIFIED BY 'training';
CREATE USER 'training'@'localhost' IDENTIFIED BY 'training';

-- GRANT (付与する権限) ON (対象データベース名).(対象テーブル名) TO "(ユーザー名)"@"(ホスト名)";
GRANT ALL ON training.* TO 'training'@'%';
--GRANT SELECT, INSERT ON training.* TO 'training'@'%';

--GRANT ALL ON training.* TO 'training'@'localhost';
--GRANT SELECT, INSERT ON training.* TO 'training'@'localhost';

FLUSH PRIVILEGES;


-- master用ユーザー
CREATE USER 'training'@'%' IDENTIFIED BY 'training';
CREATE USER 'training'@'localhost' IDENTIFIED BY 'training';

GRANT ALL ON training.* TO 'training'@'%';

FLUSH PRIVILEGES;



-- TeamA用ユーザー
CREATE USER 'teama'@'%' IDENTIFIED BY 'training';
CREATE USER 'teama'@'localhost' IDENTIFIED BY 'training';

DROP DATABASE IF EXISTS teama;
CREATE DATABASE teama;

GRANT ALL ON teama.* TO 'teama'@'%';

FLUSH PRIVILEGES;


-- TeamB用ユーザー
CREATE USER 'teamb'@'%' IDENTIFIED BY 'training';
CREATE USER 'teamb'@'localhost' IDENTIFIED BY 'training';

DROP DATABASE IF EXISTS teamb;
CREATE DATABASE teamb;

GRANT ALL ON teamb.* TO 'teamb'@'%';

FLUSH PRIVILEGES;


-- TeamC用ユーザー
CREATE USER 'teamc'@'%' IDENTIFIED BY 'training';
CREATE USER 'teamc'@'localhost' IDENTIFIED BY 'training';

DROP DATABASE IF EXISTS teamc;
CREATE DATABASE teamc;

GRANT ALL ON teamc.* TO 'teamc'@'%';

FLUSH PRIVILEGES;
