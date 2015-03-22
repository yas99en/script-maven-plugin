package io.github.yas99en.mojo.script;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class CsvParserTest {

    @DataProvider
    public static Object[][] dataTestParseLine() {
        return new Object[][] {
                { "", new String[0] },
                { "a", new String[]{"a"} },
                { "a,b", new String[]{"a", "b"} },
                { "a,b,c", new String[]{"a", "b", "c"} },
                { ",", new String[]{""} },
                { ",,", new String[]{"", ""} },
                { ",a,", new String[]{"", "a"} },
                { ",,a,,b,", new String[]{"", "", "a", "", "b"} },

                { "\\", new String[0] },
                { ",\\", new String[]{""} },
                { "\\\\", new String[]{"\\"} },
                { "\\\\,", new String[]{"\\"} },
                { "\\a", new String[]{"a"} },
                { "\\a,", new String[]{"a"} },
                { "a\\,b", new String[]{"a,b"} },
                { "a\\,b,c", new String[]{"a,b", "c"} },
                { "\\,", new String[]{","} },
                { "\\,,", new String[]{","} },
                { ",a,", new String[]{"", "a"} },
                { ",\\,a,\\,b,", new String[]{"", ",a", ",b"} },
        };
    }

    @Test
    @UseDataProvider("dataTestParseLine")
    public void testParseLine(String str, String[] expected) {
        String[] actual = CsvParser.parseLine(str);
        System.out.println(Arrays.asList(expected) + ":" + Arrays.asList(actual));
        assertArrayEquals(expected, actual);
    }

}
