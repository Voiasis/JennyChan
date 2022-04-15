package com.voiasis.commands.others;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class UptimeCommand extends CommandBuilder {
    public UptimeCommand() {
        setCommandData(Commands.slash("uptime", "Shows bots uptime."));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long ut = rb.getUptime();
        long second = (ut / 1000) % 60;
        long minute = (ut / (1000 * 60)) % 60;
        long hour = (ut / (1000 * 60 * 60)) % 24;
        long day = (ut / (1000 * 60 * 60 * 60)) % 24;
        String uptime = day + " Days, " + hour + " Hours, " + minute + " Minutes, and " + second + " Seconds. ";
        embed.addField("JennyChan's Uptime", uptime, false);
        event.replyEmbeds(embed.build()).setEphemeral(false).queue();
    }
}
