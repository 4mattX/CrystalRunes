package com.matthew4man.crystalrunes.methods;

import java.util.Random;

public class RandomNameGenerator {
    private static String adjective[] = {
            "the same", "like", "beautiful", "Certain", "The original", "beautiful", "happy", "possible", "Lovely",
            "understandable", "all", "later", "important", "frequently", "Nature", "truly", "scared", "Aerial",
            "Red", "painfully", "Clean", "hard", "wonderful", "happy", "improving", "Influential", "Yellow",
            "Dear", "basically", "perfect", "Golden", "clever", "fresh", "charming", "bright", "common",
            "direct", "real", "Heard", "with effort", "Fast", "Snow White", "hurry", "optimistic", "Main",
            "Vivid", "ice cold", "Observant", "Amazing", "horizontal", "touching", "Lots of", "Ignorant", "Polite",
            "warm", "Affectionate", "normal", "plain", "bright", "Lagging", "generous", "Boss", "Hard work",
            "Clear", "professional", "permanent", "Atmospheric", "Confidante", "Just right", "relatively", "Peaceful", "friendly",
            "Huge", "Beautiful", "Daily", "Advanced", "identical", "straight", "Stable", "satisfied", "Sturdy",
            "Long time", "obedient", "famous", "Sultry", "Many", "Crowded", "intrinsic", "Tiny", "honest",
            "Friendly", "original", "Ridiculous", "qualified", "public", "Big Red", "Powerful", "Clean", "dim",
            "Bright red", "Pink", "frightening", "extra", "Beautiful", "busy", "Cold", "Enthusiastic", "Empty",
            "Desolate", "public", "Cold", "Complete", "Grass Green", "Competent", "Furious", "Heartful", "Amateur",
            "hollow", "cool", "Long term", "Natural", "Reconciliation", "legal", "Ming Jing", "Outdated", "Low",
            "Unpleasant", "Low level", "Used", "Uncertain", "Public", "hardworking", "Little", "Busy", "Daily",
            "Important", "rare", "Non-divided", "Fear of people", "Busy", "happy", "special", "Future", "great",
            "difficult", "sad", "actual", "realistic", "abundant", "same", "huge", "patiently", "superior",
            "Dear", "Nasty", "severe", "positive", "neat", "Environmentally Friendly"};

    private static String[] noun = {
            "life", "together", "No", "people", "Nowadays", "child", "In my heart", "grandmother", "eye",
            "school", "original", "grandfather", "local", "past", "thing", "after", "may", "at night",
            "inside", "Talent", "Knowledge", "story", "How many", "game", "winter", "all", "Look",
            "Grade", "later", "before", "childhood", "problem", "day", "activity", "park", "composition",
            "next to", "in the afternoon", "natural", "room", "air", "Smile", "tomorrow", "landscape", "music",
            "years", "culture", "pissed off", "opportunity", "Shadow", "the weather", "Sky", "red", "school bag",
            "this year", "car", "in the morning", "the way", "understanding", "Method", "wonderful",
            "noon", "gift", "star", "habit", "Trees", "daughter", "friendship", "night", "significance",
            "Parent", "ear", "family", "Doorway", "class", "world", "kitchen", "wind and rain", "influences",
            "New Year", "phone", "yellow", "seed", "square", "early morning", "fundamental", "home", "Smiley face",
            "water surface", "thought", "partner", "Beauty", "photo", "fruit", "rainbow", "Just now", "moonlight",
            "Mr", "Flowers", "light", "feeling", "Family", "Language", "Love", "bright", "about",
            "new Year", "corner", "frog", "the film", "behavior", "balcony", "Intently", "topic", "world",
            "power", "garden", "poet", "forest", "umbrella", "last year", "Girl", "rural", "opponent",
            "morning", "respectively", "vitality", "effect", "Ancient", "princess", "strength", "Formerly", "works",
            "space", "Night", "Description", "youth", "bread", "past", "size", "driver",
            "center", "opposite", "Heart", "Corner of mouth", "Home", "book", "snowman", "joke",
            "Cloud", "breakfast", "Right hand", "Level", "pedestrian", "paradise", "Flowers", "Talent", "Left Hand",
            "purpose", "text", "advantage", "dust", "Era", "sand", "Fiction", "Children", "Celebrity",
            "problem", "book", "Water Drop", "color", "Streetlights", "grasp", "houses", "wish", "left",
            "news", "Early", "market", "Raindrops", "drizzle", "study", "towel", "painter", "New Year's Day",
            "green beans", "ability", "starting point", "vegetables", "potato", "to sum up", "courtesy", "right", "curtain",
            "radish", "deep feeling", "Building", "dialogue", "noodles", "north", "wood", "store", "doubt",
            "as a result of", "on site", "Poetry", "bright", "Chinese cabbage", "man", "style", "Avenue", "Dreamland",
            "sisters", "Sweater", "gardener", "Both sides", "Gale", "country", "broadcast", "Regulations", "scarf",
            "opinion", "generous", "mind", "Boss", "idiom", "profession", "background", "Coat", "soybean",
            "Master", "blade", "Past", "Player", "cream", "Time and space", "atmosphere", "Excuse", "rag",
            "brush", "goat", "Party", "flip flop", "Hand palm", "manual", "next year", "surgery", "Flame",
            "confidant", "price", "Sapling", "idea", "cradle", "Compared", "Fat", "expert", "grade",
            "Sunset", "Dongfeng", "house", "Creativity", "Report", "chin", "face", "maze", "Snow Mountain",
            "friendly", "smoke", "West", "aunt", "question mark", "Annual Ring", "Resident", "Player", "cream",
            "Time and space", "atmosphere", "Excuse", "rag", "brush", "goat", "Party", "flip flop", "Hand palm",
            "manual", "next year", "surgery", "Flame", "confidant", "price", "Sapling", "idea", "cradle",
            "Compared", "Fat", "expert", "grade", "Sunset", "Dongfeng", "house", "Creativity", "Report",
            "chin", "face", "maze", "Snow Mountain", "friendly", "smoke", "West", "aunt", "question mark",
            "Annual Ring", "Resident", "Player", "cream", "Time and space", "atmosphere", "Excuse", "rag", "brush",
            "goat", "Party", "flip flop", "Hand palm", "manual", "next year", "surgery", "Flame", "confidant",
            "price", "Sapling", "idea", "cradle", "Compared", "Fat", "expert", "grade", "Sunset",
            "Dongfeng", "house", "Creativity", "Report", "chin", "face", "maze", "Snow Mountain", "friendly",
            "smoke", "West", "aunt", "question mark", "Annual Ring", "Resident"};


    // Generates random Adjective and Noun according to lists above
    public static String generateName() {
        int adjLen= adjective.length;
        int nLen= noun.length;
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        sb.append(adjective[random.nextInt(adjLen)]);
        sb.append(" ");
        sb.append(noun[random.nextInt(nLen)]);
        return sb.toString();
    }

}
