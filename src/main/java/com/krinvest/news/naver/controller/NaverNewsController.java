package com.krinvest.news.naver.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.krinvest.news.naver.dto.Item;
import com.krinvest.news.naver.service.NaverNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NaverNewsController {

    @Autowired
    private NaverNewsService naverNewsService;

    @GetMapping("/naver/search")
    public String searchNews(@RequestParam String name, Model model){
        String items = naverNewsService.searchNews(name);
        JsonArray itemsArray = JsonParser.parseString(items).getAsJsonArray();
        List<Item> itemsList = new ArrayList<>();
        for(JsonElement element : itemsArray) {
            JsonObject itemObject = element.getAsJsonObject();
            Item item = new Item(
                    itemObject.get("title").getAsString(),
                    itemObject.get("originallink").getAsString(),
                    itemObject.get("link").getAsString(),
                    itemObject.get("description").getAsString(),
                    itemObject.get("pubDate").getAsString()
            );
            itemsList.add(item);
        }
        model.addAttribute("items", itemsList);

        return "news/naverOrgNews";
    }
}
