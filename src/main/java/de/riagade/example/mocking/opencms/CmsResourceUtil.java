package de.riagade.example.mocking.opencms;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;
import org.opencms.file.types.I_CmsResourceType;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;
import org.opencms.xml.content.CmsXmlContent;

import java.util.Collections;
import java.util.Optional;

public class CmsResourceUtil {
    public static final Log log = OpenCms.getLog(CmsResourceUtil.class);
    private static final String OFFLINE_PROJECT = "Offline";

    public static Optional<CmsObject> createAdminCms(CmsJspActionElement actionElement, String user, String password) {
        var project = OFFLINE_PROJECT;
        log.debug(String.format("create admin cms for user '%s' in project '%s'", user, project));
        try {
            var cms = OpenCms.initCmsObject(actionElement.getCmsObject());
            cms.loginUser(user, password);
            if (cms.getRequestContext().getCurrentProject().isOnlineProject()) {
                cms.getRequestContext().setCurrentProject(cms.readProject(project));
            }
            return Optional.of(cms);
        } catch (CmsException e) {
            log.error("problems on creating the admin cms", e);
        }
        return Optional.empty();
    }

    public static boolean createNewResource(CmsObject adminCms, I_CmsResourceType type, CmsXmlContent xmlContent,
                                            boolean publishImmediately) {
        var resourcePath = generateResourcePath(adminCms, type);
        try {
            log.debug(String.format("create new resource on path %s" , resourcePath));
            var resource = adminCms.createResource(resourcePath, type, xmlContent.marshal(),
                    Collections.emptyList());
            if (publishImmediately) {
                publishResource(adminCms, resource);
            } else {
                unlockResource(adminCms, resource);
            }
            return true;
        } catch (CmsException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String generateResourcePath(CmsObject adminCms, I_CmsResourceType type) {
        // TODO: generate path for resource
        return null;
    }

    public static void publishResource(CmsObject adminCms, CmsResource resource) {
        var resourcePath = adminCms.getRequestContext().removeSiteRoot(resource.getRootPath());
        log.debug(String.format("create new resource on path %s" , resourcePath));
        try {
            OpenCms.getPublishManager().publishResource(adminCms, resourcePath);
        } catch (Exception e) {
            log.error(String.format("error while publishing resource on path %s", resourcePath), e);
        }
    }

    public static void unlockResource(CmsObject adminCms, CmsResource resource) {
        var resourcePath = resource.getRootPath();
        try {
            log.debug(String.format("unlock resource on path %s", resourcePath));
            adminCms.unlockResource(resource);
        } catch (CmsException e) {
            log.error(String.format("could not unlock resource on path %s", resourcePath), e);
        }
    }
}
