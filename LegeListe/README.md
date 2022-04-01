Last ned `hovedprogram.java` og legg denne filen i samme mappe som koden din.

__NB: Hvis du bruker Windows, bør du unngå de særnorske tegnene (æøå) når programmet leser fra terminal!__ (Ved innlesning fra fil skal dette ikke være noe problem.)

### Kompilering
Kompilér alle klassene dine med
```
javac *.java
```

#### Windows
På Windows bør du bruke en opsjon for å angi at tegnsettet i filen er UTF-8 for å få de særnorske tegnene skrevet ut riktig. (Pass på at alle `.java`-filene dine er lagret i UTF-8.)

```
javac -encoding utf-8 *.java
```

### Bruk
Programmet kjøres med
```
java Oblig4 <inputfil>
```

## Inputfiler
Du finner også inputfilene på http://folk.uio.no/inf1010/v17/oblig/4/samples

### Automatisk nedlastning og utpakking
Disse kommandoene bør fungere på Linux og Mac. Sørg for at du er i riktig mappe.

#### Med zip
```sh
wget http://folk.uio.no/inf1010/v17/oblig/4/samples/alle.zip
unzip alle.zip
```

#### Med tarball
```sh
wget http://folk.uio.no/inf1010/v17/oblig/4/samples/alle.tgz
tar -xvf alle.tgz
```
