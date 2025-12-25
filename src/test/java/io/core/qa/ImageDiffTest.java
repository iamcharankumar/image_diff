package io.core.qa;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class ImageDiffTest {

    @Test(dataProvider = "imageFilesProvider")
    public void bulkVisualComparisonTest(String fileName) {
        String baseDir = "./src/test/resources";
        String diffDir = "./src/test/resources/visual-diffs/";
        String actual = baseDir + "/actual/actual_" + fileName;
        String expected = baseDir + "/expected/expected_" + fileName;
        String diff = diffDir + "DIFF_" + fileName;

        boolean isMatch = ImageComparator.compare(expected, actual, diff);

        Assert.assertTrue(isMatch, "Visual Mismatch for file: " + diff);
    }

    @DataProvider(name = "imageFilesProvider", parallel = true)
    public Object[][] getImageFiles() {
        return new Object[][]{
                {"sign_in.png"},
                {"fb.png"},
                {"my_sign_in.png"},
                {"github.png"}
        };
    }
}