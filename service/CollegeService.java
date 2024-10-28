package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CollegeDTO;
import entity.College;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollegeService {

    private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);
    private final String apiUrl = "http://universities.hipolabs.com/search";

    // Fetch colleges from the API and map to College objects
    public List<College> fetchCollegesFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String response = restTemplate.getForObject(apiUrl, String.class);

            List<CollegeDTO> collegeDTOs = objectMapper.readValue(response, new TypeReference<List<CollegeDTO>>() {});
            logger.info("Fetched {} colleges from the API.", collegeDTOs.size());

            // Map DTO to entity, taking only the first domain or an empty string if no domains
            List<College> colleges = collegeDTOs.stream()
                    .map(dto -> new College(
                            dto.getName(),
                            dto.getCountry(),
                            dto.getDomains() != null && !dto.getDomains().isEmpty() ? dto.getDomains().get(0) : ""
                    ))
                    .collect(Collectors.toList());

            logger.info("Mapped {} colleges to College entity objects.", colleges.size());
            return colleges;

        } catch (Exception e) {
            logger.error("Error fetching data from API: ", e);
            return List.of(); // Return an empty list on error
        }
    }

    // Filtering methods
    public List<College> getCollegesByName(String collegeName) {
        List<College> filtered = fetchCollegesFromAPI().stream()
                .filter(college -> college.getName().equalsIgnoreCase(collegeName))
                .sorted(Comparator.comparingInt(college -> college.getName().length()))
                .collect(Collectors.toList());

        logger.info("Filtered {} colleges by name: {}", filtered.size(), collegeName);
        return filtered;
    }

    public List<College> getCollegesByCountry(String country) {
        List<College> filtered = fetchCollegesFromAPI().stream()
                .filter(college -> college.getCountry().equalsIgnoreCase(country))
                .sorted(Comparator.comparingInt(college -> college.getName().length()))
                .collect(Collectors.toList());

        logger.info("Filtered {} colleges by country: {}", filtered.size(), country);
        return filtered;
    }

    public List<College> getCollegesByNameAndCountry(String collegeName, String country) {
        List<College> filtered = fetchCollegesFromAPI().stream()
                .filter(college -> college.getName().equalsIgnoreCase(collegeName) &&
                        college.getCountry().equalsIgnoreCase(country))
                .sorted(Comparator.comparingInt(college -> college.getName().length()))
                .collect(Collectors.toList());

        logger.info("Filtered {} colleges by name: {} and country: {}", filtered.size(), collegeName, country);
        return filtered;
    }
}
