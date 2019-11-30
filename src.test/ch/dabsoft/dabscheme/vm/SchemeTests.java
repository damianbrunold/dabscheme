package ch.dabsoft.dabscheme.vm;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SchemeTests {

    private static class SchemeTest {
        public String prepare = "";
        public List<String> tests = new ArrayList<>();
        public List<String> expectations = new ArrayList<>();
    }

    @Test
    public void testTSPL3ed() throws IOException {
        runTests("/tests_tspl3ed.scm");
    }

    @Test
    public void testR7RS() throws IOException {
        runTests("/tests_r7rs.scm");
    }

    @Test
    public void testGeneral() throws IOException {
        runTests("/tests_general.scm");
    }

    @Test
    public void testTests() throws IOException {
        runTests("/tests_tests.scm");
    }

    public void runTests(String filename) throws IOException {
        System.out.println("Testfile " + filename);
        boolean success = true;
        int tests = 0;
        Scheme scheme = new Scheme();
        for (SchemeTest test : readTests(filename)) {
            if (!test.prepare.isEmpty()) scheme.evalString(test.prepare);
            for (int i = 0; i < test.tests.size(); i++) {
                String code = test.tests.get(i);
                String expected = test.expectations.get(i);
                String output = Value.printRep(scheme.evalString(code));
                tests++;
                if (!output.equals(expected)) {
                    success = false;
                    System.out.println("Test failure");
                    System.out.println("code:");
                    System.out.println(code);
                    System.out.println("output:");
                    System.out.println(output);
                    System.out.println("expected:");
                    System.out.println(expected);
                    System.out.println();
                }
            }
            scheme.reset();
        }
        System.out.println(tests + " tests run");
        System.out.println();
        if (!success) Assert.fail("Tests failed, see output for details");
    }

    private List<SchemeTest> readTests(String filename) throws IOException {
        List<SchemeTest> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(SchemeTests.class.getResourceAsStream(filename), StandardCharsets.UTF_8))) {
            SchemeTest test = new SchemeTest();
            StringBuilder current = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    if (line.trim().equals("=")) {
                        test.prepare = current.toString();
                        current = new StringBuilder();
                    } else if (line.trim().equals("=>")) {
                        test.tests.add(stripNewline(current.toString()));
                        current = new StringBuilder();
                    } else if (line.trim().equals(".")) {
                        test.expectations.add(stripNewline(current.toString()));
                        current = new StringBuilder();
                    } else if (line.trim().equals("<<")) {
                        test.expectations.add(stripNewline(current.toString()));
                        result.add(test);
                        test = new SchemeTest();
                        current = new StringBuilder();
                    } else {
                        current.append(line).append("\n");
                    }
                }
                line = in.readLine();
            }
        }
        return result;
    }

    private String stripNewline(String s) {
        if (s.endsWith("\n")) return s.substring(0, s.length() - 1);
        return s;
    }

}
