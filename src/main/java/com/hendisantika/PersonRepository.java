package com.hendisantika;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/13/23
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
@RestResource
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
