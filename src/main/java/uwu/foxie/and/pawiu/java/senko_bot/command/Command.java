package uwu.foxie.and.pawiu.java.senko_bot.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
  String name();
}
