package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.web.dto.StatisticsDTO;
import gr.hua.dit.Ergasia.core.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
