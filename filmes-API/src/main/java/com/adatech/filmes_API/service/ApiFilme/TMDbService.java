package com.adatech.filmes_API.service.ApiFilme;

import com.adatech.filmes_API.dto.response.ApiFilmeResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TMDbService {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String format;

    public TMDbService(RestTemplate restTemplate,
                       @Value("${tmdb.api.key}") String apiKey,
                       @Value("${service.endereco.host}") String baseUrl,
                       @Value("${service.endereco.format}") String format) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.format = format;
    }

    public ApiFilmeResponseDTO obterDetalhesFilme(String title) {
        String url = String.format("%s/search/movie?query=%s&api_key=%s&format=%s", baseUrl, title, apiKey, format);
        TMDbResponse response = restTemplate.getForObject(url, TMDbResponse.class);
        if (response != null && !response.getResults().isEmpty()) {
            TMDbResponse.Result result = response.getResults().get(0);
            return new ApiFilmeResponseDTO(
                    result.getTitle(),
                    result.getRelease_date(),
                    result.getOverview(),
                    result.getRuntime(),
                    result.getPopularity(),
                    result.getOriginal_language()
            );
        }
        return null;
    }

    private static class TMDbResponse {
        private List<Result> results;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        private static class Result {
            private String title;
            private String release_date;
            private String overview;
            private int runtime;
            //private List<ApiFilmeResponseDTO.Genre> genres;
            private double popularity;
            private String original_language;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public String getOverview() {
                return overview;
            }

            public void setOverview(String overview) {
                this.overview = overview;
            }

            public int getRuntime() {
                return runtime;
            }

            public void setRuntime(int runtime) {
                this.runtime = runtime;
            }

//            public List<ApiFilmeResponseDTO.Genre> getGenres() {
//                return genres;
//            }
//
//            public void setGenres(List<ApiFilmeResponseDTO.Genre> genres) {
//                this.genres = genres;
//            }

            public double getPopularity() {
                return popularity;
            }

            public void setPopularity(double popularity) {
                this.popularity = popularity;
            }

            public String getOriginal_language() {
                return original_language;
            }

            public void setOriginal_language(String original_language) {
                this.original_language = original_language;
            }
        }
    }
}