package asia.chengfu.kvm.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.*;

import java.util.*;

/**
 * VirshXmlParser类用于解析XML内容并将其格式化为Map对象。
 */
public class VirshXmlParser {

    /**
     * 解析XML内容并格式化为Map对象
     * @param xmlContent XML内容
     * @return 格式化后的Map对象
     */
    public static Map<String, Object> parseXmlAndFormat(String xmlContent){
        Document document = XmlUtil.parseXml(xmlContent);
        Map<String, Object> map = parseDocumentElement(document.getDocumentElement());
        return formatXmlMap(map);
    }

    /**
     * 解析XML元素并将其转换为Map对象
     * @param element XML元素
     * @return 转换后的Map对象
     */
    private static Map<String, Object> parseDocumentElement(Element element) {
        // 获取节点名称
        String tagName = element.getTagName();

        // 获取节点属性
        NamedNodeMap attributes = element.getAttributes();
        int attributesLength = attributes.getLength();

        Map<String, Object> attributesMap = new LinkedHashMap<>();
        for (int i = 0; i < attributesLength; i++) {
            Node node = attributes.item(i);
            attributesMap.put(node.getNodeName(), node.getNodeValue());
        }

        // 获取子节点
        NodeList childNodes = element.getChildNodes();
        int nodesLength = childNodes.getLength();
        for (int i = 0; i < nodesLength; i++) {
            Node node = childNodes.item(i);

            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            // 如果是元素节点，递归解析
            if (XmlUtil.isElement(node)) {
                Map<String, Object> map = parseDocumentElement((Element) node);
                if (!map.isEmpty()) {
                    attributesMap.compute(nodeName, (k, v) -> {
                        if (v == null) {
                            List<Map<String, Object>> value = new ArrayList<>();
                            value.add(map);
                            return value;
                        } else {
                            if (v instanceof List) {
                                List list = (List) v;
                                list.add(map);
                                return list;
                            }
                            return v;
                        }
                    });
                }
            } else {
                // 如果是文本节点且不为空，将其添加到Map中
                if (StrUtil.equals(nodeName, "#text") && StrUtil.isNotBlank(nodeValue)) {
                    attributesMap.put("value", nodeValue);
                }
            }
        }

        return attributesMap;
    }

    /**
     * 格式化解析后的XML Map对象
     * @param map 解析后的XML Map对象
     * @return 格式化后的Map对象
     */
    private static Map<String, Object> formatXmlMap(Map<String, Object> map) {
        Map<String, Object> formattedMap = new LinkedHashMap<>();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                formattedMap.put(key, value);
            } else if (value instanceof List) {
                List list = (List) value;
                int size = list.size();

                if (size == 1) {
                    Object firstElement = list.get(0);
                    if (firstElement instanceof Map) {
                        Map mapValue = (Map) firstElement;
                        if (mapValue.containsKey("value") && mapValue.size() == 1) {
                            formattedMap.put(key, mapValue.get("value"));
                        } else {
                            formattedMap.put(key, formatXmlMap(mapValue));
                        }
                    }
                } else {
                    List<Map<String, Object>> formattedList = new ArrayList<>();
                    formattedMap.put(key + "s", formattedList);
                    for (Object o : list) {
                        if (o instanceof Map) {
                            Map mapValue = (Map) o;
                            formattedList.add(formatXmlMap(mapValue));
                        }
                    }
                }
            }
        }
        return formattedMap;
    }
}
