package com.github.hiteshsondhi88.libffmpeg;

import com.github.hiteshsondhi88.libffmpeg.utils.AssertionHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

public class ShellCommandTest extends CommonTestCase {

    public void testRun() throws Exception {
        ShellCommand shellCommand = new ShellCommand();
        final Process process = shellCommand.run(new String[] {"logcat"});
        assertNotNull(process);
        assertEquals(false, Util.isProcessCompleted(process));

        Util.destroyProcess(process);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                String errorStream = Util.convertInputStreamToString(process.getErrorStream());
                String inputStream = Util.convertInputStreamToString(process.getInputStream());

                assertEquals(null, errorStream);
                assertEquals(null, inputStream);
            }
        });

        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            AssertionHelper.assertError("could not destroy process");
        }

        executor.shutdownNow();
    }

    public void testRunWaitFor() throws Exception {
        ShellCommand shellCommand = new ShellCommand();
        CommandResult commandResult = shellCommand.runWaitFor(new String[] {"ls"});
        assertNotNull(commandResult);
        assertEquals(true, commandResult.success);
        assertThat(commandResult.output).isNotEmpty();
    }
}