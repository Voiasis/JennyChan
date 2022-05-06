package com.voiasis.commands.others;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PingCommand extends CommandBuilder {
    public PingCommand() {
        setCommandData(Commands.slash("ping", "Shows message response time."));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        LocalDateTime time = LocalDateTime.now();
        String gwp = Long.toString(event.getJDA().getGatewayPing());
        embed.setTitle("Pong!", null);
        embed.setDescription("Ping: ... \nWebsocket: ...");
        event.replyEmbeds(embed.build()).queue(message -> {
            embed.clear();
            long ping = time.until(LocalDateTime.now(), ChronoUnit.MILLIS);
            embed.setTitle("Pong!", null);
            embed.setDescription("Ping: " + ping + " ms\nWebsocket: " + gwp + " ms");
            embed.setColor(Color.MAGENTA);
            message.editOriginalEmbeds(embed.build()).queue();
            embed.clear();
        });
    }
}
