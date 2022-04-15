package com.voiasis.commands.others;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class AfkCommand extends CommandBuilder {
    public AfkCommand() {
        setCommandData(Commands.slash("afk", "Sets your AFK status.").addOption(OptionType.STRING, "message", "AFK status message.", false));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        try {
            String afkread = Files.readString(Path.of("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt"));
            File afktxt = new File("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt");
            afktxt.delete();
            embed.addField("AFK Disabled", "Removed afk status.", false);
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
        } catch(Exception ex) {
            try {
                File afktxt = new File("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt");
                afktxt.createNewFile();
                OptionMapping msgopt = event.getOption("message");
                String msg = msgopt == null ? null : msgopt.getAsString();
                if (!msg.isBlank()) {
                    FileWriter afkfile = new FileWriter("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt");
                    afkfile.write(msg);
                    afkfile.close();
                    embed.addField("AFK Enabled", "Added afk status of \"" + msg + "\".", false);
                    event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                } else {
                    embed.addField("AFK enabled", "Added afk status.", false);
                    event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                }
            } catch(Exception e) {
            }
        }
    }
}
