package com.eureka.kidsworld.domain.book.service;

import com.eureka.kidsworld.domain.book.dto.BookContentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final BookContentService bookContentService;

    /**
     * 사용자의 MBTI에 기반하여 추천할 도서를 반환
     *
     * @param userMbtiPreferences 사용자가 선호하는 MBTI 성향 (예: { "E": 0.8, "N": 0.7 })
     * @return 추천 도서 목록
     */
    public List<BookContentDto> recommendBooksByMbti(Map<String, Float> userMbtiPreferences) {
        List<BookContentDto> allBooks = bookContentService.getAllBooks();

        return allBooks.stream()
                .filter(book -> matchesMbtiPreferences(book.getMbtiTraits(), userMbtiPreferences))
                .collect(Collectors.toList());
    }

    /**
     * 도서의 MBTI 요소가 사용자의 MBTI 선호도와 일치하는지 확인
     *
     * @param bookMbtiTraits 도서의 MBTI 요소 (예: { "E": 0.6, "N": 0.7 })
     * @param userPreferences 사용자가 선호하는 MBTI 요소 (예: { "E": 0.8, "N": 0.7 })
     * @return 사용자의 선호와 일치하면 true
     */
    private boolean matchesMbtiPreferences(Map<String, Float> bookMbtiTraits, Map<String, Float> userPreferences) {
        float threshold = 0.5f; // 예시 기준점: 사용자 선호와 0.5 이상 일치하면 추천

        for (Map.Entry<String, Float> preference : userPreferences.entrySet()) {
            String trait = preference.getKey();
            Float userTraitValue = preference.getValue();

            // 도서가 해당 MBTI 요소를 가지고 있고, 그 강도가 사용자의 선호와 비슷하면 추천
            if (bookMbtiTraits.containsKey(trait)) {
                float bookTraitValue = bookMbtiTraits.get(trait);
                if (Math.abs(userTraitValue - bookTraitValue) < threshold) {
                    return true;
                }
            }
        }

        return false;
    }
}
