package systemtest.smoketest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Maybe make separate module entirely for systemtesting for better configuration etc.
 *
 * Structure smoke and functionality to make some lightweight testing
 *
 * Testcases for smoketesting:
 * - -f csv -F csv -0.123,2.5 -a sum
 * - -i FILE -f json -F csv -o URL -a lt4
 * - -i URL -f csv -F json -o FILE -a minMax
 *
 *  -> assert exitCode, assert output (cmd, file, url)
 *
 * Possible improvements:
 * - Make it platform independent
 * - Make it so you can create csv/json files and use them as test input and test output to test against
 * - Make things like path for test files, name of the artifact and which OS to use configurable
 *
 *
 * source for running a cmd from java: https://www.baeldung.com/run-shell-command-in-java
 */
class MainSystemSmokeTest {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final static boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    @BeforeEach
    void setUp() {
    }

    @Test
    void testMain() throws IOException, InterruptedException {
        // given
        // Can't run a systemtest on something that doesn't exist yet (make mvn clean install work without having to run -DskipTests before)
        assumeTrue(new File("target/cg-0.1.0-SNAPSHOT.jar").exists(), "Artifact to test not existing yet. Did you run it with mvn clean?");

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File("target"));
        if (isWindows) {
            builder.command("cmd.exe", "/c", "java -Dorg.slf4j.simpleLogger.logFile=System.out -jar cg-0.1.0-SNAPSHOT.jar -f csv -F csv -0.123,2.5 -a sum");
        } else {
            // TODO make it work for linux
            builder.command("sh", "-c", "java -Dorg.slf4j.simpleLogger.logFile=System.out -jar cg-0.1.0-SNAPSHOT.jar -f csv -F csv -0.123,2.5 -a sum");
        }

        Process process = builder.start();
        // process.getInputStream() is the piped output stream of the process
        // We pipe the output of the executed process to System.out::println
        List<String> output = new ArrayList<>();
        StreamGobbler streamGobbler =
                new StreamGobbler(process.getInputStream(), System.out::println, output);

        // when
        Future<?> future = executorService.submit(streamGobbler);

        // then
        assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS)); // Call future.get before process.waitFor because of the timeout or it can get stuck
        int exitCode = process.waitFor();
        assertEquals(0, exitCode);
        assertFalse(output.isEmpty());
        assertEquals("[main] INFO org.example.cg.core.output.adapter.CLIOutputAdapter - 2.377", output.get(output.size() - 2));
        assertEquals("", output.getLast());
    }

    @AfterEach
    void tearDown() {
        executorService.shutdown();
    }

    private record StreamGobbler(InputStream inputStream, Consumer<String> consumer,
                                    List<String> output) implements Runnable {

        @Override
            public void run() {

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    synchronized (output) {
                        while ((line = reader.readLine()) != null) {
                            output.add(line);
                            consumer.accept(line);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
}