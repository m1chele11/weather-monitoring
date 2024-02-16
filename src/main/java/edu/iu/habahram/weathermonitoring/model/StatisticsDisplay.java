package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class StatisticsDisplay implements Observer, DisplayElement {
    private float averageTemperature;
    private float maxHumidity;
    private float minPressure;

    private Subject weatherData;

    public StatisticsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObserver(this);
    }

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/display2.png); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Average Temperature: %s</label><br />", averageTemperature);
        html += String.format("<label>Max Humidity: %s</label><br />", maxHumidity);
        html += String.format("<label>Min Pressure: %s</label>", minPressure);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        // Assuming weather stats are calculated based on received data
        // You may need to implement your own logic for calculating average temperature,
        // max humidity, and min pressure based on the updates from the subject.
        // This is just a placeholder logic for demonstration purposes.
        averageTemperature = (averageTemperature + temperature) / 2;
        maxHumidity = Math.max(maxHumidity, humidity);
        minPressure = (minPressure == 0 || pressure < minPressure) ? pressure : minPressure;
    }

    @Override
    public String name() {
        return "Weather Stats Display";
    }

    @Override
    public String id() {
        return "weather-stats";
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }

    
}