package uwu.foxie.and.pawiu.java.senko_bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import javax.security.auth.login.LoginException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    private static final long BOT_PERMISSION_BITMASK = Permission.getRaw(
        // View channel and nickname change
        Permission.VIEW_CHANNEL,
        Permission.NICKNAME_CHANGE,
        
        // Threads creations
        Permission.CREATE_PUBLIC_THREADS,
        Permission.CREATE_PRIVATE_THREADS,
        
        // Send messages
        Permission.MESSAGE_SEND,
        Permission.MESSAGE_SEND_IN_THREADS,
        Permission.MESSAGE_EMBED_LINKS,
        Permission.MESSAGE_ATTACH_FILES,
        
        // Read messages
        Permission.MESSAGE_HISTORY,
        
        // Create polls
        Permission.MESSAGE_SEND_POLLS
    );
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws LoginException {
        // get token from .env file
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        String clientID = dotenv.get("CLIENT_ID");
        
        if (token == null || token.isEmpty()) {
            LOGGER.fatal("Token not found in .env file");
            System.exit(1);
        }
        
        if (clientID == null || clientID.isEmpty()) {
            LOGGER.warn("Client ID not found in .env file. Invite link will not be generated");
            System.exit(1);
        }
        
        if (clientID != null)
            LOGGER.info("Bot invite link: https://discord.com/oauth2/authorize?client_id=" + clientID + "&permission=" + Main.BOT_PERMISSION_BITMASK + "&scope=bot+applications.commands");

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.enableIntents(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.MESSAGE_CONTENT
        );
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.addEventListeners(new ListenerAdapter() {
            @Override
            public void onMessageReceived(MessageReceivedEvent event) {
                Message msg = event.getMessage();
                if (msg.getAuthor().isBot())
                    return;
                LOGGER.info("Received message: " + msg.getContentDisplay());
            }
            
            @Override
            public void onReady(ReadyEvent event) {
                LOGGER.info("Bot is ready.");
            }
            
            @Override
            public void onGuildJoin(GuildJoinEvent event) {
                Guild guild = event.getGuild();
                guild.updateCommands().addCommands(
                    Commands.slash("ping", "Responds with bot's ping to Discord gateway and pong UwU")
                ).queue();
            }
            
            @Override
            public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
                if (!event.getName().equals("ping"))
                    return;
              
                OffsetDateTime sendTime = event.getInteraction().getTimeCreated();
                OffsetDateTime eventCreationTime = event.getTimeCreated();
                float ping = sendTime.until(eventCreationTime, ChronoUnit.MICROS) / 1000;
                
                StringBuilder reply = new StringBuilder();
                reply.append("Hello Wold UwU\n");
                reply.append("Ping from bot to Discord is " + ping + "\n");
                event.reply(reply.toString()).queue();
            }
         });
        builder.build();
    }
}
