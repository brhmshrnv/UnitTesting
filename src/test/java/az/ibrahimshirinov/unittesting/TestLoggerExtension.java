package az.ibrahimshirinov.unittesting;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.logging.Logger;

/**
 * @author IbrahimShirinov
 * @project UnitTesting
 * @since 31.08.2021
 */
public class TestLoggerExtension implements BeforeAllCallback, AfterAllCallback {

    private static final Logger log = Logger.getLogger(TestLoggerExtension.class.getName());

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        log.info(String.format("Test is started... %s",context.getDisplayName()));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        log.info(String.format("Test is ended... %s",context.getDisplayName()));
    }
}
