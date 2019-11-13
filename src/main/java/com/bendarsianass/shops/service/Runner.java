package com.bendarsianass.shops.service;

import com.bendarsianass.shops.entity.Point;
import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.repository.UserRepository;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private ShopService shopService;


    @Autowired UserService userService;

    @Override
    public void run(String... args) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("razouq");
        userEntity.setPassword("123456");
        userService.save(userEntity);
        addAllShopsToDatabase();
    }

    private void addAllShopsToDatabase() throws Exception{
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("shops.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String json = null;
        while((json = br.readLine()) != null) {
            JsonReader jsonReader =  new JsonReader(new StringReader(json));
            jsonReader.beginObject();

            Shop shop = new Shop();
            Point point = new Point();

            while(jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("picture")) {
                    String picture = jsonReader.nextString();
                    System.out.println(picture);
                    shop.setPicture(picture);
                } else if (name.equals("name")) {
                    String shopName = jsonReader.nextString();
                    System.out.println(shopName);
                    shop.setName(shopName);
                } else if (name.equals("email")) {
                    String email = jsonReader.nextString();
                    System.out.println(email);
                    shop.setEmail(email);
                } else if (name.equals("city")) {
                    String city = jsonReader.nextString();
                    System.out.println(city);
                    shop.setCity(city);
                } else if (name.equals("location")) {
                    List<Double> location = readLocation(jsonReader);
                    point.setLat(location.get(0));
                    point.setLon(location.get(1));
                    System.out.println(location);
                } else {
                    jsonReader.skipValue();
                }
            }
            shopService.saveShopAndPoint(shop, point);
        }
        System.out.println("end !");
    }

    private List<Double> readLocation(JsonReader jsonReader) throws Exception {
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if(name.equals("coordinates")) {
                return readDoublesArray(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        return null;
    }

    private List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

}
