package de.riagade.example.mocking.opencms;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.opencms.file.CmsObject;
import org.opencms.file.types.I_CmsResourceType;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;
import org.opencms.xml.CmsXmlContentDefinition;
import org.opencms.xml.content.CmsXmlContent;
import org.opencms.xml.content.CmsXmlContentFactory;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static de.riagade.example.mocking.opencms.CmsResourceUtil.*;
import static de.riagade.example.mocking.opencms.CmsXMLContentPathUtil.*;

public class AfterWebform {
    private final Log log = OpenCms.getLog(this);
    private static final String USER_NAME = "Admin";
    private static final String USER_PASSWORD = "admin";
    private static final Locale LOCALE = Locale.GERMAN;
    private static final String UTF8_ENCODING = "UTF-8";
    private static final boolean SHOULD_PUBLISH = false;

    /**
     * Erstellt ein neues OpenCms-Objekt des angegebenen Typs im Standardverzeichnis
     *
     * @param jsp       für die Bearbeitung wird daraus ein neues {@link CmsObject} im Scope des Admin-Users erstellt
     * @param type      der {@link I_CmsResourceType} des Objekts, welches erstellt werden soll
     * @param fields    die Felder mit Pfad als key<br>
     *                  valide Pfade:
     *                  <ul>
     *                      <li>/first/second</li>
     *                      <li>/first/second/</li>
     *                      <li>/first/second[1]</li>
     *                      <li>/first/second[7]</li>
     *                      <li>/first/second[1]/third_here/fourth[123]/fifth</li>
     *                  </ul>
     *                  nicht valide Pfade:
     *                  <ul>
     *                      <li>//first/second</li>
     *                      <li>/first//second</li>
     *                      <li>/first/second[0]</li>
     *                      <li>/first/second[0]/third_here</li>
     *                  </ul>
     * @return ein {@link Boolean} der aussagt, ob das Objekt erfolgreich erstellt wurde
     */
    public boolean createOpenCmsObject(CmsJspActionElement jsp, I_CmsResourceType type, Map<String, String> fields) {
        var locale = LOCALE;
        var typeName = type.getTypeName();
        var optionalAdminCms = createAdminCms(jsp, USER_NAME, USER_PASSWORD);
        if(optionalAdminCms.isPresent()) {
            var adminCms = optionalAdminCms.get();
            var optionalXmlContent = getXmlContent(adminCms, typeName, locale);
            if(optionalXmlContent.isPresent()) {
                var xmlContent = optionalXmlContent.get();
                insertFields(adminCms, xmlContent, fields, locale);
                return createNewResource(adminCms, type, xmlContent, SHOULD_PUBLISH);
            }
        } else {
            log.error(String.format("can't create element of type %s due to not being able to create admin cms object",
                    typeName));
        }
        return false;
    }

    private Optional<CmsXmlContent> getXmlContent(CmsObject adminCms, String typeName, Locale locale) {
        log.debug(String.format("create xml content for resource of type %s and locale %s", typeName,
                locale.getLanguage()));
        try {
            var definition = CmsXmlContentDefinition.getContentDefinitionForType(adminCms,
                    typeName);
            return Optional.of(CmsXmlContentFactory.createDocument(adminCms, locale, UTF8_ENCODING, definition));
        } catch (CmsException e) {
            log.error(String.format("typename %s does not exists", typeName), e);
        }
        return Optional.empty();
    }

    private void insertFields(CmsObject adminCms, CmsXmlContent xmlContent, Map<String, String> fields, Locale locale) {
        log.debug(String.format("add / fill fields into locale %s", locale.getLanguage()));
        for(var entry : fields.entrySet()) {
            var path = assureCorrectEnding(entry.getKey());
            var value = StringUtils.stripToEmpty(entry.getValue());
            insertField(adminCms, xmlContent, path, value, locale);
        }
    }

    private void insertField(CmsObject adminCms, CmsXmlContent xmlContent, String path, String value, Locale locale) {
        if(!value.isEmpty()) {
            createPathsToTarget(adminCms, xmlContent, path, locale);
            log.debug(String.format("set field on path %s to value %s", path, value));
            xmlContent.getValue(path, locale).setStringValue(adminCms, value);
        }
    }

    private void createPathsToTarget(CmsObject adminCms, CmsXmlContent xmlContent, String path, Locale locale) {
        var stepsToTarget = createStepsToTarget(path);
        for(var stepPath : stepsToTarget) {
            if (!xmlContent.hasValue(stepPath, locale)) {
                int position = positionOfPath(stepPath);
                log.debug(String.format("adding field with path %s at position %d", path, position));
                xmlContent.addValue(adminCms, path, locale, position);
            }
        }
    }
}
