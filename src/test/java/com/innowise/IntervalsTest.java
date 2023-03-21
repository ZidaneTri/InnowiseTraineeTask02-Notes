package com.innowise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalsTest {

    @Test
    void testintervalConstruction() {
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
    void testintervalIndentification() {
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
}