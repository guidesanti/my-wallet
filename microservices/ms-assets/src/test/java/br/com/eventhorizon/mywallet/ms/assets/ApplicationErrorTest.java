package br.com.eventhorizon.mywallet.ms.assets;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationErrorTest {

    private static final String DOMAIN = "ASSETS";

    private static final Map<ApplicationError, Error> errors = new HashMap<>();

    private static final Map<ApplicationError, Object[]> args = new HashMap<>();

    static {
        errors.put(ApplicationError.UNKNOWN_ERROR, Error.of(ErrorCode.app(DOMAIN, "UNKNOWN_ERROR"), "Unknown error"));
        args.put(ApplicationError.UNKNOWN_ERROR, new Object[]{});

        errors.put(ApplicationError.ASSET_ALREADY_EXIST, Error.of(ErrorCode.app(DOMAIN, "ASSET_ALREADY_EXIST"), "Asset already exist with shortName 'short-name' or longName 'long-name'"));
        args.put(ApplicationError.ASSET_ALREADY_EXIST, new Object[]{ "short-name", "long-name" });

        errors.put(ApplicationError.ASSET_NOT_FOUND, Error.of(ErrorCode.app(DOMAIN, "ASSET_NOT_FOUND"), "Asset not found for ID 'id'"));
        args.put(ApplicationError.ASSET_NOT_FOUND, new Object[]{ "id" });

        errors.put(ApplicationError.ASSET_TYPE_ALREADY_EXIST, Error.of(ErrorCode.app(DOMAIN, "ASSET_TYPE_ALREADY_EXIST"), "Asset type already exist with name 'name'"));
        args.put(ApplicationError.ASSET_TYPE_ALREADY_EXIST, new Object[]{ "name" });
    }

    @Test
    void testBuild() {
        errors.forEach((applicationError, expectedError) -> {
            var actualError = applicationError.build(args.get(applicationError));
            assertEquals(expectedError, actualError);
            assertTrue(actualError.getAdditionalInformation().isEmpty());
        });
    }

//    @Test
//    void testBuildWithAdditionalInformation() {
//        errors.forEach((applicationError, expectedError) -> {
//            var actualError = applicationError.build("Additional information", args.get(applicationError));
//            assertEquals(expectedError, actualError);
//            assertTrue(actualError.getAdditionalInformation().isPresent());
//            assertEquals("Additional information", actualError.getAdditionalInformation().get());
//        });
//    }
}
