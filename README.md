# projektGrupp16INTE

## Beskrivning av projektet:

## Krav
- JDK 21+
- Maven

## Hur man kör

Köra tester samt skapa en täckningsrapport:

```bash 
mvn test
```

Rapporten som skapas hamnar i `target/site/jacoco/index.html`.

Köra tester och statisk analys:

```bash
mvn verify
```

## Verktyg som används

Junit 5 --> enhetstester
JaCoCo --> mätning av kodtäckning
PMD --> statisk analys 
VisualVM --> profiliering
Git --> versionshantering
Maven --> byggverktyg


