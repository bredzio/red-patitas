package com.egg.patitas.red.config.seeder;

import com.egg.patitas.red.config.RoleEnum;
import com.egg.patitas.red.model.*;
import com.egg.patitas.red.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    private static final Integer ROLE_USER = 1;
    private static final Integer ROLE_ADMIN = 2;
    private static final String PASSWORD_GENERIC = "123456";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AnimalRepository animalRepository;
    private final ZoneRepository zoneRepository;
    private final PetRepository petRepository;
    private final PostRepository postRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String GATO1 = "https://huellapp.s3.us-east-2.amazonaws.com/gato1.jpg";
    private static final String GATO2 = "https://huellapp.s3.us-east-2.amazonaws.com/gato2.jpg";
    private static final String GATO3 = "https://huellapp.s3.us-east-2.amazonaws.com/gato3.jpg";
    private static final String GATO4 = "https://huellapp.s3.us-east-2.amazonaws.com/gato4.jpg";
    private static final String GATO5 = "https://huellapp.s3.us-east-2.amazonaws.com/gato5.jpg";
    private static final String PERRO1 = "https://huellapp.s3.us-east-2.amazonaws.com/perro1.jpg";
    private static final String PERRO2 = "https://huellapp.s3.us-east-2.amazonaws.com/perro2.jpg";
    private static final String PERRO3 = "https://huellapp.s3.us-east-2.amazonaws.com/perro3.jpg";
    private static final String PERRO4 = "https://huellapp.s3.us-east-2.amazonaws.com/perro4.jpg";
    private static final String CONEJO = "https://huellapp.s3.us-east-2.amazonaws.com/conejo1.jpg";


    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
        loadAnimals();
        loadZones();
        loadPets();
        loadPosts();
    }

    //METODOS PARA CARGAR ROLES
    private void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(buildRole(RoleEnum.USER));
            roleRepository.save(buildRole(RoleEnum.ADMIN));
        }
    }

    private Role buildRole(RoleEnum roleEnum) {
        Role role = new Role();
        role.setId(getRoleId(roleEnum));
        role.setName(roleEnum.getRoleName());
        return role;
    }

    private Integer getRoleId(RoleEnum roleEnum) {
        return roleEnum == roleEnum.USER ? ROLE_USER : ROLE_ADMIN;
    }

    //METODOS PARA CARGAR USUARIOS

    private void loadUsers() {
        if (userRepository.count() == 0) {
            loadUsersWithRoleUser();
            loadUsersWithRoleAdmin();
        }
    }

    private void loadUsersWithRoleUser(){
        userRepository.save(buidUser("Wanda","Rocha","wrocha@huellapp.com"));
        userRepository.save(buidUser("Dianyeli","Yrigay","dyrigay@huellapp.com"));
        userRepository.save(buidUser("Martín ","Solá","msola@huellapp.com"));
        userRepository.save(buidUser("Fabiola ","Siles","fsiles@huellapp.com"));
        userRepository.save(buidUser("Camila ","Nani","cnani@huellapp.com"));
        userRepository.save(buidUser("Maximiliano ","Monje","mmonje@huellapp.com"));
        userRepository.save(buidUser("Bruno","Redzio","bredzio@huellapp.com"));
        userRepository.save(buidUser("Nestor Ariel","Rivas","nrivas@huellapp.com"));
        userRepository.save(buidUser("Cristian Samuel","Solís","csolis@huellapp.com"));

    }

    private void loadUsersWithRoleAdmin(){
        userRepository.save(buidUserAdmin("Wanda","Rocha","wrocha_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Dianyeli","Yrigay","dyrigay_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Martín ","Solá","msola_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Fabiola ","Siles","fsiles_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Camila ","Nani","cnani_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Maximiliano ","Monje","mmonje_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Bruno","Redzio","bredzio_admin@huellapp.com"));
        userRepository.save(buidUserAdmin("Cristian Samuel","Solís","csolis_admin@huellapp.com"));
    }

    private User buidUser(String name, String lastname, String email){
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(PASSWORD_GENERIC));
        user.setRole(roleRepository.findById(ROLE_USER).get());
        user.setEnabled(Boolean.TRUE);
        return user;
    }

    private User buidUserAdmin(String name, String lastname, String email){
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(PASSWORD_GENERIC));
        user.setRole(roleRepository.findById(ROLE_ADMIN).get());
        user.setEnabled(Boolean.TRUE);
        return user;
    }

    //METODOS PARA CARGAR ESPECIES DE ANIMALES


    private Animal buildAnimal(String name){
        Animal animal = new Animal();
        animal.setName(name);
        animal.setEnabled(Boolean.TRUE);
        return animal;
    }

    private void loadAnimals(){
        if(animalRepository.count()==0){
            animalRepository.save(buildAnimal("Perro"));
            animalRepository.save(buildAnimal("Gato"));
            animalRepository.save(buildAnimal("Conejo"));
            animalRepository.save(buildAnimal("Tortuga"));
            animalRepository.save(buildAnimal("Otro"));
        }
    }

    //METODOS PARA CARGAR ZONAS


    private Zone buildZone(String province, String city, Integer zipCode) {
        Zone zone = new Zone();
        zone.setProvince(province);
        zone.setCity(city);
        zone.setZipCode(zipCode);
        zone.setEnabled(Boolean.TRUE);
        return zone;
    }

    private void loadZones() {
        if(zoneRepository.count()==0) {
            zoneRepository.save(buildZone("Buenos Aires", "Capital Federal", 1111));
            zoneRepository.save(buildZone("Buenos Aires", "Gran Buenos Aires", 2222));
            zoneRepository.save(buildZone("Santa Fe", "Rosario", 3333));
            zoneRepository.save(buildZone("Santa Fe", "Rafaela", 4444));
        }
    }

    //METODOS PARA CARGAR MASCOTAS

    private Pet buildPet(String name, String userEmail, String photo, String animal) {
        Pet pet = new Pet();

        pet.setName(name);
        pet.setPhoto(photo);
        pet.setUser(userRepository.findByEmail(userEmail));
        pet.setAnimal(animalRepository.findByName(animal).get());
        pet.setEnabled(Boolean.TRUE);

        return pet;
    }

    private void loadPets(){
        if(petRepository.count()==0) {
            petRepository.save(buildPet("Shazam", "bredzio@huellapp.com", GATO1,"Gato"));
            petRepository.save(buildPet("Clarita", "bredzio@huellapp.com", GATO2,"Gato"));
            petRepository.save(buildPet("Pepe", "bredzio@huellapp.com", GATO3,"Gato"));
            petRepository.save(buildPet("Toto", "dyrigay@huellapp.com", GATO4,"Gato"));
            petRepository.save(buildPet("Chicho", "dyrigay@huellapp.com", GATO5,"Gato"));
            petRepository.save(buildPet("Pilu", "msola@huellapp.com", PERRO1,"Perro"));
            petRepository.save(buildPet("Luli", "cnani@huellapp.com", PERRO2,"Perro"));
            petRepository.save(buildPet("Lucas", "cnani@huellapp.com", PERRO3,"Perro"));
            petRepository.save(buildPet("Sancho", "wrocha@huellapp.com", PERRO4,"Perro"));
            petRepository.save(buildPet("Bugs", "wrocha@huellapp.com", CONEJO,"Conejo"));
        }
    }

    //METODOS PARA CARGAR POSTEOS


    private Post buildPostLost(String user, String description, String pet, String zone) {
        Post post = new Post();

        post.setUser(userRepository.findByEmail(user));
        post.setDescription(description);
        post.setPet(petRepository.findByName(pet).get());
        post.setZone(zoneRepository.findByCity(zone).get());
        post.setLostOrFound(Boolean.FALSE);
        post.setEnabled(Boolean.TRUE);
        return post;
    }

    private Post buildPostFound(String user, String description, String pet, String zone) {
        Post post = new Post();

        post.setUser(userRepository.findByEmail(user));
        post.setDescription(description);
        post.setPet(petRepository.findByName(pet).get());
        post.setZone(zoneRepository.findByCity(zone).get());
        post.setLostOrFound(Boolean.TRUE);
        post.setEnabled(Boolean.TRUE);
        return post;
    }

    private void loadPosts() {
        if (postRepository.count() == 0) {
            loadPostsFound();
            loadPostsLost();
        }
    }

    private void loadPostsFound(){
            postRepository.save(buildPostFound("bredzio@huellapp.com","Es muy mimoso, te necesita","Shazam","Rafaela"));
            postRepository.save(buildPostFound("dyrigay@huellapp.com","Con esta carita ¿como no lo vas a querer?","Toto","Rafaela"));
            postRepository.save(buildPostFound("dyrigay@huellapp.com","Es charlatan y muy compañero","Chicho","Capital Federal"));
            postRepository.save(buildPostFound("msola@huellapp.com","Es muy gracioso, te va a alegrar la vida","Pilu","Capital Federal"));
    }

    private void loadPostsLost(){
            postRepository.save(buildPostLost("cnani@huellapp.com","Perdido en la plaza almagro","Luli","Capital Federal"));
            postRepository.save(buildPostLost("cnani@huellapp.com","Perdido por la 9 de julio","Lucas","Capital Federal"));
            postRepository.save(buildPostLost("wrocha@huellapp.com","Se fue corriendo y nunca más lo vi","Sancho","Rafaela"));
            postRepository.save(buildPostLost("wrocha@huellapp.com","Era un loquito de las motos, y persiguiendo una, lo perdí de vista","Bugs","Rafaela"));
    }
}