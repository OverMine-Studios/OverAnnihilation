package dev.risas.dencore.models.phase;

import lombok.Getter;

/**
 * @author Risas
 * @date 21-05-2025
 * @discord https://risas.me/discord
 */

@Getter
public enum PhaseType {
    PHASE_I("I"),
    PHASE_II("II"),
    PHASE_III("III"),
    PHASE_IV("IV"),
    PHASE_V("V"),
    PHASE_VI("VI"),
    PHASE_VII("VII");

    private final String romanNumeral;

    PhaseType(String romanNumeral) {
        this.romanNumeral = romanNumeral;
    }
}
