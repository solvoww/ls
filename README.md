# Directory Viewer


## Wstęp

Aplikacja prezentuje strukturę plików/folderów w oknie przeglądarki.


## Wymagania

### Node.js

Wersja: 10.3.0 (zawierająca npm w wersji 6.1.0)

### Angular CLI

Instalacja w oknie wiersza poleceń (z uprawnieniami administratora) : 'npm install -g @angular/cli'

### Java

Wersja co najmniej 1.8.0


## Uruchomienie aplikacji

- Pobrać i rozpakować aplikację do dowolnego folderu. Zawartość folderu tworzą dwa podfoldery: ls-app (część serwerowa) i ls-web (interfejs użytkownika);
- Otworzyć edytorem tekstu (notatnikiem) plik application.yml w folderze ls-app/target/classes i ustawić parametr rootPath na folder, który będzie głównym folderem struktury plików/folderów.
- Uruchomić okno poleceń, dojść do folderu ls-app/target i uruchomić część serwerową aplikacji poleceniem 'java -jar ls-app-0.0.1-SNAPSHOT.jar';
- Uruchomić okno poleceń, dojść do folderu ls-web i uruchomić interfejs aplikacji poleceniem 'ng serve';
- Otworzyć przeglądarkę, podając w pasku adresu adres 'localhost:4200'.


## Funkcjonalność aplikacji

1.            Listowanie zawartości folderu (root od którego zaczynamy ma być parametrem konfiguracyjnym), prezentowane atrybuty:

a.            Rodzaj (plik/folder) w formie ikony

b.            Nazwa pliku/folderu

c.            Data modyfikacji

d.            Rozmiar (dla folderu puste)

e.            Atrybuty (readonly, systemowy itp.)

2.            Akcje:

a.            Przejście do zawartości wskazanego folderu (tylko katalogi) – akcja domyślna (kliknięcie w nazwę)

b.            Pobieranie pliku (tylko pliki) – akcja domyślna (kliknięcie w nazwę), pobiera i otwiera plik w domyślnej aplikacji

c.            Zapisz (tylko plik) – akcja w formie ikony dla rekordu, pobranie i zapis (bez otwierania)

d.            Zmiana nazwy (pliku i foldery) – akcja w formie ikony, po wywołaniu okno z możliwością wpisania nazwy, podpowiada się nazwa dotychczasowa

e.            Usuń – (pliki i foldery) akcja w formie ikony, przed wykonaniem popup z prośbą o potwierdzenie zamiaru usunięcia, w przypadku folder walidacja czy jest pusty, jeżeli nie zablokowanie akcji z komunikatem

f.             Powrót do folderu nadrzędnego – akcja w formie pierwszego rekordu na liście [..], akcja niedostępna dla folderu root, nie podlega sortowaniu

3.            Inne wymaganie

a.            Aktualny kontekst – ponad listą prezentacja aktualnej ścieżki (np. /home/Dokumenty/Prezentacje), opcjonalnie przechodzenie do folderu po kliknięciu miejsca na ścieżce

b.            Możliwość sortowania zawartości folderu po wszystkich kolumnach

