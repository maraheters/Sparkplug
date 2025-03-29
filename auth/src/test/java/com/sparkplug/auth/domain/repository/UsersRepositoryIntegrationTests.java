package com.sparkplug.auth.domain.repository;

import com.sparkplug.auth.JpaTestsBase;
import com.sparkplug.auth.domain.entity.Client;
import com.sparkplug.auth.domain.entity.ClientAuthority;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.Username;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersRepositoryIntegrationTests extends JpaTestsBase {

    @Autowired
    private UsersRepository usersRepository;

    private final Username testUsername = new Username("testUsername");
    private final Email testEmail = new Email("testEmail@example.com");
    private final PhoneNumber testPhoneNumber = new PhoneNumber("+12345678919000");
    private final String testPasswordHash = "fdjp929f98301123f";

    @BeforeEach
    void cleanDatabase() {
        transactionTemplate.executeWithoutResult(
                s -> em.getEntityManager().createQuery("DELETE FROM User").executeUpdate()
        );
    }

    @Test
    void existsByUsername_whenExists_thenCorrect() {
        insertClient(testUsername, testEmail, testPhoneNumber, testPasswordHash);

        assertTrue(usersRepository.existsByUsername(testUsername));
    }

    @Test
    void existsByUsername_whenDoesntExist_thenCorrect() {
        assertFalse(usersRepository.existsByUsername(testUsername));
    }

    @Test
    void existsByEmail_whenExists_thenCorrect() {
        insertClient(testUsername, testEmail, testPhoneNumber, testPasswordHash);

        assertTrue(usersRepository.existsByEmail(testEmail));
    }
    @Test
    void existsByEmail_whenDoesntExist_thenCorrect() {
        assertFalse(usersRepository.existsByEmail(testEmail));
    }

    @Test
    void existsByPhoneNumber_whenExists_thenCorrect() {
        insertClient(testUsername, testEmail, testPhoneNumber, testPasswordHash);

        assertTrue(usersRepository.existsByPhoneNumber(testPhoneNumber));
    }
    @Test
    void existsByPhoneNumber_whenDoesntExist_thenCorrect() {
        assertFalse(usersRepository.existsByPhoneNumber(testPhoneNumber));
    }

    private void insertClient(Username username, Email email, PhoneNumber phoneNumber, String passwordHash) {
        transactionTemplate.execute(status -> {

            var clientAuthority = em.find(ClientAuthority.class, 1 );

            var client = Client.createWithPhoneNumberAndEmail(
                    username, phoneNumber, email, passwordHash, List.of(clientAuthority));

            em.persist(client);

            return null;
        });
    }

}
