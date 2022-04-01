--1B
WITH res AS (
SELECT t.togNr, t.endeSt, TIMEDIFF(tb.avgangsTid, t.ankomsttid) as diff, tb.stasjon
FROM Tog t
	INNER JOIN TogTabell tb
		ON t.togNr = tb.togNr
WHERE t.endeSt = tb.stasjon)

SELECT togNr
FROM res
WHERE diff >= ALL(SELECT diff FROM res);


--3B
WITH res AS (
    SELECT p.fnr, p.etternavn, e.etternavn1, e.etternavn2, p.adresse
    FROM Person p
        INNER JOIN Ekteskap e
            ON p.fnr = e.fnr1
)

SELECT

--4A
WITH RECURSIVE Tilkoblinger(person, antall) AS (
    SELECT s.person, 0
    FROM selskapsinfo s
    WHERE s.person = 'Olav Thorsen'
    UNION
    SELECT g.person, t.antall + 1
    FROM Tilkoblinger t JOIN selskapsinfo g ON g.person = 'Celina Monsen'
    WHERE t.antall < 10
)

SELECT * FROM Tilkoblinger;

--1
WITH RECURSIVE Tilkoblinger(fraPerson, fraFirma, personOversikt, antall) AS (
	SELECT s.person, s.selskap, array[s.person, f.person], 0
	FROM selskapsinfo s
	INNER JOIN selskapsinfo f ON s.selskap = f.selskap
	WHERE s.person = 'Olav Thorsen' AND f.person != 'Olav Thorsen'
	UNION
	FROM Tilkoblinger t
	INNER JOIN selskapsinfo s ON s.person = 'Celina Monsen' AND
)

--2 (Beste)
WITH RECURSIVE Tilkoblinger(fraPerson, fraFirma, selskapOversikt, antall) AS (
	SELECT s.person, s.selskap, array[s.selskap], 0
	FROM selskapsinfo s
	INNER JOIN selskapsinfo f ON s.selskap = f.selskap
	WHERE s.person = 'Olav Thorsen' AND f.person != 'Olav Thorsen'
	UNION
	SELECT t.fraPerson, s.selskap, t.selskapOversikt || s.selskap, antall + 1
	FROM Tilkoblinger t
	INNER JOIN selskapsinfo s ON s.selskap = t.fraFirma
	WHERE s.selskap <> ALL(selskapOversikt) AND s.person != 'Celina Monsen'
)

SELECT * FROM Tilkoblinger;

--3
WITH RECURSIVE Tilkoblinger(fraPerson, fraFirma, selskapOversikt, antall) AS (
	SELECT s.person, s.selskap, array[s.selskap], 0
	FROM selskapsinfo s
	INNER JOIN selskapsinfo f ON s.selskap = f.selskap
	WHERE s.person = 'Olav Thorsen' AND f.person != 'Olav Thorsen'
	UNION
	SELECT t.fraPerson, s.selskap, t.selskapOversikt || t.fraFirma, antall + 1
	FROM Tilkoblinger t
	INNER JOIN selskapsinfo s ON s.selskap = t.fraFirma
	WHERE s.selskap <> ALL(selskapOversikt) AND s.person != 'Celina Monsen'
)

SELECT * FROM Tilkoblinger;
