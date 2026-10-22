# Testdesign: ekvivalensklassupdelning för betalningsbelopp

## Indata

### Klass och metod
Klassen är Payment och beloppet kommer in via konstruktorn (new Payment(terminal, amount)). Däremot är det i pay() som vi vill att det ska kontrolleras, eftersom det är därifrån vi anropar terminalen.

### Uppdelning av indata
Money-objektet som skickas in --> beloppet. Och det är detta värde som avgör om betalningen blir giltig eller ej. Alltså, ska vi ens anropa terminalen?

### Enhet
Värdet lagras i fältet amountInOre i Money.java klassen. Värdet är av typen long och beloppet anges alltså i ören. Detta är viktigt eftersom det minsta beloppet inte kommer att vara 1 kr, utan 1 öre. 

## Klasserna

### 0 och negativ är två olika klasser
Eftersom båda borde kasta samma undantag går det att argumentera för att dessa egentligen ska vara en klass, men tänk också på att negativt och 0 inte måste bero på samma orsak. 0 kr kan indikera en tom kundvagn t ex, negativt kan betyda att man ska returnera pengar. Därför om vi ska ha två olika klasser för dessa måste vi behandla dem olika. Vi kan t ex ha två olika felmeddelanden för dem för att kassören ska veta vad som orsakade felet. 

### Inget tak, alla positiva belopp är giltiga
Motivering: i det här projektet väljer vi att ge ansvaret för ett övre tak till banken. 

## Ekvivalensklassuppdelningen

| Klass | Intervall | Giltig | Testvärde | Förväntat resultat |
|-------|-----------|--------|-----------|--------------------|
| K1 | {x∈ℤ : x<0} | Nej | -250 öre | Kasta IllegalArgumentException, meddelande: "beloppet får ej vara negativt", terminalen anropas ej |
| K2 | {x∈ℤ : x=0} | Nej | 0 öre | Kasta IllegalArgumentException, meddelande: "beloppet får ej vara 0", terminalen anropas ej |
| K3 | {x∈ℤ : x>0} | Ja | 550 öre | Inget undantag kastas, terminalen anropas, tillståndet blir PAID/DECLINED beroende på svaret från terminalen |

## Gränsvärden

| Värde | Klass | Varför |
|-------|-------|--------|
|-1 | K1 | Det negativa heltal som ligger närmast 0. Skulle det t ex stå x < -1 i koden istället för x < 0, skulle -1 släppas igenom till terminalen trots att den inte borde göra det
| 0 | K2 | Ligger på båda gränserna och använder vi <= och >= på fel sätt så skulle 0 hamna i fel klass --> behandlas då som negativt (felmeddelande) eller giltigt (terminalen anropas trots att beloppet är 0 kr)
| 1 | K3 | Minsta möjliga giltiga belopp. Skulle det stå x <= 1 istället för x <= 0 skulle valideringen bli fel och ett giltigt köp skulle felaktigt kunna nekas!

## Motivering
Alla värden i en ekvivalensklass kommer att behandlas på samma vis av koden: fungerar ett värde så fungerar alla och på samma vis om ett skulle fallera. Alltså kommer flera värden som ligger i samma klass inte att hitta några nya fel. Vid gränserna är det däremot annorlunda, och de täcks av våra gränsvärden. 

Noll och negativt är två olika klasser trots att de ger väldigt likande fel, men! De ger faktiskt olika felmeddelanden och behandlas därmed på två olika sätt. Kassören behöver veta vad felet beror på och det får denne veta med de olika felmeddelandena. 0 --> kanske en tom kundvagn? Negativt --> Bugg? Eller en felaktig retur!

Eftersom vi har två ogiltiga klasser behöver dessa testas en i taget. Detta beror på att ett fel kan dölja ett annat och har vi bara en ogiltig klass per testfall så vet vi genast vad som orsakade felet. En indata --> varje ogiltig klass behöver sitt eget test.

Avsaknaden av ett övre tak för stora belopp (alltså en gräns) beror på att vi ser detta som bankens, eller terminalens, ansvar. Kassan kommer endast att validera att beloppet är positivt, inte HUR positivt det är. Alltså hanteras redan alldeles för stora belopp av terminalen, som svarar DECLINED. 