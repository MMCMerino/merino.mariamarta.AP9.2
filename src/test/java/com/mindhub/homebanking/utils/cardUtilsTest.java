package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class cardUtilsTest {
    @Autowired
    LoanRepository loanRepository;
    @Test
    void getRandomNumber() {
        int numero =cardUtils.getRandomNumber(0,9999);
        assertThat(numero, greaterThan(00));


    }
}