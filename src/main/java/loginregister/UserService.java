package loginregister;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new loginregister.UserRepository();
    }

    public boolean login (String korisnickoIme, String plainTextPassword) {
        Optional<User> userOptional = userRepository.pronadjiPoImenu(korisnickoIme);
        return userOptional.isPresent() && PasswordUtil.provjeriLozinku(plainTextPassword, userOptional.get().getHashLozinka());
    }

    public boolean register (String korisnickoIme, String plainTextPassword) {
        if (userRepository.pronadjiPoImenu(korisnickoIme).isPresent()) {
            System.out.println("Registracija neuspješna! Korisnik s tim imenom već postoji!");
            return false;
        }

        String hashedPassword = PasswordUtil.hashLozinka(plainTextPassword);
        User newUser = new User(korisnickoIme, hashedPassword);
        userRepository.sacuvaj(newUser);
        System.out.println("Korisnik ' " + korisnickoIme + " ' uspješno registrovan!");
        return true;
    }

    public Optional<User>pronadjiPoImenu(String korisnickoIme) {
        return userRepository.pronadjiPoImenu(korisnickoIme);
    }

    public void azurirajLozinku(String korisnickoIme, String newPassword) {
        userRepository.pronadjiPoImenu(korisnickoIme).ifPresent(user->userRepository.azurirajLozinku(korisnickoIme, PasswordUtil.hashLozinka(newPassword)));
    }

    public void izbrisiKorisnika(String korisnickoIme) {
        userRepository.pronadjiPoImenu(korisnickoIme).ifPresent(user->userRepository.izbrisiPoId(user.getId()));
    }

    public void azurirajKorisnickoIme(String oldUsername, String newUsername) {
        userRepository.azurirajKorisnickoIme(oldUsername,newUsername);
    }

    public List<User>sviKorisnici() {
        return userRepository.sviKorisnici();
    }

    public boolean postojiKorisnik(String korisnickoIme) {
        return userRepository.postojiKorisnik(korisnickoIme);
    }
}
