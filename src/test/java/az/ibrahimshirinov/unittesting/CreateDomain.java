package az.ibrahimshirinov.unittesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author IbrahimShirinov
 * @date 30.08.2021
 * @project UnitTesting
 */
public interface CreateDomain<T> {

    T createDomain();

    @Test
    default void createDomainShouldBeImplemented() {
        assertNotNull(createDomain());
    }
}
