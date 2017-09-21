package com.projects.qoderoom.business.logic.parser;

import com.projects.qoderoom.business.model.Device;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Parses list of devices from rozetka.ua
 */
public class RozetkaDeviceParser implements DeviceParser {

    private static final Logger log = LoggerFactory.getLogger(RozetkaDeviceParser.class);

    private final String LINK = "https://rozetka.com.ua/notebooks/c80004/filter/page=";

    /**
     * Parses first 10 pages with devices on rozetka.ua
     *
     * @return list of parsed devices
     */
    public List<Device> parse() {
        List<Device> devices = new LinkedList<Device>();

        for (int i = 1; i < 10; i++) {
            log.debug("Getting devices from url {}", LINK+i);
            List<Device> parsedDevices = getDevicesFromLink(LINK + i);
            if(parsedDevices == null || parsedDevices.size() < 1){
                break;
            }

            devices.addAll(parsedDevices);
        }

        return devices;
    }

    private List<Device> getDevicesFromLink(String link){
        List<Device> devices = new LinkedList<Device>();

        try {
            Document site = Jsoup.connect(link).timeout(30 * 1000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com.ua")
                    .get();

            Elements products = site.select("div.g-i-tile.g-i-tile-catalog");
            for(Element e : products){

                String name = e.select("div.g-i-tile-i-title.clearfix").text();
                int reviews = extractReviewsFromString(e.select("span.g-rating-reviews").text());
                double price = extractPriceFromScript(e.select("script").html());
                String category = site.select("h1").text();

                if(name.length() > 0) {
                    devices.add(new Device(price, reviews, name, category));
                }
            }
        } catch(IOException ioe){
            log.error("Failed to process url {}", link, ioe);
        }

        return devices;
    }

    private double extractPriceFromScript(String script) {
        String priceExample = "%22price%22";
        String statusExample = "%22status%22";
        if(script.contains(priceExample)){
            int pricePosition = script.indexOf(priceExample);
            int statusPosition = script.indexOf(statusExample);

            String priceString = script.substring(pricePosition + priceExample.length(), statusPosition);
            try{
                return Double.parseDouble(priceString.substring(3, priceString.length() - 3));
            } catch (NumberFormatException nfe){
                log.error("Failed to get price from String", nfe);
            }
        }

        return 0d;
    }

    private int extractReviewsFromString(String reviews){
        if(reviews != null){
            String[] words = reviews.split(" ");
            for(String w : words){
                if(w.matches("[0-9]+")){
                    return Integer.parseInt(w);
                }
            }
        }

        return 0;
    }
}
