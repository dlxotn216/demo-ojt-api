GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '%password%' WITH GRANT OPTION;

https://hub.docker.com/_/mariadb

http://eastflag.co.kr/spring/rest-tutorial/spring-rest_database/

https://mariadb.com/kb/en/library/installing-and-using-mariadb-via-docker/

https://gongzza.github.io/docker/db-sample/

https://sarc.io/index.php/mariadb/1113-maria-db-show

https://jojoldu.tistory.com/269


# RDS Mariadb
parameter group에서 character set을 utf8 변경 후 아래 쿼리로 확인  
* show variables like 'c%';

아래 두 변수가 백날 수정해도 utf-8이 적용되지 않는다  
* character_set_database  
* collation_database

아래의 DDL을 통해 수정후 적용 확인 완료
 
* ALTER DATABASE demo DEFAULT COLLATE utf8_unicode_ci;   
  -> Change the MySQL database collation to UTF-8
* ALTER DATABASE demo DEFAULT CHARACTER SET utf8;   
  -> Change the MySQL database character set to UTF-8 