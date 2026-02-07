# Viikko 4: Tehtävälista-sovellus

## Esittely
- **Video:** <https://youtu.be/gxkIW86S53Y>


## Navigointi Jetpack Composessa
Jetpack Composessa navigointi tarkoittaa näkymien (Composable-funktioiden) välistä siirtymistä ilman perinteisiä Activity- tai Fragment-vaihtoja. Navigointi hoidetaan tilapohjaisesti, ja näkymä vaihtuu, kun navigaatiotila muuttuu.

## NavController ja NavHost
NavController vastaa navigoinnin ohjaamisesta:
- Se tietää, mikä näkymä on tällä hetkellä näkyvissä
- Sitä käytetään siirtymiseen reitiltä toiselle (navigate, popBackStack)

NavHost määrittelee sovelluksen navigaatiokaavion:
- Sisältää kaikki reitit (routes)
- Määrittelee, mikä Composable näytetään milläkin reitillä


## Sovelluksen navigaatiorakenne
Sovelluksessa on kolme pääruutua:
- HomeScreen (ROUTE_HOME)
- CalendarScreen (ROUTE_CALENDAR)
- SettingsScreen (ROUTE_SETTINGS)

Navigointi toimii seuraavasti:
- HomeScreenin yläpalkista voidaan siirtyä CalendarScreenille tai SettingsScreenille
- CalendarScreeniltä voidaan palata takaisin HomeScreenille
- SettingsScreeniltä voidaan palata takaisin HomeScreenille

Navigointi on toteutettu MainActivityssa NavHostin avulla, ja NavController välitetään callbackien kautta UI-komponenteille.


## Jaettu ViewModel kahdelle ruudulle
Sekä HomeScreen että CalendarScreen käyttävät samaa ViewModel-instanssia:
- ViewModel luodaan NavHostin tasolla
- ViewModel (TaskViewModel) sisältää kaiken sovelluksen tilan ja logiikan
- Sama tila (StateFlow) jaetaan molemmille ruuduille
- Kaikki muutokset (add, update, delete, toggle) näkyvät automaattisesti molemmissa näkymissä

## ViewModelin tilan jakaminen
ViewModel käyttää StateFlowta sovelluksen tilan hallintaan.
- Tehtävälista ja suodatus tallennetaan TaskUiState-data classiin
- UI lukee tilan collectAsState()-kutsulla
- Kun ViewModel päivittää tilaa (esim. addTask, updateTask), Compose tekee automaattisesti uudelleenpiirron


## CalendarScreen
CalendarScreen esittää tehtävät kalenterimaisesti ryhmiteltynä dueDate-kentän perusteella.
- Tehtävät ryhmitellään groupBy { it.dueDate }
- Jokainen päivämäärä näytetään otsikkona
- Sen alle listataan kyseisen päivän tehtävät

Käyttäjä voi avata tehtävän muokkausdialogin myös kalenterinäkymästä

## AlertDialog
#### addTask (AddDialog)
Avautuu HomeScreeniltä "New"-painikkeesta.
- Sisältää tekstikentät:
    - title
    - description
    - dueDate
- "Save" -> kutsuu viewModel.addTask()
- "Cancel" -> sulkee dialogin ilman muutoksia

#### editTask (DetailDialog)
Avautuu, kun käyttäjä painaa tehtävää listassa tai kalenterissa.
- Kentät on esitäytetty valitun tehtävän tiedoilla
- "Save" -> viewModel.updateTask()
- "Delete" -> viewModel.removeTask()
- "Cancel" -> sulkee dialogin