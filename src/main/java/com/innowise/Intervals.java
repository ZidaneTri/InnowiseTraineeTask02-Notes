package com.innowise;

import java.util.Map;
import java.util.stream.IntStream;

public class Intervals {

    // Array, which contains chromatic scale with all semitone notes. Empty string means there are two notes occupying this place
    private static final String[] CHROMATIC_SCALE = {"C","С#","Db","D","D#","Eb","E"," ","F","F#","Gb","G","G#","Ab","A","A#","Bb","B"," "};
    //Following maps needed only for validation
    static Map<String, String> intervalMap =
            Map.ofEntries(Map.entry("m2",""),
                    Map.entry("M2",""),
                    Map.entry("m3",""),
                    Map.entry("M3",""),
                    Map.entry("P4",""),
                    Map.entry("P5",""),
                    Map.entry("m6",""),
                    Map.entry("M6",""),
                    Map.entry("m7",""),
                    Map.entry("M7",""),
                    Map.entry("P8","")
                    );

    static Map<String, String> noteMap =
            Map.ofEntries(Map.entry("Cb",""),
                    Map.entry("C",""),
                    Map.entry("C#",""),
                    Map.entry("Db",""),
                    Map.entry("D",""),
                    Map.entry("D#",""),
                    Map.entry("Eb",""),
                    Map.entry("E",""),
                    Map.entry("E#",""),
                    Map.entry("Fb",""),
                    Map.entry("F",""),
                    Map.entry("F#",""),
                    Map.entry("Gb",""),
                    Map.entry("G",""),
                    Map.entry("G#",""),
                    Map.entry("Ab",""),
                    Map.entry("A",""),
                    Map.entry("A#",""),
                    Map.entry("Bb",""),
                    Map.entry("B",""),
                    Map.entry("B#","")
            );

    static Map<String, String> extendedNoteMap =
            Map.ofEntries(Map.entry("Cbb",""),
                    Map.entry("C##",""),
                    Map.entry("Dbb",""),
                    Map.entry("D##",""),
                    Map.entry("Ebb",""),
                    Map.entry("E##",""),
                    Map.entry("Fbb",""),
                    Map.entry("F##",""),
                    Map.entry("Gbb",""),
                    Map.entry("G##",""),
                    Map.entry("Abb",""),
                    Map.entry("A##",""),
                    Map.entry("Bbb",""),
                    Map.entry("B##","")
            );


    // Algorithm logic: we trim given note of its accidental, then build major scale for trimmed note.
    // From major scale we can find end note for given interval and trimmed note.
    // If we add trimmed accidental to found end note, we can get real end note which we return.
    public static String intervalConstruction(String[] args) {
        if (!((args.length == 2)||(args.length == 3))) {
            throw new IllegalArgumentException("Illegal number of elements in input array");
        }
        if(validateInputForIntervalConstruction(args)) {
            throw new IllegalArgumentException("Illegal elements in input array");
        }

        String startNote = args[1];
        int modifier = getNoteAccidentialCount(startNote);

        // True means either perfect or major quality, false - minor
        boolean intervalQuality = args[0].chars().anyMatch(c -> (c == 'P')||(c == 'M'));
        int intervalNumber = Character.getNumericValue(args[0].charAt(1));

        boolean descendingOrder = checkIfOrderIsDescending(args);
        // If we required to go in descending order, we can invert given interval and go in ascending order
        if (descendingOrder) {
            if (!checkIsIntervalPerfect(intervalNumber)) {
                intervalQuality = !intervalQuality;
            }
            intervalNumber = 9 - intervalNumber;
        }

        // All start notes have one accidental at max, so we can safely use only note letter
        int index = getNoteIndex(startNote.substring(0,1));
        // Major array contain indexes of notes which make up major scale
        int[] major = getMajorScale(index);

        String finalNote;

        if (intervalQuality == false) {
            // Minor interval is one semitone lower than major, so final note will be at previous index of chromatic scale
            finalNote = CHROMATIC_SCALE[major[intervalNumber-1]-1];
        } else {
            finalNote = CHROMATIC_SCALE[major[intervalNumber-1]];
        }

        // To get real final note we must count its own accidental and trimmed accidental from start note
        int finalModifier = modifier + getNoteAccidentialCount(finalNote);

        // Building real final note based on modifier
        return buildNoteBasedOnModifier(finalNote.substring(0, 1),finalModifier);
    }

    // Algorithm logic: to simplify building of major scale, we trim both start and final notes if start note has accidental.
    // Then we build major scale based on start note, and compare note letters of major with letter of final note.
    // Found index of major will be interval number we seek.
    // After that we compare note at index and final note again, now including accidentals.
    // If they are equal, our interval will be major or perfect. If not - interval is minor.
    // If we add trimmed accidental to found end note, we can get real end note which we return.
    public static String intervalIdentification(String[] args) {
        if (!((args.length == 2)||(args.length == 3))) {
            throw new IllegalArgumentException("Illegal number of elements in input array");
        }
        if(validateInputForIntervalIdentification(args)) {
            throw new IllegalArgumentException("Cannot identify the interval");
        }

        String startNote = args[0];
        String finalNote = args[1];
        int modifier = getNoteAccidentialCount(startNote);
        // All start notes have one accidental at max, so trimmed start note will consist only from letter
        String trimmedStartNote = startNote.substring(0,1);
        String trimmedFinalNote;
        //If start note not modified by accidentals, we keep final note as is.
        if (modifier == 0) {
            trimmedFinalNote = finalNote;
        } else {
            //Otherwise we need to modify final note too.
            trimmedFinalNote = buildNoteBasedOnModifier(finalNote.substring(0,1),(-1*modifier)+getNoteAccidentialCount(finalNote));
        }
        int trimmedStartNoteIndex = getNoteIndex(trimmedStartNote);
        // Major array contain indexes of notes which make up major scale
        int[] major = getMajorScale(trimmedStartNoteIndex);

        boolean intervalQuality = false;
        int intervalNumber = 0;
        for(int i = 0; i < major.length; i++){
            String majorNote = CHROMATIC_SCALE[major[i]];
            // If note letter from major scale equal to note letter of final note, interval number is found
            if (majorNote.substring(0,1).equals(trimmedFinalNote.substring(0,1))){
                intervalNumber = i+1;
                // If notes equals fully, that means interval will be perfect or major quality and minor if not
                if (majorNote.equals(trimmedFinalNote)) {
                    intervalQuality = true;
                } else if(verifyIntervalInvalidity(majorNote,trimmedFinalNote,intervalNumber)) {
                    // If interval is invalid, we throw an exception
                    throw new IllegalArgumentException("Cannot identify the interval");
                }
            }
        }
        // For now we got interval for ascending order. If we need to go in descending order, we need to reverse interval
        boolean descendingOrder = checkIfOrderIsDescending(args);
        if (descendingOrder) {
            intervalNumber = 9 - intervalNumber;
            if (!checkIsIntervalPerfect(intervalNumber)) {
                intervalQuality = !intervalQuality;
            }
        }

        return buildIntervalBasedOnNumberAndQuality(intervalNumber,intervalQuality);
    }

    private static boolean validateInputForIntervalConstruction(String[] args) {
        if(!intervalMap.containsKey(args[0])) {
            return true;
        }
        if(!noteMap.containsKey(args[1])) {
            return true;
        }
        if(args.length == 3) {
            if (!(args[2].equals("asc") || args[2].equals("dsc"))) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateInputForIntervalIdentification(String[] args) {
        if(!noteMap.containsKey(args[0])) {
            return true;
        }
        if(!noteMap.containsKey(args[1]) & !extendedNoteMap.containsKey(args[1]) ) {
            return true;
        }
        if(args.length == 3) {
            if (!(args[2].equals("asc") || args[2].equals("dsc"))) {
                return true;
            }
        }
        return false;
    }

    private static int getNoteAccidentialCount(String note) {
        if (note.length() > 1) {
            if (note.charAt(1) == '#') {
                return note.substring(1).length();
            } else {
                return -1 * note.substring(1).length();
            }
        } else {
            return 0;
        }
    }

    private static boolean checkIfOrderIsDescending(String[] args) {
        if(args.length == 3) {
            if (args[2].equals("dsc")) {
                return true;
            }
        }
        return false;
    }

    private static int getNoteIndex(String note) {
        return IntStream.range(0, CHROMATIC_SCALE.length)
                .filter(i -> note.equals(CHROMATIC_SCALE[i]))
                .findFirst().getAsInt();
    }

    // Major scale structure is W W H W W W H, where W is whole step and H is half step
    // For given chromatic scale W means moving 3 notes forward and H means 2 notes forward
    private static int[] getMajorScale(int index) {
        int[] major = new int[8];
        major[0] = index;
        for (int i = 1; i < major.length; i++) {
            int term;
            if((i == 3) || (i == 7)) {
                term = 2;
            } else {
                term = 3;
            }
            // Handling the looping of chromatic scale
            if ((major[i-1] + term) > CHROMATIC_SCALE.length-1) {
                major[i] = major[i-1] + term - CHROMATIC_SCALE.length;
            } else {
                major[i] = major[i-1] + term;
            }
        }
        return major;
    }

    private static String buildNoteBasedOnModifier(String baseNote, int modifier) {
        StringBuilder noteBuilder = new StringBuilder(baseNote);
        if(modifier > 0) {
            for (int i = 0; i < modifier; i++){
                noteBuilder.append("#");
            }
        } else if (modifier < 0) {
            for (int i = 0; i < Math.abs(modifier); i++){
                noteBuilder.append("b");
            }
        }
        return noteBuilder.toString();
    }

    private static String buildIntervalBasedOnNumberAndQuality(int intervalNumber, boolean intervalQuality) {
        StringBuilder intervalBuilder = new StringBuilder();
        if (intervalQuality) {
            //Intervals with numbers 4,5,8 called Perfect, so we need to check that
            if (checkIsIntervalPerfect(intervalNumber)) {
                intervalBuilder.append("P");
            } else {
                intervalBuilder.append("M");
            }
        } else {
            intervalBuilder.append("m");
        }
        intervalBuilder.append(intervalNumber);
        return intervalBuilder.toString();
    }

    private static boolean checkIsIntervalPerfect(int intervalNumber) {
        if ((intervalNumber == 4)||(intervalNumber == 5)||(intervalNumber == 8)) {
            return true;
        } else {
            return false;
        }
    }

    // Interval can be considered invalid in two cases:
    // 1) It appears to be minor, but it's number belongs to perfect interval
    // 2) It appears to be minor, but difference between major and final notes is not equally one half step
    private static boolean verifyIntervalInvalidity(String majorNote, String finalNote, int intervalNumber){
        if(checkIsIntervalPerfect(intervalNumber)) {
            return true;
        } else if ((getNoteAccidentialCount(majorNote)-getNoteAccidentialCount(finalNote)) != 1) {
            return true;
        } else {
            return false;
        }
    }

}
