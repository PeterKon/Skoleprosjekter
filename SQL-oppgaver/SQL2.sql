--4A
--
--Dene faar tilbake
--resultatet fra den rekursive kjoeringen og henter ut det oeverste
--(den korteste) stien til Celina Monsen fra grafen ved hjelp av limit 1.
--Deretter trenger vi bare aa trekke fra to elementer fra lengden paa
--arrayet (som alltid inneholder olav og celina) for aa faa antall
--elementer mellom dem paa korteste vei.
WITH res AS (
    WITH RECURSIVE Tilkoblinger(p1, p2, fraFirma, graf) AS (
        SELECT s.person, f.person, s.selskap, array[s.person, f.person]
        FROM selskapsinfo s, selskapsinfo f
        WHERE s.person = 'Olav Thorsen'
            AND s.selskap = f.selskap AND s.person <> f.person
        UNION ALL
        SELECT t.p1, f.person, s.selskap, t.graf || f.person
        FROM Tilkoblinger t, selskapsinfo s, selskapsinfo f
        WHERE t.p2 = s.person
            AND s.selskap = f.selskap
            AND s.person <> f.person
            AND f.person <> all(t.graf)
            AND s.selskap <> t.fraFirma
    )

    SELECT * FROM Tilkoblinger
    WHERE p2 = 'Celina Monsen'
    ORDER BY array_length(graf, 1) ASC limit 1
)

--Minus 2 for det korrekte antall personer mellom celina og olav,
--minus 1 for det oppgaven ber om.
SELECT array_length(graf, 1) - 1 as resultat FROM res;


--4B
--Denne spoerringen finner sykler og unngaar evig rekursjon ved aa
--sende med en teller "antall" som teller nedover. Vi er interessert
--i stoerre enn 4 og mindre enn 6 fordi mine sykler viser navnet til p1
--paa starten og slutten, saa ett svar med 6 elementer har 5 individuelle.
--Vi sjekker s sin rolle og at neste person i rekken har en av de andre
--rollene slik oppgaven ba om.
WITH RECURSIVE Tilkoblinger(p1, p2, fraFirma, graf, antall, selskapArray) AS (
    SELECT s.person, f.person, s.selskap, array[s.person, f.person], 0, array[s.selskap]
    FROM selskapsinfo s, selskapsinfo f
    WHERE s.selskap = f.selskap AND s.person <> f.person
        AND (s.rolle = 'daglig leder' AND (f.rolle = 'styreleder' OR f.rolle = 'nestleder' OR f.rolle = 'styremedlem'))
    UNION ALL
    SELECT t.p1, f.person, s.selskap, t.graf || f.person, antall + 1, t.selskapArray || f.selskap
    FROM Tilkoblinger t, selskapsinfo s, selskapsinfo f
    WHERE t.p2 = s.person
        AND s.selskap = f.selskap
        AND s.person <> f.person
        AND s.selskap <> t.fraFirma
        AND f.selskap <> all(t.selskapArray)
        AND antall <= 6
)
--Svaret vi faar trimmes til infoen vi oensker, som er alle svar der p1
--er lik p2(sykler) og alle arrayer med lenge 4(3 individer) og 6(5).
SELECT graf, selskapArray FROM Tilkoblinger
WHERE array_length(graf, 1) >= 4 AND array_length(graf, 1) <= 6
AND p1 = p2;


--5C alt 2
--Ideen bak hele denne spoerringen er aa ha en hovedspoerring nede som finner de
--av gender F som har deltatt i flere enn 50 filmer av type C, saa bruke
--list til aa filtrere ut bare de personene som forekommer foerst i alfabetet
--i alle filmer de har deltatt i. Denne spoerringen tar lang lang tid
WITH list AS(
    SELECT p.personid, fp.filmid, p.lastname
    FROM person p
        FULL OUTER JOIN filmparticipation fp
            ON p.personid = fp.personid
    WHERE p.personid IN (
        SELECT checkList.personid FROM (
            SELECT p.personid, fp.filmid, p.lastname
            FROM person p
                FULL OUTER JOIN filmparticipation fp
                    ON p.personid = fp.personid
            ORDER BY p.lastname ASC limit 1
        ) as checkList
        WHERE fp.filmid = checkList.filmid
    )
    ORDER BY p.lastname ASC limit 1
)

SELECT p.firstname, p.lastname, p.personid, COUNT(fi.filmid), fi.filmtype
FROM person p
    INNER JOIN filmparticipation fp
        ON p.personid = fp.personid
    INNER JOIN filmitem fi
        ON fi.filmid = fp.filmid
WHERE p.personid IN (
    SELECT list.personid FROM list
    WHERE fi.filmid = list.filmid
)
GROUP BY p.firstname, p.lastname, p.personid, p.gender, fi.filmtype
HAVING p.gender = 'F' AND fi.filmtype = 'C' AND COUNT(fi.filmid) > 50
ORDER BY COUNT(fi.filmid) DESC;
