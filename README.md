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

Implementiran projekat 1 - Finance App u main menu dio projekta. Zadržana logika i kod iz projekta 1 uz odrađene modifikacije na GUI dijelu. 
Također promijenjena baza podataka tako da se i FinanceApp dio sada unosi i čuva u sklopu baze podataka Life Management System-a.

Kreirana ModulesWindow klasa koja sadrži četiri dugmeta koja vode na zasebne trackere/module.
Kreirana klasa MongoDBConnection za spajanje pojedinacnih trackera i njihovih podataka u bazu.
Kreiran prvi tracker - Sleep Tracker koji korisniku omogućava da unese datum i broj sati spavanja gdje pritiskom na dugme "Dodaj" podaci se prikazuju u tabeli i upisuju u kolekciju u bazi. Ispod tabele nalazi se dugme za prikaz statistike koji korisniku izbacuje tabelu sa datumima i satima spavanja,a na dnu tabele izbacuje prosječno vrijeme spavanja u satima na osnovu unesenih podataka.
