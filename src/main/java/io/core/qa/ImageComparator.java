package io.core.qa;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
public class ImageComparator {

    // Threshold: 0.0 means exact match. 5.0 allows minor pixel noise (good for cross-OS).
    private static final double PIXEL_TOLERANCE = 0.1;

    /**
     * Compares two images from file paths.
     *
     * @param expectedPath Path to the baseline image (The "Golden" image)
     * @param actualPath   Path to the new image
     * @param resultPath   Path where the "Diff" image should be saved if they fail
     * @return boolean true if images match, false otherwise
     */
    public static boolean compare(String expectedPath, String actualPath, String resultPath) {
        // 1. Load Images
        BufferedImage expected = ImageComparisonUtil.readImageFromResources(String.valueOf(new File(expectedPath)));
        BufferedImage actual = ImageComparisonUtil.readImageFromResources(String.valueOf(new File(actualPath)));

        // 2. Configure Comparison
        ImageComparison comparison = new ImageComparison(expected, actual);
        comparison.setThreshold((int) PIXEL_TOLERANCE); // Precision level
        comparison.setDestination(new File(resultPath)); // Where to save the diff

        // 3. Compare
        ImageComparisonResult result = comparison.compareImages();
        ImageComparisonState state = result.getImageComparisonState();

        // 4. Handle Result
        if (state != ImageComparisonState.MATCH) {
            // It automatically saves the difference image to 'resultPath' only if they differ
            log.error("Visual mismatch found! Diff saved at: {}", resultPath);
            return false;
        }
        return true;
    }
}