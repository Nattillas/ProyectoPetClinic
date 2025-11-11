package com.petclinic.config;

import com.petclinic.infrastructure.entity.OwnerEntity;
import com.petclinic.infrastructure.entity.PetEntity;
import com.petclinic.infrastructure.entity.PetTypeEntity;
import com.petclinic.infrastructure.entity.VisitEntity;
import com.petclinic.infrastructure.repository.OwnerJpaRepository;
import com.petclinic.infrastructure.repository.PetJpaRepository;
import com.petclinic.infrastructure.repository.PetTypeJpaRepository;
import com.petclinic.infrastructure.repository.VisitJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Data initializer to populate the database with sample data after application startup.
 * This ensures that the database schema is created before data insertion.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PetTypeJpaRepository petTypeRepository;
    
    @Autowired
    private OwnerJpaRepository ownerRepository;
    
    @Autowired
    private PetJpaRepository petRepository;
    
    @Autowired
    private VisitJpaRepository visitRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Only initialize data if the database is empty
        if (petTypeRepository.count() == 0) {
            initializePetTypes();
            initializeOwners();
            initializePets();
            initializeVisits();
        }
    }

    private void initializePetTypes() {
        // Create pet types
        petTypeRepository.save(new PetTypeEntity("cat"));
        petTypeRepository.save(new PetTypeEntity("dog"));
        petTypeRepository.save(new PetTypeEntity("lizard"));
        petTypeRepository.save(new PetTypeEntity("snake"));
        petTypeRepository.save(new PetTypeEntity("bird"));
        petTypeRepository.save(new PetTypeEntity("hamster"));
    }

    private void initializeOwners() {
        // Create owners
        OwnerEntity owner1 = new OwnerEntity();
        owner1.setFirstName("George");
        owner1.setLastName("Franklin");
        owner1.setAddress("110 W. Liberty St.");
        owner1.setCity("Madison");
        owner1.setTelephone("6085551023");
        ownerRepository.save(owner1);

        OwnerEntity owner2 = new OwnerEntity();
        owner2.setFirstName("Betty");
        owner2.setLastName("Davis");
        owner2.setAddress("638 Cardinal Ave.");
        owner2.setCity("Sun Prairie");
        owner2.setTelephone("6085551749");
        ownerRepository.save(owner2);

        OwnerEntity owner3 = new OwnerEntity();
        owner3.setFirstName("Eduardo");
        owner3.setLastName("Rodriquez");
        owner3.setAddress("2693 Commerce St.");
        owner3.setCity("McFarland");
        owner3.setTelephone("6085558763");
        ownerRepository.save(owner3);
    }

    private void initializePets() {
        // Get pet types by name
        PetTypeEntity catType = petTypeRepository.findByName("cat").orElseThrow();
        PetTypeEntity dogType = petTypeRepository.findByName("dog").orElseThrow();
        PetTypeEntity lizardType = petTypeRepository.findByName("lizard").orElseThrow();

        // Get owners by name (using the available repository methods)
        OwnerEntity owner1 = ownerRepository.findByFirstNameAndLastName("George", "Franklin").stream().findFirst().orElseThrow();
        OwnerEntity owner2 = ownerRepository.findByFirstNameAndLastName("Betty", "Davis").stream().findFirst().orElseThrow();
        OwnerEntity owner3 = ownerRepository.findByFirstNameAndLastName("Eduardo", "Rodriquez").stream().findFirst().orElseThrow();

        // Create pets
        PetEntity pet1 = new PetEntity();
        pet1.setName("Leo");
        pet1.setBirthDate(LocalDate.of(2020, 9, 7));
        pet1.setType(catType);
        pet1.setOwner(owner1);
        petRepository.save(pet1);

        PetEntity pet2 = new PetEntity();
        pet2.setName("Basil");
        pet2.setBirthDate(LocalDate.of(2022, 8, 6));
        pet2.setType(dogType);
        pet2.setOwner(owner2);
        petRepository.save(pet2);

        PetEntity pet3 = new PetEntity();
        pet3.setName("Rosy");
        pet3.setBirthDate(LocalDate.of(2021, 4, 17));
        pet3.setType(catType);
        pet3.setOwner(owner3);
        petRepository.save(pet3);

        PetEntity pet4 = new PetEntity();
        pet4.setName("Jewel");
        pet4.setBirthDate(LocalDate.of(2020, 3, 7));
        pet4.setType(dogType);
        pet4.setOwner(owner3);
        petRepository.save(pet4);

        PetEntity pet5 = new PetEntity();
        pet5.setName("Iggy");
        pet5.setBirthDate(LocalDate.of(2020, 11, 30));
        pet5.setType(lizardType);
        pet5.setOwner(owner2);
        petRepository.save(pet5);
    }

    private void initializeVisits() {
        // Get pets by name (using the available repository method)
        PetEntity pet1 = petRepository.findByNameContainingIgnoreCase("Leo").stream().findFirst().orElseThrow();
        PetEntity pet2 = petRepository.findByNameContainingIgnoreCase("Basil").stream().findFirst().orElseThrow();
        PetEntity pet3 = petRepository.findByNameContainingIgnoreCase("Rosy").stream().findFirst().orElseThrow();

        // Create visits
        VisitEntity visit1 = new VisitEntity();
        visit1.setDate(LocalDate.of(2023, 1, 1));
        visit1.setDescription("rabies shot");
        visit1.setPet(pet1);
        visitRepository.save(visit1);

        VisitEntity visit2 = new VisitEntity();
        visit2.setDate(LocalDate.of(2023, 1, 2));
        visit2.setDescription("rabies shot");
        visit2.setPet(pet3);
        visitRepository.save(visit2);

        VisitEntity visit3 = new VisitEntity();
        visit3.setDate(LocalDate.of(2023, 1, 3));
        visit3.setDescription("neutered");
        visit3.setPet(pet2);
        visitRepository.save(visit3);

        VisitEntity visit4 = new VisitEntity();
        visit4.setDate(LocalDate.of(2023, 1, 4));
        visit4.setDescription("spayed");
        visit4.setPet(pet3);
        visitRepository.save(visit4);
    }
}