package org.example.mbti.util;

import org.example.mbti.domain.entity.MbtiQuestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<MbtiQuestion> readCsv(String path) {
        List<MbtiQuestion> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();  // 헤더 스킵

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                MbtiQuestion question = MbtiQuestion.builder()
                        .questionText(values[1])
                        .optionA(values[2])
                        .optionB(values[3])
                        .mbtiTypeA(values[4])
                        .mbtiTypeB(values[5])
                        .imageFile(values[6])
                        .build();
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
