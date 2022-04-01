--Oppgave 1
SELECT filmcharacter, COUNT(*) as occurences FROM filmcharacter
GROUP BY filmcharacter HAVING (COUNT(*)) > 800
ORDER BY COUNT(*) DESC;


--Oppgave 2
SELECT person.personid, person.lastname, film.title, filmcountry.country
FROM person
    INNER JOIN filmparticipation
        ON person.personid = filmparticipation.personid
    INNER JOIN filmcharacter
        ON filmparticipation.partid = filmcharacter.partid
    INNER JOIN film
        ON film.filmid = filmparticipation.filmid
    INNER JOIN filmcountry
        ON filmcountry.filmid = filmparticipation.filmid
WHERE person.firstname = 'Ingrid' AND filmcharacter.filmcharacter = 'Ingrid';


--Oppgave 3
SELECT person.firstname, person.lastname, filmcharacter.filmcharacter, film.title
FROM person
    INNER JOIN filmparticipation
        ON person.personid = filmparticipation.personid
    INNER JOIN filmcharacter
        ON filmparticipation.partid = filmcharacter.partid
    INNER JOIN film
        ON film.filmid = filmparticipation.filmid
WHERE person.personid = 3914169 AND filmcharacter.filmcharacter <> '' AND filmcharacter.filmcharacter IS NOT NULL;


--Oppgave 4
--Litt hacky maate med limit 1, men returnerer korrekt
SELECT person.firstname, person.lastname, person.personid, COUNT(filmcharacter)
FROM PERSON
    INNER JOIN filmparticipation
        ON person.personid = filmparticipation.personid
    INNER JOIN filmcharacter
        ON filmparticipation.partid = filmcharacter.partid
GROUP BY person.firstname, person.lastname, person.personid, filmcharacter.filmcharacter
HAVING filmcharacter.filmcharacter = 'Ingrid'
ORDER BY COUNT(filmcharacter) DESC limit 1;


--Oppgave 5
--Litt hacky maate med limit 1, men returnerer korrekt
SELECT filmcharacter.filmcharacter, COUNT(*)
FROM filmcharacter
    INNER JOIN filmparticipation
        ON filmcharacter.partid = filmparticipation.partid
    INNER JOIN person
        ON person.personid = filmparticipation.personid
GROUP BY filmcharacter.filmcharacter, person.firstname
HAVING filmcharacter.filmcharacter = person.firstname
ORDER BY COUNT(*) DESC limit 1;


--Oppgave 6
SELECT film.title, filmitem.filmtype, filmparticipation.parttype, COUNT(filmparticipation)
FROM filmitem
    INNER JOIN film
        ON filmitem.filmid = film.filmid
    INNER JOIN filmparticipation
        ON filmparticipation.filmid = filmitem.filmid
GROUP BY film.title, filmitem.filmtype, filmparticipation.parttype
HAVING film.title LIKE '%Lord of the Rings%' AND filmitem.filmtype = 'C';


--Oppgave 7
SELECT noir.title
FROM (
    SELECT film.title, film.filmid
    FROM film
    INNER JOIN filmgenre
        ON film.filmid = filmgenre.filmid
    GROUP BY film.title, filmgenre.genre, film.filmid
    HAVING filmgenre.genre = 'Film-Noir'
) as noir INNER JOIN (
    SELECT film.title, film.filmid
    FROM film
    INNER JOIN filmgenre
        ON film.filmid = filmgenre.filmid
    GROUP BY film.title, filmgenre.genre, film.filmid
    HAVING filmgenre.genre = 'Comedy'
) as com ON noir.filmid = com.filmid;


--Oppgave 8
--den gir en tabell med alle filmer
--som inneholder "Antoine " joinet med en tabell over alle filmer og en rad
--der det staar antall sjangre den filmen har.
SELECT b.filmid, b.title, a.genreCount
FROM (
    SELECT film.filmid, COUNT(filmgenre) as genreCount
    FROM filmgenre
        RIGHT OUTER JOIN film
            ON filmgenre.filmid = film.filmid
    GROUP BY film.filmid
) AS a RIGHT OUTER JOIN(
    SELECT film.title, film.filmid
    FROM film
    WHERE film.title LIKE '%Antoine %'
) AS b ON a.filmid = b.filmid
GROUP BY b.filmid, b.title, a.genreCount;
