--Part 1
-- COLUMNS NAME : TYPE
-- ID     :      INTEGER
-- NAME    :    VARCHAR
-- EMPLOYER   :   VARCHAR
-- SKILLS    :     VARCHAR
--Part 2

SELECT name
FROM employer
WHERE location = "St. Louis City";

--Part 3

DROP TABLE job;

--Part 4

SELECT name
FROM job
WHERE job.id IN (SELECT jobs_id FROM job_skills);
ORDER BY name ASC;
