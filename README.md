# Projekat2-LMS

Programiranje u Javi 2025/26
Student : Kerim Bukvić

# Opis
Kao zadatak za projekat 2 iz predmeta "Programiranje u Javi" imamo kreirati aplikaciju za upravljanje životnim navikama (Life Management System).
Ona treba da sarži Login/Register dio te Main menu sa opcijama (View my profile,FinanceApp i Modules koji treba da sadrži 4 modula).

# Login/register dio
Završena implementacija login/register dijela aplikacije. Korisniku se otvara prozor u kome unosi korisničko ime i lozinku koja je skrivena(hashirana)
korištenjem SHA-256 algoritma. Prilikom registracije vrši se provjera da li korisničko ime već postoji u bazi i poklapa li se unesenaa lozinka
s hash lozinkom u bazi. Login/Register dio mog projekta zamišljen je bez rola/uloga (superadmin,admin,user).
Unutar LoginWindow i RegisterWindow ubačen meni sa opcijama za izbor boje/teme koje su omogućene samo u Login i Register prozoru.
Korisnik može da bira paletu boja (plava,zelena,narandžasta,roza,dark,cyberpunk).

# Main menu dio
Unutar main menu prozora imamo opcije za prikaz/uređivanje korisničkog profila,financeApp i module. 
Uređivanje korisničkog profila sadrži opcije za izmjenu korisničkog imena(username),njegove lozinke,brisanje korisnika kao i opciju prikaza svih korisnika unutar aplikacije.

# Finance app dio
Implementiran projekat 1 - Finance App u main menu dio projekta. Zadržana logika i kod iz projekta 1 uz odrađene modifikacije na GUI dijelu. 
Također promijenjena baza podataka tako da se i FinanceApp dio sada unosi i čuva u sklopu baze podataka Life Management System-a.

# Module/tracker dio
Kreirana ModulesWindow klasa koja sadrži četiri dugmeta koja vode na zasebne trackere/module.

Kreirana klasa MongoDBConnection za spajanje pojedinačnih trackera i njihovih podataka u bazu.

Kreiran prvi tracker - Sleep Tracker koji korisniku omogućava da unese datum i broj sati spavanja gdje pritiskom na dugme "Dodaj" podaci se prikazuju u tabeli i upisuju u kolekciju u bazi. Ispod tabele nalazi se dugme za prikaz statistike koji korisniku izbacuje tabelu sa datumima i satima spavanja,a na dnu tabele izbacuje prosječno vrijeme spavanja u satima na osnovu unesenih podataka.

Kreiran drugi tracker - Mood Tracker koji korisniku omogućava da unese datum, izabere raspoloženje kroz opcije padajućeg menija, unese opis zašto ima to raspoloženje i pritiskom na dugme "Dodaj" podaci se prikazuju u tabeli i upisuju u svoju kolekciju u bazi. Ispod tabele se nalazi dugme za prikazivanje statistike koje korisniku izbacuje broj prikaza svakog unesenog raspoloženja.

Kreiran treći tracker - Fitness Tracker koji korisniku omogućava unos datuma, biranje vrste aktivnosti kroz padajući meni, opis izabrane aktivnosti, označavanje završetka izabrane aktivnosti. Pritiskom na dugme "Dodaj" uneseni podaci se prikazuju u tabeli i upisuju u svoju kolekciju u bazi. Ispod tabele imamo dugme za prikaz statistike koja korisniku prikazuje ukupni broj aktivnosti kao i broj završenih i nezavršenih aktivnosti.

Kreiran i posljednji 4. tracker - Movie Tracker koji korisniku omogućava da unese naziv filma, izabere žanr kroz padajući meni, izabere ocjenu filma i pritiskom na dugme "Dodaj" uneseni podaci se prikazuju u tabeli i upisuju u svoju kolekciju u bazi. Ispod tabele se nalazi dugme za prkzivanje statistike koja korisniku izbacuje tabelu sa unesenim podacima i na dnu prosječnu ocjenu pregledanih filmova korisnika.

# Način korištenja aplikacije
1. Pokrenemo aplikaciju - App.java
2. Nakon pokretanja aplikacije otvorit će nam se prozor gdje se prijavimo ili registrujemo ukoliko nemamo profil
3. Nakon prijave ili registracije prikazat će se main menu prozor sa opcijama (uređivanje profila korisnika, prikaz Finance App-a i Moduli). Uređivanje profila korisnika omogućava promjenu korisničkog imena i lozinke, brisanje korisnika kao i prikaz svih korisnika aplikacije. Finance App omogućava prikaz praćenja finansijskih navika korisnika gdje možemo dodavati iznos prihoda/rashoda, birati kategoriju prihoda/rashoda i dodati u tabelu koja se prikazuje ispod unesenih podataka a koja prikazuje ukupne prihode,rashode i trenutni balans korisnika. Izborom opcije "Moduli" otvara se novi prozor na kome se prikazuju četiri dugmeta za praćenje četiri trackera korisnika.
4. Izborom bilo kojeg trackera otvara se novi prozor u kome se mogu unositi podaci i spremiti u tabeli,a koji se također pohranjuju i spremaju u bazu podataka projekta. Na dnu svakog trackera nalazi se dugme za prikaz statistike. Pritiskom na to dugme se otvara novi prozor sa prikazom statistike izabranog trackera na osnovu unesenih podataka.  
