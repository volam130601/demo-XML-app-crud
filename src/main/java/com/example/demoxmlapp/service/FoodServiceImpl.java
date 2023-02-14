package com.example.demoxmlapp.service;

import com.example.demoxmlapp.entity.Food;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private static final String fileName = "menu.xml";
    private static List<Food> list = new ArrayList<>();

    public static Long getLastId() {
        return list.get(list.size() - 1).getId();
    }

    @Override
    public List<Food> getFoods() {
        return list;
    }


    @Override
    public void setFoods() {
        list = readXML();
    }

    public int writeXMl(List<Food> foods) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("breakfast_menu");
            doc.appendChild(root);

            for (Food item : foods) {
                Element food = doc.createElement("food");
                root.appendChild(food);
                food.setAttribute("id", String.valueOf(item.getId()));

                Element name = doc.createElement("name");
                name.setTextContent(item.getName());
                Element price = doc.createElement("price");
                price.setTextContent("$" + String.valueOf(item.getPrice()));
                Element description = doc.createElement("description");
                description.setTextContent(item.getDescription());
                Element calories = doc.createElement("calories");
                calories.setTextContent(String.valueOf(item.getCalories()));

                food.appendChild(name);
                food.appendChild(price);
                food.appendChild(description);
                food.appendChild(calories);
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(doc), new StreamResult(new File(fileName)));
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public List<Food> readXML() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<Food> foods = new ArrayList<>();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("food");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String price = element.getElementsByTagName("price").item(0).getTextContent();
                    String description = element.getElementsByTagName("description").item(0).getTextContent();
                    String calories = element.getElementsByTagName("calories").item(0).getTextContent();
                    price = price.substring(1);
                    foods.add(Food.builder()
                            .id(Long.parseLong(id))
                            .name(name)
                            .price(Double.parseDouble(price))
                            .description(description)
                            .calories(Integer.parseInt(calories))
                            .build());

                }
            }
            return foods;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Food food) {
        food.setId(getLastId() + 1);
        list.add(food);
        writeXMl(list);
    }

    @Override
    public void update(Food food) {
        for (Food item : list) {
            if (item.getId().equals(food.getId())) {
                item.setName(food.getName());
                item.setPrice(food.getPrice());
                item.setDescription(food.getDescription());
                item.setCalories(food.getCalories());
            }
        }
        writeXMl(list);
    }

    @Override
    public void delete(Long foodId) {
        list.removeIf(item -> item.getId().equals(foodId));
        writeXMl(list);
    }

    @Override
    public Food findOneById(Long foodId) {
        return list.stream().filter(food -> food.getId().equals(foodId)).findFirst().get();
    }

    @Override
    public List<Food> findBySearchValue(String key) {
        return list.stream().filter(item -> item.getName().toLowerCase().trim().contains(key.toLowerCase().trim())).collect(Collectors.toList());
    }
}
