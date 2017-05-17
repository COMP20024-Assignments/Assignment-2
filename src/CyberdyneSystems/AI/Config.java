package CyberdyneSystems.AI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/** COMP30024 Artificial Intelligence
 Config class
 George Juliff - 624946
 Thomas Miles - 626263

 Reads the config.xml file to easily set weighting and depth parameters for T800 AI. This format allows easy, stable
 access to these parameters for training the AI and manual adjustment. Needs to be in the same directory as config.xml
 */
public class Config {


    // Modifier weightings for evaluation functions, _e represents enemy player's state

    private static float W1, W1_e;      // Overall forwardness of pieces along the board
    private static float W2, W2_e, W2x; // Amount of blocked directions, W2x - multiplier for forward blocking
    private static float W3, W3_e;      // Pieces remaining
    private static float W4f,W4n;       // Modifiers for first move made, f - forward first, n - another direction first
    private static float W4f_e,W4n_e;

    private static int depthInit, depthCutoff; // Variables for iterative deepening search
    private static int heapSize;               // maximum memory allowed (kB)


    /**
     * Default constructor, scans the config file, all parameters are kept in this class and accessed by get/set methods
     */
    public Config() {
        try {
            File configXML = new File("D:\\Documents\\GitHub\\Assignment-2\\config\\config.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(configXML);


            NodeList depthConfig = doc.getElementsByTagName("depthConfig");
            NodeList forwardness = doc.getElementsByTagName("forwardness");
            NodeList blockedness = doc.getElementsByTagName("blockedness");
            NodeList emptyness   = doc.getElementsByTagName("emptyness");
            NodeList firstFwd    = doc.getElementsByTagName("firstFwd");
            NodeList firstN      = doc.getElementsByTagName("firstN");

            Node node;
            Element element;


            node = depthConfig.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                depthInit = Integer.parseInt(element.getElementsByTagName("min").item(0).getTextContent());
                depthCutoff = Integer.parseInt(element.getElementsByTagName("max").item(0).getTextContent());
                heapSize = Integer.parseInt(element.getElementsByTagName("heapSize").item(0).getTextContent());
            }

            node = forwardness.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                W1 = Float.parseFloat(element.getElementsByTagName("agent").item(0).getTextContent());
                W1_e = Float.parseFloat(element.getElementsByTagName("opponent").item(0).getTextContent());
            }

            node = blockedness.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                W2 = Float.parseFloat(element.getElementsByTagName("agent").item(0).getTextContent());
                W2_e = Float.parseFloat(element.getElementsByTagName("opponent").item(0).getTextContent());
                W2x = Float.parseFloat(element.getElementsByTagName("fwdMultiplier").item(0).getTextContent());
            }

            node = emptyness.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                W3 = Float.parseFloat(element.getElementsByTagName("agent").item(0).getTextContent());
                W3_e = Float.parseFloat(element.getElementsByTagName("opponent").item(0).getTextContent());
            }

            node = firstFwd.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                W4f = Float.parseFloat(element.getElementsByTagName("agent").item(0).getTextContent());
                W4f_e = Float.parseFloat(element.getElementsByTagName("opponent").item(0).getTextContent());
            }

            node = firstN.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                W4n = Float.parseFloat(element.getElementsByTagName("agent").item(0).getTextContent());
                W4n_e = Float.parseFloat(element.getElementsByTagName("opponent").item(0).getTextContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getters for all values
     */
    public static float getW1() {
        return W1;
    }

    public static float getW1e() {
        return W1_e;
    }

    public static float getW2() {
        return W2;
    }

    public static float getW2e() {
        return W2_e;
    }

    public static float getW2x() {
        return W2x;
    }

    public static float getW3() {
        return W3;
    }

    public static float getW3e() {
        return W3_e;
    }

    public static float getW4f() {
        return W4f;
    }

    public static float getW4n() {
        return W4n;
    }

    public static float getW4f_e() {
        return W4f_e;
    }

    public static float getW4n_e() {
        return W4n_e;
    }

    public static int getDepthInit() {
        return depthInit;
    }

    public static int getDepthCutoff() {
        return depthCutoff;
    }

    public static int getHeapSize() {
        return heapSize;
    }

}
