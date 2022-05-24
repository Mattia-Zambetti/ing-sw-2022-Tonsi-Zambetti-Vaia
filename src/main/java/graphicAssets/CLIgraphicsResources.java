package graphicAssets;

import model.Color;

public class CLIgraphicsResources {

    public static class ColorCLIgraphicsResources {

        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[90m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_LIGHT_GREY = "\u001B[37m";
        public static final String ANSI_WHITE = "\u001B[97m";

        public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
        public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        public static final String ANSI_LIGHT_CYAN_BACKGROUND = "\u001B[106m";

        public static final String TEXT_COLOR = ANSI_WHITE;

        public static String getTextColor(Color c) {
            return switch (c) {
                case RED -> ANSI_RED;
                case YELLOW -> ANSI_YELLOW;
                case GREEN -> ANSI_GREEN;
                case BLUE -> ANSI_BLUE;
                case PINK -> ANSI_PURPLE;
            };
        }



    }
    public static String getStringColor(String normalString, String color){
        StringBuilder res=new StringBuilder(color);
        res.append(normalString);
        res.append(ColorCLIgraphicsResources.TEXT_COLOR);
        return res.toString();
    }

}
