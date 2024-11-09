package Assignment;

import javax.swing.JOptionPane;

public class EndingPage {
    // ANSI escape codes for color
    private static final String ANSI_RESET = "\u001B[0m"; // Reset color
    private static final String ANSI_RED = "\u001B[31m"; // Red color

    // Method to colorize the string with red
    private static String colorizeRed(String str) {
        return ANSI_RED + str + ANSI_RESET;
    }
    
    public void ending() {
        // ASCII art
        String ending =
            "              ,n888888n,\n" +
            "             .8888888888b\n" +
            "             888888888888nd8P~''8g,\n" +
            "             88888888888888   _  `'~\\.  .n.\n" +
            "             `Y888888888888. / _  |~\\\\ (8\"8b\n" +
            "            ,nnn.. 8888888b.  |  \\ \\m\\|8888P\n" +
            "          ,d8888888888888888b. \\8b|.\\P~ ~P8~\n" +
            "          888888888888888P~~_~  `8B_|      |\n" +
            "          ~888888888~'8'   d8.    ~      _/\n" +
            "           ~Y8888P'   ~\\ | |~|~b,__ __--~\n" +
            "       --~~\\   ,d8888888b.\\`\\_/ __/~\n" +
            "            \\_ d88888888888b\\_-~8888888bn.\n" +
            "              \\8888P   \"Y888888888888\"888888bn.\n" +
            "           /~'\\_\"__)      \"d88888888P,-~~-~888\n" +
            "          /  / )   ~\\     ,888888/~' /  / / 8'\n" +
            "       .-(  / / / |) )-----------(/ ~  / /  |---.\n" +
            "______ | (   '    /_/ Mickey Mouse (__/     /   |_______\n" +
            "\\      |   (_(_ ( /~ For President  \\___/_/'    |      /\n" +
            " \\     |  Thanks to our Amazing Reward System ! |     /\n" +
            " /     (________________________________________)     \\\n" +
            "/__________)     __--|~mb  ,g8888b.         (__________\\\n" +
            "               _/    8888b(.8P\"~'~---__\n" +
            "              /       ~~~| / ,/~~~~--, `\\\n" +
            "             (       ~\\,_) (/         ~-_`\\\n" +
            "              \\  -__---~._ \\             ~\\\\\n" +
            "              (           )\\\\              ))\n" +
            "              `\\          )  \"-_           `|\n" +
            "                \\__    __/      ~-__   __--~\n" +
            "                   ~~\"~             ~~~";
    
        // Rainbowify the ASCII art with red color
        String redEnding = colorizeRed(ending);
        // Print red ASCII art
        System.out.println(redEnding);
        // Display JOptionPane
        JOptionPane.showMessageDialog(null, "Thank you!", "Message", JOptionPane.INFORMATION_MESSAGE);
        // Exit the program after the message dialog is closed
        System.exit(0);
    }
}
