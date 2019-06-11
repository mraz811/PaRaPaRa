-- 1. 오라클 SYSTEM 계정 접속
SQLPLUS SYSTEM/비밀번호

-- 2. para 계정 생성
CREATE USER para IDENTIFIED BY para;

-- 3. para 계정에 사용할 tablespace 생성
CREATE TABLESPACE para DATAFILE 'C:\oraclexe\app\oracle\oradata\XE\para.dbf'
SIZE 100M AUTOEXTEND ON NEXT 5M MAXSIZE 150M;

-- 4. para 계정의 default tablespace를 생성한 para로 변경(가용량 unlimited 설정)
ALTER USER para DEFAULT TABLESPACE para QUOTA UNLIMITED ON para;

-- 5. 계정 이름과 tablespace 확인
SELECT USERNAME, DEFAULT_TABLESPACE FROM DBA_USERS;

-- 6. para 계정에 사용할 임시 tablespace 생성
CREATE TEMPORARY TABLESPACE para_temp TEMPFILE 'C:\oraclexe\app\oracle\oradata\XE\para_temp.dbf' SIZE 50M;

-- 7. para 계정의 temporary tablespace를 생성한 para_temp로 변경
ALTER USER para TEMPORARY TABLESPACE para_temp;

-- 8. para 계정에 권한 부여
GRANT CONNECT, RESOURCE, DBA TO para;