package com.br.playmakerhub.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FIFAService {

    public String getInitialSeasonByFIFAVersion(String fifaVersion) {
        return switch (fifaVersion) {
            case "FIFA 15" -> "temporada-14-15";
            case "FIFA 16" -> "temporada-15-16";
            case "FIFA 17" -> "temporada-16-17";
            case "FIFA 18" -> "temporada-17-18";
            case "FIFA 19" -> "temporada-18-19";
            case "FIFA 20" -> "temporada-19-20";
            case "FIFA 21" -> "temporada-20-21";
            case "FIFA 22" -> "temporada-21-22";
            case "FIFA 23" -> "temporada-22-23";
            case "EA FC 24" -> "temporada-23-24";
            case "EA FC 25" -> "temporada-24-25";
            case "EA FC 26" -> "temporada-25-26";
            case "EA FC 27" -> "temporada-26-27";

            default -> fifaVersion;
        };
    }

    public List<String> getFifaVersions() {
        return List.of("FIFA 15", "FIFA 16", "FIFA 17", "FIFA 18", "FIFA 19", "FIFA 20",
                "FIFA 21", "FIFA 22", "FIFA 23", "EA FC 24", "EA FC 25");
    }

    public List<String> getFifaSeasons() {
        return List.of(
                "temporada-14-15", "temporada-15-16", "temporada-16-17", "temporada-17-18",
                "temporada-18-19", "temporada-19-20", "temporada-20-21", "temporada-21-22",
                "temporada-22-23", "temporada-23-24", "temporada-24-25", "temporada-25-26",
                "temporada-26-27", "temporada-27-28", "temporada-28-29", "temporada-29-30",
                "temporada-30-31"
        );
    }

}
