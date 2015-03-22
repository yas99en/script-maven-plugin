package io.github.yas99en.mojo.script;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class TestCsvParser {

    @DataProvider
    public static Object[][] dataTestParse() {
        return new Object[][] {
                { "", new String[]{""} },
                { "a", new String[]{"a"} },
                { "a,b", new String[]{"a", "b"} },
                { "a,b,c", new String[]{"a", "b", "c"} },
                { ",", new String[]{"", ""} },
                { ",,", new String[]{"", "", ""} },
                { ",a,", new String[]{"", "a", ""} },
                { ",,a,,b,", new String[]{"", "", "a", "", "b", ""} },

                { "\\", new String[]{""} },
                { "\\a", new String[]{"a"} },
                { "\\a,", new String[]{"a", ""} },
                { "a\\,b", new String[]{"a,b"} },
                { "a\\,b,c", new String[]{"a,b", "c"} },
                { "\\,", new String[]{","} },
                { "\\,,", new String[]{",", ""} },
                { ",a,", new String[]{"", "a", ""} },
                { ",\\,a,\\,b,", new String[]{"", ",a", ",b", ""} },

        };
    }

    @Test
    @UseDataProvider("dataTestParse")
    public void testParse(String str, String[] expected) {
        String[] actual = CsvParser.splitByCommna(str);
        System.out.println(Arrays.asList(expected) + ":" + Arrays.asList(actual));
        Assert.assertArrayEquals(expected, actual);
    }

}
