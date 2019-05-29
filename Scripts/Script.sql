

DELETE FROM stock;

SELECT * FROM 

SELECT ALBA_SEQ, TS_SEQ, SUBSTR(TS_DATE, 0, 7) TS_DATE, TS_WORKHOUR, ALBA_NAME, ALBA_PHONE, ALBA_ADDRESS, 
		ALBA_TIMESAL, ALBA_BANK, ALBA_ACCOUNT, ALBA_DELFLAG, ALBA_REGDATE, STORE_CODE
	FROM TIMESHEET JOIN ALBA
		USING(ALBA_SEQ)
			WHERE SUBSTR(TS_DATE, 0, 7) = '2019-05';
			
SELECT ALBA_SEQ, SUBSTR(TS_DATE, 0, 7) TS_DATE, SUM(TS_WORKHOUR)
	FROM TIMESHEET
		GROUP BY ALBA_SEQ, TS_DATE;
	
SELECT ALBA_SEQ, SUBSTR(TS_DATE, 0, 7) TS_DATE, TS_DATETIME
			FROM TIMESHEET
				WHERE ALBA_SEQ = '128'
				AND SUBSTR(TS_DATE, 0, 7) = '2019-05';
			
SELECT TS_SEQ, ALBA_SEQ, SUBSTR(TS_DATE, 0, 7) TS_DATE, TS_DATETIME, TS_WORKHOUR
			FROM TIMESHEET
				WHERE ALBA_SEQ IN (SELECT ALBA_SEQ FROM ALBA WHERE STORE_CODE = 'SEOUL02_18')
				AND SUBSTR(TS_DATE, 0, 7) = '2019-05';
			
 select (TO_DATE('2019/05/28 23:00:00','yyyy/mm/dd hh24:mi:ss') - TO_DATE('2019/05/28 22:00:00','yyyy/mm/dd hh24:mi:ss')) AS worktime
 	FROM dual;
 
SELECT DATEDIFF(dd,'2018-01-01','2018-12-31') + 1 FROM DUAL;

-- WORKTIME_EARLY 가 양수일 경우 WORKTIME_DAY-WORKTIME_EARLY = DAYTIME
-- WORKTIME_NIGHT 가 양수일 경우 WORKTIME_DAY-WORKTIME_NIGHT = DAYTIME
-- WORKTIME_EARLY, WORKTIME_NIGHT 모두 음수일 경우 WORKTIME_DAY = DAYTIME
SELECT 
  (  
    to_date('06:00', 'hh24:mi') -- 고정
    - to_date('21:00', 'hh24:mi') -- 시작
  ) * 24 * 60 / 60
  AS WORKTIME_EARLY,
  ROUND((
    to_date('23:59', 'hh24:mi')    -- 끝
    - to_date('21:00', 'hh24:mi')  -- 시작
  ) * 24 * 60 / 60)
  AS WORKTIME_DAY,
  ROUND((
    to_date('23:59', 'hh24:mi') -- 끝
    - to_date('22:00', 'hh24:mi') -- 고정
  ) * 24 * 60 / 60)
  AS WORKTIME_NIGHT
FROM DUAL

SELECT 
  (  
    to_date('06:00', 'hh24:mi') -- 고정
    - to_date('21:00', 'hh24:mi') -- 시작
  ) * 24 * 60 / 60
  AS WORKTIME_EARLY,
  ROUND((
    to_date('23:59', 'hh24:mi')    -- 끝
    - to_date('21:00', 'hh24:mi')  -- 시작
  ) * 24 * 60 / 60)
  AS WORKTIME_DAY,
  ROUND((
    to_date('23:59', 'hh24:mi') -- 끝
    - to_date('22:00', 'hh24:mi') -- 고정
  ) * 24 * 60 / 60)
  AS WORKTIME_NIGHT
FROM TIMESHEET;

SELECT * FROM ALBA;
			





