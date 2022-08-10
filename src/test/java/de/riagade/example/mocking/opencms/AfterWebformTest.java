package de.riagade.example.mocking.opencms;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsRequestContext;
import org.opencms.file.types.I_CmsResourceType;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AfterWebformTest {

    @BeforeEach
    void setUp() {
        // TODO: CmsObject with online project and guest user
        // TODO: CmsJspActionElement with CmsObject inside it
        // TODO: user and password that exist
        // TODO: create ResourceType
        // TODO: create XmlContentDefinition for ResourceType
        // TODO: create CmsXmlContentFactory that returns document
    }

    @AfterEach
    void teatDown() {
    }

    @Test
    void validData() throws CmsException {
        var cmsRequestContext = mock(CmsRequestContext.class);
        var log = mock(Log.class);
        var cmsObject = mock(CmsObject.class);
        var jsp = mock(CmsJspActionElement.class);
        var type = mock(I_CmsResourceType.class);
        var opencmsClass = mockStatic(OpenCms.class);
        when(jsp.getCmsObject()).thenReturn(cmsObject);
        opencmsClass.when(() -> OpenCms.initCmsObject(any(CmsObject.class))).thenReturn(mock(CmsObject.class));
        opencmsClass.when(() -> OpenCms.getLog(any())).thenReturn(log);
        when(cmsObject.getRequestContext()).thenReturn(cmsRequestContext);

        // Given
        var afterWebform = new AfterWebform();
        var fieldMap = new HashMap<String, String>();
        var createPath = "/.content/test/test_00001.xml";

        // When
        afterWebform.createNewCmsResource(jsp, type, fieldMap);

        // Then
        var createdResource = jsp.getCmsObject().readResource(createPath);
        assertNotNull(createdResource);
        assertEquals(type, OpenCms.getResourceManager().getResourceType(createdResource));
    }

    @Test
    void fromProject_offline() {
        // Given

        // When

        // Then
    }

    @Test
    void fieldValuesNull() {
        // Given

        // When

        // Then
    }

    @Test
    void fieldValues_dontMatch_contentValues() {
        // Given

        // When

        // Then
    }

    @Test
    void resourceType_notFound() {
        // Given

        // When

        // Then
    }

    @Test
    void wrongUser() {
        // Given

        // When

        // Then
    }
}