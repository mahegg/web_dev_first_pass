package com.mahegg.first_pass.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Component;
import org.passay.PasswordGenerator;

@Component
public class PasswordGen {

    public String genpassword() {
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        alphabets.setNumberOfCharacters(4);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        alphabets.setNumberOfCharacters(4);
        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);
        special.setNumberOfCharacters(2);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(10, alphabets, digits, special);
        return password;
    }

}
