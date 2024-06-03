package uwu.foxie.and.pawiu.java.senko_bot.handlers;

import java.util.Arrays;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class TxtCommandsHandler {
    
    public static void loadCommands() {

    }

    public static void handler(Message msg) {

        String content = msg.getContentDisplay();
        User author = msg.getAuthor();

        System.out.println(content);

        String[] args = content.split(" ");

        System.out.println(Arrays.toString(args));
    }

}
