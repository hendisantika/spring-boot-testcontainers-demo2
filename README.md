# spring-boot-testcontainers-demo2

Spring boot is the now the best known RAD(Rapid Application Development) framework for many applications in software
world. When we use spring provided JPA i.e Spring Data JPA, it wraps us an easy way to create database queries and test
them with an embedded H2/HSQL database.

But in some cases, testing on an actual database is much more reasonable and safe, especially if we use database
platform provider dependent queries.

In order to tackle this situation easily, we have a simple solution known as Testcontainers.

[Testcontainers](https://www.testcontainers.org/) is a Java library that supports JUnit tests, providing lightweight,
throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container. You
can run your services in Docker and let the Testcontainers manage this for you.

We will see how to configure Testcontainer to run PostgreSQL. This configuration should be almost same for other
database providers too.

### Datasource configuration for Test

Below are the steps to configure Testcontainers for Spring Boot tests:

1. You can configure the datasource in Class also but we would see the approach from application.propertiesfile.
2. Define and set the driver to org.testcontainers.jdbc.ContainerDatabaseDriver which is a Testcontainers JDBC proxy
   driver. This driver makes will be responsible for starting the required Docker container when the datasource gets
   initialized.
3. Define and set the dialect explicitly to implementation of the dialect for your database otherwise you get the
   exception while starting the application. This step is required when you use JPA in your application (via Spring Data
   JPA)
4. Set the JDBC URL to jdbc:tc:<database-image>:<version>:/// so that Testcontainers knows which database image to use.

### PostgreSQL configuration:

The test configuration is very minimal as below:

```shell
spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver
spring.datasource.url=jdbc:tc:postgresql:15.2:///?TC_INITSCRIPT=init_script.sql
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.generate-ddl=false
```

### Initializing test db with data

You may initialize the database with the script loaded by Testcontainers. The file can be loaded either directly from
the classpath or from any project location. To do that, the only thing to do is to change the JDBC URL path:
`spring.datasource.url=jdbc:tc:postgresql:15.2:///?TC_INITSCRIPT=init_script.sql`

It can also be done in defining in classpath as below:

`spring.datasource.url=jdbc:tc:postgresql:15.2:///?TC_INITSCRIPT=classpath:init_script.sql`

### @DataJpaTest

In order to use test containers in @DataJpaTest you need to make sure that the application defined (auto-configured)
datasource is used. You can do it easily by annotating your test with @AutoConfigureTestDatabase as shown below:

```java

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

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
```

### @SpringBootTest

@SpringBootTest will use application defined datasource, and so more additional changes are required.

```java

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonResourceTests {

    @Autowired
    WebApplicationContext wac;

    @Test
    void testFindAllReturnsName() {
        given()
                .webAppContextSetup(wac)
                .when()
                .get("/persons")
                .then()
                .status(HttpStatus.OK)
                .body(
                        "_embedded.persons.firstName", containsInAnyOrder("Uzumaki", "Haruno", "Uchiha", "Hatake",
                                "Asuma"),
                        "_embedded.persons.lastName", containsInAnyOrder("Naruto", "Sakura", "Sasuke", "Kakashi",
                                "Sarutobi")
                );
    }
}
```

### Summary

We have just seen the simplest easiest way to configure PostgreSQL for Spring boot Testing with Testcontainers. The
configuration would be almost same for other databases like MySQL and MariaDB. If you need more control over the Docker
images, please consult official Testcontainers documentation.

### Things todo list:

1. Clone this repository: `git clone https://github.com/hendisantika/spring-boot-testcontainers-demo2.git`
2. Navigate to the folder: `cd spring-boot-testcontainers-demo2`
3. Replace credentials with yours in `application.properties` file
4. Run the application test: `mvn clean test`
