package uwu.foxie.and.pawiu.java.senko_bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import javax.security.auth.login.LoginException;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) throws LoginException {
        // get token from .env file
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        if (token == null || token.isEmpty()) {
            System.err.println("Token not found in .env file.");
            System.exit(1);
        }

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(new ReadyListener());
        builder.build();
    }

    public static class MessageListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            Message msg = event.getMessage();
            if (msg.getAuthor().isBot()) {
                return;
            }
            System.out.println("Received message: " + msg.getContentDisplay());
        }
    }

    public static class ReadyListener extends ListenerAdapter {
        @Override
        public void onReady(ReadyEvent event) {
            System.out.println("Bot is ready.");
        }
    }
}
