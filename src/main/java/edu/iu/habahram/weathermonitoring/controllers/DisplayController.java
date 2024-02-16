package edu.iu.habahram.weathermonitoring.controllers;

import edu.iu.habahram.weathermonitoring.model.CurrentConditionDisplay;
import edu.iu.habahram.weathermonitoring.model.ForecastDisplay;
import edu.iu.habahram.weathermonitoring.model.Observer;
import edu.iu.habahram.weathermonitoring.model.StatisticsDisplay;
import edu.iu.habahram.weathermonitoring.model.WeatherData;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/displays")
public class DisplayController {

    private CurrentConditionDisplay currentConditionDisplay;
    private StatisticsDisplay weatherStatsDisplay; // Add WeatherStatsDisplay field
    private ForecastDisplay forecastDisplay;

    public DisplayController(CurrentConditionDisplay currentConditionDisplay, StatisticsDisplay weatherStatsDisplay, ForecastDisplay forecastDisplay) {
        this.currentConditionDisplay = currentConditionDisplay;
        this.weatherStatsDisplay = weatherStatsDisplay;
        this.forecastDisplay = forecastDisplay;
    }

    @GetMapping
    public ResponseEntity index() {
        String html = String.format("<h1>Available screens:</h1>");
        html += "<ul>";
        
        // Link for Current Condition Display
        html += "<li>";
        html += String.format("<a href=\"/displays/%s\">%s</a>", currentConditionDisplay.id(), currentConditionDisplay.name());
        html += "</li>";

        // Link for Weather Stats Display
        html += "<li>";
        html += String.format("<a href=\"/displays/%s\">%s</a>", weatherStatsDisplay.id(), weatherStatsDisplay.name());
        html += "</li>";

         // Link for Forecast Display
         html += "<li>";
         html += String.format("<a href=\"/displays/%s\">%s</a>", forecastDisplay.id(), forecastDisplay.name());
         html += "</li>";

        html += "</ul>";

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(html);
    }


    @GetMapping("/{id}")
    public ResponseEntity display(@PathVariable String id) {
        String html = "";
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            html = currentConditionDisplay.display();
            status = HttpStatus.FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }

    @GetMapping("/weather-stats")
    public ResponseEntity weatherStatsDisplay() {
        String html = weatherStatsDisplay.display();
        return ResponseEntity.status(HttpStatus.FOUND).body(html);
    }

    @GetMapping("/currentCondition-display")
    public ResponseEntity currentConditionDisplay() {
        String html = currentConditionDisplay.display();
        return ResponseEntity.status(HttpStatus.FOUND).body(html);
    }

    @GetMapping("/forecast-display")
    public ResponseEntity forecastDisplay() {
        String html = forecastDisplay.display();
        return ResponseEntity.status(HttpStatus.FOUND).body(html);
    }

    @GetMapping("/{id}/subscribe")
    public ResponseEntity subscribe(@PathVariable String id) {
        String html = "";
        HttpStatus status = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            currentConditionDisplay.subscribe();
            html = "Subscribed!";
            status = HttpStatus.FOUND;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }

    @GetMapping("/{id}/unsubscribe")
    public ResponseEntity unsubscribe(@PathVariable String id) {
        String html = "";
        HttpStatus status = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            currentConditionDisplay.unsubscribe();
            html = "Unsubscribed!";
            status = HttpStatus.FOUND;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }
}
