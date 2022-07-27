package de.riagade.example.mocking.opencms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmsResourceUtilTest {

    @BeforeEach
    void setup() {
        // TODO: I have an CmsObject in offline with user admin
    }

    @Nested
    class CreateAdminCms {
        @BeforeEach
        void setup() {
            // TODO: I have an CmsObject in online with user guest
        }

        @Test
        void noAdminYet() {
            // Given

            // When

            // Then
        }

        @Test
        void alreadyAdmin() {
            // Given

            // When

            // Then
        }

        @Test
        void whenComingFrom_offlineProject() {
            // Given

            // When

            // Then
        }
    }

    @Nested
    class CreateNewResource {
        @Test
        void noResources_ofThatType() {
            // Given

            // When

            // Then
        }

        @Test
        void withExistingResources_ofThatType() {
            // Given

            // When

            // Then
        }

        @Test
        void folder_insideContent_doesNotExist() {
            // Given

            // When

            // Then
        }

        @Test
        void doNotPublish() {
            // Given

            // When

            // Then
        }

        @Test
        void noContentGiven() {
            // Given

            // When

            // Then
        }
    }

    @Nested
    class GenerateResourcePath {
        @Test
        void noResources_ofThatType() {
            // Given

            // When

            // Then
        }

        @Test
        void withExistingResources_ofThatType() {
            // Given

            // When

            // Then
        }

        @Test
        void withOnlineProject() {
            // Given

            // When

            // Then
        }
    }

    @Nested
    class PublishResource {
        @Test
        void resourceLockedByMe() {
            // Given

            // When

            // Then
        }

        @Test
        void resourceLockedByOther() {
            // Given

            // When

            // Then
        }

        @Test
        void resourceLockedByNone() {
            // Given

            // When

            // Then
        }
    }

    @Nested
    class UnlockResource {
        @Test
        void resourceLockedByMe() {
            // Given

            // When

            // Then
        }

        @Test
        void resourceLockedByOther() {
            // Given

            // When

            // Then
        }

        @Test
        void resourceLockedByNone() {
            // Given

            // When

            // Then
        }
    }
}