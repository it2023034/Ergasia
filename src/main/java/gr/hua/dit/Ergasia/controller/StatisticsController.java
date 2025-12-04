package gr.hua.dit.Ergasia.controller;

import gr.hua.dit.adminbt.adminbt.model.StatisticsDTO;
import gr.hua.dit.adminbt.adminbt.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public StatisticsDTO getStatistics() {
        return statisticsService.getStatistics();
    }
}
