package br.ufmg.reuso.marcelosg.reprova.validators;

import br.ufmg.reuso.marcelosg.reprova.model.SemesterGrade;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GradesValidator {

    public static Optional<List<String>> isValidSemesterGrade(SemesterGrade semesterGrade) {
        if (semesterGrade.getYear() == null || semesterGrade.getSemester() == null) {
            return Optional.of(Arrays.asList("year and semester must be valid."));
        }
        return Optional.empty();
    }
}
