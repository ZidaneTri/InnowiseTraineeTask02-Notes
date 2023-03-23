package com.innowise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalsTest {

    @Test
    void testintervalConstructionCorrectInput() {
        assertEquals(Intervals.intervalConstruction(new String[]{"M2", "C"}), "D");
        assertEquals(Intervals.intervalConstruction(new String[]{"P5", "B"}), "F#");
        assertEquals(Intervals.intervalConstruction(new String[]{"m2", "Fb", "asc"}), "Gbb");
        assertEquals(Intervals.intervalConstruction(new String[]{"m2", "D#"}), "E");
        assertEquals(Intervals.intervalConstruction(new String[]{"m2", "Bb", "dsc"}), "A");
        assertEquals(Intervals.intervalConstruction(new String[]{"M3", "Cb", "dsc"}), "Abb");
        assertEquals(Intervals.intervalConstruction(new String[]{"P4", "G#", "dsc"}), "D#");
        assertEquals(Intervals.intervalConstruction(new String[]{"m3", "B", "dsc"}), "G#");
        assertEquals(Intervals.intervalConstruction(new String[]{"M2", "E#", "dsc"}), "D#");
        assertEquals(Intervals.intervalConstruction(new String[]{"P4", "E", "dsc"}), "B");
        assertEquals(Intervals.intervalConstruction(new String[]{"M7", "G", "asc"}), "F#");
        assertEquals(Intervals.intervalConstruction(new String[]{"P8", "Cb", "asc"}), "Cb");
    }
    @Test
    void testintervalIndentificationCorrectInput() {
        assertEquals(Intervals.intervalIdentification(new String[]{"B", "F#"}), "P5");
        assertEquals(Intervals.intervalIdentification(new String[]{"Fb", "Gbb", "asc"}), "m2");
        assertEquals(Intervals.intervalIdentification(new String[]{"G", "F#"}), "M7");
        assertEquals(Intervals.intervalIdentification(new String[]{"Bb", "A", "dsc"}), "m2");
        assertEquals(Intervals.intervalIdentification(new String[]{"Cb", "Abb", "dsc"}), "M3");
        assertEquals(Intervals.intervalIdentification(new String[]{"G#", "D#", "dsc"}), "P4");
        assertEquals(Intervals.intervalIdentification(new String[]{"E", "B", "dsc"}), "P4");
        assertEquals(Intervals.intervalIdentification(new String[]{"E#", "D#", "dsc"}), "M2");
        assertEquals(Intervals.intervalIdentification(new String[]{"B", "G#", "dsc"}), "m3");
    }

    @Test
    void testintervalConstructionIncorrectInput() {
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M19", "C"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M5", "C"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"B6", "C"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{null, "C"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M3", "C##"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M3", "B&&"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M3", "I"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M3", null}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"m2", "Fb", "ascfgfgfg"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M3", "C##","asc", "M3"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalConstruction(new String[]{"M3"}));
    }
    @Test
    void testintervalIndentificationIncorrectInput() {
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"B&&", "F#"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"C##", "F#"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"H", "F#"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{null, "F#"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"B", "F#$"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"B", "fdfd"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"B", null}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"Fb", "Gbb", "gfgfg"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"B"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"Fb", "Gbb", "asc", "string"}));
        assertThrows(IllegalArgumentException.class,() -> Intervals.intervalIdentification(new String[]{"B", "F##"}));
    }
}