package de.arkadi.persistence.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static de.arkadi.persistence.model.Language.*;


@Converter
public class LanguageConverter implements AttributeConverter<Language, String> {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public String convertToDatabaseColumn(Language language) {
        switch (language) {
            case ENGLISH:
                return "EN";
            case FINISH:
                return "FI";
            case FRENCH:
                return "FR";
            case GERMAN:
                return "DE";
            case ITALIAN:
                return "IT";
            case PORTUGUESE:
                return "PT";
            case RUSSIAN:
                return "RU";
            case SPANISH:
                return "SP";
            default:
                throw new IllegalArgumentException("Unknown" + language);
        }
    }

    @Override
    public Language convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "DE":
                return GERMAN;
            case "EN":
                return ENGLISH;
            case "FI":
                return FINISH;
            case "FR":
                return FRENCH;
            case "IT":
                return ITALIAN;
            case "PT":
                return PORTUGUESE;
            case "RU":
                return RUSSIAN;
            case "SP":
                return SPANISH;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}