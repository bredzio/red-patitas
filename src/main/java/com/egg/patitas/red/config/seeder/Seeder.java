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
            //Capital Federal
            zoneRepository.save(buildZone("Capital Federal", "Agronomia", 1419));
            zoneRepository.save(buildZone("Capital Federal", "Chacarita", 1418));
            zoneRepository.save(buildZone("Capital Federal", "Paternal", 1417));
            zoneRepository.save(buildZone("Capital Federal", "Villa Crespo", 1069));
            zoneRepository.save(buildZone("Capital Federal", "Villa Santa Rita", 1416));
            zoneRepository.save(buildZone("Capital Federal", "Almagro", 1172));
            zoneRepository.save(buildZone("Capital Federal", "Balvanera", 1180));
            zoneRepository.save(buildZone("Capital Federal", "Barracas", 1110));
            zoneRepository.save(buildZone("Capital Federal", "Belgrano", 1424));
            zoneRepository.save(buildZone("Capital Federal", "La Boca", 1063));
            zoneRepository.save(buildZone("Capital Federal", "Boedo", 1218));
            zoneRepository.save(buildZone("Capital Federal", "Coghlan", 1428));
            zoneRepository.save(buildZone("Capital Federal", "Colegiales", 1414));
            zoneRepository.save(buildZone("Capital Federal", "Constitucion", 1070));
            zoneRepository.save(buildZone("Capital Federal", "Floresta", 1406));
            zoneRepository.save(buildZone("Capital Federal", "Monserrat", 1010));
            zoneRepository.save(buildZone("Capital Federal", "Monte Castro", 1407));
            zoneRepository.save(buildZone("Capital Federal", "Nueva Pompeya", 1263));
            zoneRepository.save(buildZone("Capital Federal", "Parque Chas", 1427));
            zoneRepository.save(buildZone("Capital Federal", "Parque Patricios", 1234));
            zoneRepository.save(buildZone("Capital Federal", "Puerto Madero ", 1005));
            zoneRepository.save(buildZone("Capital Federal", "Retiro", 1007));
            zoneRepository.save(buildZone("Capital Federal", "Saavedra", 1428));
            zoneRepository.save(buildZone("Capital Federal", "San Cristobal", 1080));
            zoneRepository.save(buildZone("Capital Federal", "San Nicolas", 1025));
            zoneRepository.save(buildZone("Capital Federal", "San Telmo", 1065));
            zoneRepository.save(buildZone("Capital Federal", "Velez Sarfield", 1407));
            zoneRepository.save(buildZone("Capital Federal", "Versalles", 1068));
            zoneRepository.save(buildZone("Capital Federal", "Villa del Parque", 1084));
            zoneRepository.save(buildZone("Capital Federal", "Villa Devoto", 1419));
            zoneRepository.save(buildZone("Capital Federal", "Villa General Mitre", 1158));
            zoneRepository.save(buildZone("Capital Federal", "Villa Luro", 1440));
            zoneRepository.save(buildZone("Capital Federal", "Villa Ortuzar", 1430));
            zoneRepository.save(buildZone("Capital Federal", "Villa Pueyrredon", 1419));
            zoneRepository.save(buildZone("Capital Federal", "Villa Real", 1408));
            zoneRepository.save(buildZone("Capital Federal", "Villa Urquiza", 1427));
            zoneRepository.save(buildZone("Capital Federal", "Caballito", 1184));

            zoneRepository.save(buildZone("Capital Federal", "Flores", 1437));
            zoneRepository.save(buildZone("Capital Federal", "Liniers", 1408));
            zoneRepository.save(buildZone("Capital Federal", "Mataderos", 1439));
            zoneRepository.save(buildZone("Capital Federal", "Parque Avellaneda", 1440));
            zoneRepository.save(buildZone("Capital Federal", "Parque Chacabuco", 1238));
            zoneRepository.save(buildZone("Capital Federal", "Villa Lugano", 1439));
            zoneRepository.save(buildZone("Capital Federal", "Villa Riachuelo", 1439));
            zoneRepository.save(buildZone("Capital Federal", "Villa Soldati", 1407));
            //Gran Buenos Aires
            zoneRepository.save(buildZone("Buenos Aires", "La Matanza", 1770));
            zoneRepository.save(buildZone("Buenos Aires", "Lanus", 1824));
            zoneRepository.save(buildZone("Buenos Aires", "Lomas de Zamora", 1286));
            zoneRepository.save(buildZone("Buenos Aires", "La Plata", 1902));
            zoneRepository.save(buildZone("Buenos Aires", "Merlo", 1744));
            zoneRepository.save(buildZone("Buenos Aires", "Moron", 1755));
            zoneRepository.save(buildZone("Buenos Aires", "Pilar", 1630));
            zoneRepository.save(buildZone("Buenos Aires", "Quilmes", 1878));
            zoneRepository.save(buildZone("Buenos Aires", "Caseros", 1683));
            zoneRepository.save(buildZone("Buenos Aires", "Tigre", 1648));
            zoneRepository.save(buildZone("Buenos Aires", "Zarate", 1800));
            zoneRepository.save(buildZone("Buenos Aires", "Otra", 1111));
            //Sata Fe
            zoneRepository.save(buildZone("Santa Fe", "Rosario", 2000));
            zoneRepository.save(buildZone("Santa Fe", "Rafaela", 2300));
            zoneRepository.save(buildZone("Santa Fe", "Santa Fe", 2013));
            zoneRepository.save(buildZone("Santa Fe", "Otra", 0000));

            //Misiones
            zoneRepository.save(buildZone("Misiones", "Posadas", 3300));
            zoneRepository.save(buildZone("Misiones", "Puerto Iguazu", 3370));
            zoneRepository.save(buildZone("Misiones", "Eldorado", 3380));
            zoneRepository.save(buildZone("Misiones", "Otra", 0000));

            //Mendoza
            zoneRepository.save(buildZone("Mendoza", "Maipu", 5511));
            zoneRepository.save(buildZone("Mendoza", "Las Heras", 5502));
            zoneRepository.save(buildZone("Mendoza", "Godoy Cruz", 5501));
            zoneRepository.save(buildZone("Mendoza", "Otra", 0000));

            //Cordoba
            zoneRepository.save(buildZone("Cordoba", "Capilla del Monte", 5511));
            zoneRepository.save(buildZone("Cordoba", "La Falda", 5172));
            zoneRepository.save(buildZone("Cordoba", "Otra", 0000));


            //Tierra Del fuego
            zoneRepository.save(buildZone("Tierra del Fuego", "Ushuaia", 9410));
            zoneRepository.save(buildZone("Tierra del Fuego", "Rio Grande", 9420));
            zoneRepository.save(buildZone("Tierra del Fuego", "Otra", 9410));

            //Santa Cruz
            zoneRepository.save(buildZone("Santa Cruz", "Rio Gallegos", 9400));
            zoneRepository.save(buildZone("Santa Cruz", "Rio Turbio", 9407));
            zoneRepository.save(buildZone("Santa Cruz", "Otra", 0000));

            //Chubut
            zoneRepository.save(buildZone("Chubut", "Puerto Madryn", 9120));
            zoneRepository.save(buildZone("Chubut", "Trelew", 9100));
            zoneRepository.save(buildZone("Chubut", "Otra", 0000));

            //Rio Negro
            zoneRepository.save(buildZone("Rio Negro", "Bariloche", 8400));
            zoneRepository.save(buildZone("Rio Negro", "Viedma", 8500));
            zoneRepository.save(buildZone("Rio Negro", "Otra", 0000));

            //Neuquen
            zoneRepository.save(buildZone("Neuquen", "Neuquen", 8300));
            zoneRepository.save(buildZone("Neuquen", "Zapala", 8340));
            zoneRepository.save(buildZone("Neuquen", "Otra", 0000));

            //La Pampa
            zoneRepository.save(buildZone("La Pampa", "Santa Rosa", 6300));
            zoneRepository.save(buildZone("La Pampa", "Otra", 0000));


            //San Luis
            zoneRepository.save(buildZone("San Luis", "San Luis", 5700));
            zoneRepository.save(buildZone("San Luis", "Otra", 0000));
            //Entre Rios
            zoneRepository.save(buildZone("Entre Rios", "Gualeguaychu", 2820));
            zoneRepository.save(buildZone("Entre Rios", "Otra", 0000));

            //Corrientes
            zoneRepository.save(buildZone("Corrientes", "San Luis", 5700));
            zoneRepository.save(buildZone("Corrientes", "Otra", 0000));
            //Santiago del Estero
            zoneRepository.save(buildZone("Santiago del Estero", "Santiago del Estero", 4200));
            zoneRepository.save(buildZone("Santiago del Estero", "Otra", 0000));


            //Chaco
            zoneRepository.save(buildZone("Chaco", "Resistencia", 3500));
            zoneRepository.save(buildZone("Chaco", "Otra", 0000));


            //La rioja
            zoneRepository.save(buildZone("La Rioja", "La Rioja", 5300));
            zoneRepository.save(buildZone("La Rioja", "Otra", 0000));


            //San Juan
            zoneRepository.save(buildZone("San Juan", "San Juan", 5400));
            zoneRepository.save(buildZone("San Juan", "Otra", 0000));


            //Salta
            zoneRepository.save(buildZone("Salta", "Salta", 4400));
            zoneRepository.save(buildZone("Salta", "Otra", 0000));

            //Jujuy
            zoneRepository.save(buildZone("Jujuy", "San Salvador de Jujuy", 4600));
            zoneRepository.save(buildZone("Jujuy", "Otra", 0000));


            //Catamarca
            zoneRepository.save(buildZone("Catamarca", "San Fernando del Valle de Catamarca", 4700));
            zoneRepository.save(buildZone("Catamarca", "Otra", 0000));


            //Tucuman
            zoneRepository.save(buildZone("Tucuman", "San Miguel de Tucuman", 4000));
            zoneRepository.save(buildZone("Tucuman", "Otra", 0000));


            //Formosa
            zoneRepository.save(buildZone("Formosa", "Formosa", 3600));
            zoneRepository.save(buildZone("Formosa", "Otra", 0000));


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
            postRepository.save(buildPostFound("dyrigay@huellapp.com","Es charlatan y muy compañero","Chicho","Paternal"));
            postRepository.save(buildPostFound("msola@huellapp.com","Es muy gracioso, te va a alegrar la vida","Pilu","Boedo"));
    }

    private void loadPostsLost(){
            postRepository.save(buildPostLost("cnani@huellapp.com","Perdida en Villa del Parque, llevaba una correa rosa","Luli","Villa Del Parque"));
            postRepository.save(buildPostLost("cnani@huellapp.com","Perdido por la 9 de julio","Lucas","Posadas"));
            postRepository.save(buildPostLost("wrocha@huellapp.com","Se fue corriendo y nunca más lo vi","Sancho","Rafaela"));
            postRepository.save(buildPostLost("wrocha@huellapp.com","Era un loquito de las motos, y persiguiendo una, lo perdí de vista","Bugs","La Plata"));
    }
}