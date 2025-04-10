package com.sparkplug.catalog.service;

import com.sparkplug.catalog.model.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@Transactional
@Testcontainers
@ActiveProfiles("test")
@DataJpaTest
public class ServiceTest {

    @Autowired
    TransactionTemplate transactionTemplate;
    @Autowired
    EntityManager em;

    protected Long insertManufacturer() {
        return transactionTemplate.execute(status -> {

            var manufacturer = Manufacturer.builder()
                    .name("TestManufacturer")
                    .country("TestCountry")
                    .build();

            em.persist(manufacturer);

            return manufacturer.getId();
        });
    }

    protected Long insertCarModel(Long manufacturerId) {
        return transactionTemplate.execute(status -> {

            var carModel = CarModel.builder()
                    .name("TestManufacturer")
                    .manufacturer(Manufacturer.builder().id(manufacturerId).build())
                    .build();

            em.persist(carModel);

            return carModel.getId();
        });
    }

    protected Long insertGeneration(Long carModelId) {
        return transactionTemplate.execute(status -> {

            var generation = Generation.builder()
                    .name("TestName")
                    .startYear(2000)
                    .carModel(CarModel.builder().id(carModelId).build())
                    .build();

            em.persist(generation);

            return generation.getId();
        });
    }

    protected Long insertModification(Long generationId) {
        return transactionTemplate.execute(status -> {

            var modification = Modification.builder()
                    .name("TestModification")
                    .build();

            em.persist(modification);

            return modification.getId();
        });
    }
}
