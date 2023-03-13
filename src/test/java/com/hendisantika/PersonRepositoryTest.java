package com.hendisantika;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/13/23
 * Time: 08:14
 * To change this template use File | Settings | File Templates.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testFindAllReturnsName() {
        // This is defined in tc-init_script.sql
        List<Person> persons = personRepository.findAll();
        int size = persons.size();
        Assertions.assertEquals(size, persons.size());
        assertThat(persons.get(0).getFirstName()).isEqualTo("Uzumaki");
        assertThat(persons.get(0).getLastName()).isEqualTo("Naruto");
    }

}
