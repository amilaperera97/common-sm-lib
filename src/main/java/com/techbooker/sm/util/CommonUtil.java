package com.techbooker.sm.util;

import com.sun.codemodel.JCodeModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {
    private static final Random RANDOM = new Random();

    public static boolean isLengthEqualValid(String inputString, int length) {
        return (inputString.length() == length);
    }

    public static boolean hasEmptySpace(String string) {
        Pattern whitespace = Pattern.compile("\\s");
        Matcher matcher = whitespace.matcher(string);
        return matcher.find();
    }

    public static boolean haveSpecialCharacters(String input) {

        Pattern special = Pattern.compile("[^A-Za-z0-9]+");
        Matcher hasSpecial = special.matcher(input);
        return (hasSpecial.find());
    }

    public static boolean isTraceIdFormat(String inputString) {
        String head = inputString.substring(0, 3);
        String tail = inputString.substring(3, 15);
        return isCharacters(head) && isNumeric(tail);
    }

    public static boolean isCharacters(String input) {
        return input.matches("^[a-zA-Z]*$");
    }

    public static boolean isNumeric(String stringNumber) {
        if (stringNumber == null) {
            return false;
        }
        try {
            Double.parseDouble(stringNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static long getTimeTaken(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public static String traceIDGenerator(String serviceName) {
        long randomNum = generateRandom();
        return serviceName.toUpperCase().substring(0, 3) + randomNum;
    }

    private static long generateRandom() {
        int length = 12;
        char[] digits = new char[length];
        digits[0] = (char) (RANDOM.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (RANDOM.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static long uuid() {
        LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
        Duration duration = Duration.between(start, LocalDateTime.now());
        long seconds = duration.getSeconds();
        long nanos = duration.getNano();
        long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
        long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
        long version = 1 << 12;
        return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
    }

    public static void convertJsonToPojoClass(String json, String packageName, String javaClassName, File outputPojoDirectory) {
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        try {
            JCodeModel jcodeModel = new JCodeModel();
            mapper.generate(jcodeModel, javaClassName, packageName, json);
            jcodeModel.build(outputPojoDirectory);
        } catch (IOException ioe) {
            log.error("Encountered issue while converting to pojo: {}", ioe.getMessage());
        }
    }
}
