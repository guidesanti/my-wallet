package br.com.eventhorizon.common.concurrent;

import br.com.eventhorizon.common.concurrent.impl.SleeperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class SleeperTest {

    @Test
    public void testGetDefaultSleeper() {
        assertSame(SleeperImpl.getInstance(), Sleeper.getDefaultSleeper());
    }
}
