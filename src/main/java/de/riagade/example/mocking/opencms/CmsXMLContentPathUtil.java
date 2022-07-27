package de.riagade.example.mocking.opencms;

import org.apache.commons.lang.StringUtils;

public class CmsXMLContentPathUtil {
    public static final String SPLIT_SIGN = "/";

    /**
     * erstellt eine Schrittfolge von Pfadangaben zu einem Ziel<br>
     * jeder Schritt enthält den vollständigen Pfad bis dahin<br>
     * der letzte Schritt ist das Ziel<br>
     *
     * @param path  der Pfad der in Teilschritte aufgeteilt werden soll
     * @return ein Array, das garantiert eine aufsteigende Sortierung hat
     */
    public static String[] createStepsToTarget(String path) {
        var stepAmount = StringUtils.countMatches(path, SPLIT_SIGN);
        var steps = new String[stepAmount];
        for(var i = 0; i < stepAmount; i++) {
            steps[i] = getStep(i, path);
        }
        return steps;
    }

    private static String getStep(int step, String path) {
        var steps = path.split(SPLIT_SIGN);
        var resultPath = new StringBuilder();
        for(var i = 0; i <= step; i++) {
            resultPath.append(SPLIT_SIGN);
            resultPath.append(steps[i+1]);
        }
        return resultPath.toString();
    }

    public static String assureCorrectEnding(String path) {
        while(path.endsWith(SPLIT_SIGN)) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * die Position des Pfades wird bestimmt, diese ist entweder am Ende des Pfades mittels [n+1] angegeben
     * oder wird per Default als 0 bestimmt
     *
     * @param path  der Pfad an dessen Ende ggf. eine Nummerierung steht
     * @return die Verwendbare Positionsangabe zum Erstellen eines XML-Knotens
     */
    public static int positionOfPath(String path) {
        path = assureCorrectEnding(path);
        if(path.endsWith("]")) {
            var beginNumber = path.lastIndexOf("[");
            var endNumber = path.lastIndexOf("]");
            var strNumber = path.substring(beginNumber + 1, endNumber);
            return Integer.parseInt(strNumber) - 1;
        }
        return 0;
    }
}
