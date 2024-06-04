package uwu.foxie.and.pawiu.java.senko_bot.command;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class AbstractSlashCommand extends AbstractCommand {
  public abstract void execute(SlashCommandInteraction data);
  
  public abstract OptionData[] getOptions();
  public abstract String getDescription();
}
