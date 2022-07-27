package de.riagade.example.mocking.opencms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmsXMLContentPathUtilTest {
    private String path;

    @BeforeEach
    void setup() {
        path = null;
    }

    @Nested
    class CreateStepsToTarget {
        @Test
        void singleStep() {
            // Given
            path = "/first";
            var expected = new String[]{"/first"};

            // When
            var result = CmsXMLContentPathUtil.createStepsToTarget(path);

            // Then
            assertArrayEquals(expected, result);
        }

        @Test
        void singleStep_withNumber() {
            // Given
            path = "/first[2]";
            var expected = new String[]{"/first[2]"};

            // When
            var result = CmsXMLContentPathUtil.createStepsToTarget(path);

            // Then
            assertArrayEquals(expected, result);
        }

        @Test
        void multipleSteps() {
            // Given
            path = "/first/second/third";
            var expected = new String[]{"/first","/first/second","/first/second/third"};

            // When
            var result = CmsXMLContentPathUtil.createStepsToTarget(path);

            // Then
            assertArrayEquals(expected, result);
        }

        @Test
        void multipleSteps_withNumbers() {
            // Given
            path = "/first/second[2]/third[3]";
            var expected = new String[]{"/first","/first/second[2]","/first/second[2]/third[3]"};

            // When
            var result = CmsXMLContentPathUtil.createStepsToTarget(path);

            // Then
            assertArrayEquals(expected, result);
        }
    }

    @Nested
    class AssureCorrectEnding {
        @Test
        void noSlash_atTheEnd() {
            // Given
            path = "/first";
            var expected = path;

            // When
            var result = CmsXMLContentPathUtil.assureCorrectEnding(path);

            // Then
            assertEquals(expected, result);
        }

        @Test
        void oneSlash_atTheEnd() {
            // Given
            path = "/first/";
            var expected = "/first";

            // When
            var result = CmsXMLContentPathUtil.assureCorrectEnding(path);

            // Then
            assertEquals(expected, result);
        }

        @Test
        void multipleSlashes_atTheEnd() {
            // Given
            path = "/first//";
            var expected = "/first";

            // When
            var result = CmsXMLContentPathUtil.assureCorrectEnding(path);

            // Then
            assertEquals(expected, result);
        }
    }

    @Nested
    class PositionOfPath {
        @Test
        void endsWithSlash() {
            // Given
            path = "/first[1]/";
            var expected = 0;

            // When
            var result = CmsXMLContentPathUtil.positionOfPath(path);

            // Then
            assertEquals(expected, result);
        }

        @Test
        void noNumberGiven() {
            // Given
            path = "/first";
            var expected = 0;

            // When
            var result = CmsXMLContentPathUtil.positionOfPath(path);

            // Then
            assertEquals(expected, result);
        }

        @Test
        void multipleDigitsGiven() {
            // Given
            path = "/first[999]";
            var expected = 998;

            // When
            var result = CmsXMLContentPathUtil.positionOfPath(path);

            // Then
            assertEquals(expected, result);
        }

        @Test
        void multipleStepsBefore() {
            // Given
            path = "/first/second/third[1]";
            var expected = 0;

            // When
            var result = CmsXMLContentPathUtil.positionOfPath(path);

            // Then
            assertEquals(expected, result);
        }

        @Test
        void multipleStepsWithPosition() {
            // Given
            path = "/first/second[9]/third[1]";
            var expected = 0;

            // When
            var result = CmsXMLContentPathUtil.positionOfPath(path);

            // Then
            assertEquals(expected, result);
        }
    }
}