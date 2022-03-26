package com.voiasis;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message.Interaction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommands extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
        if (event.getName().equals("ping")) {
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
        if (event.getName().equals("say")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                OptionMapping channelopt = event.getOption("channel");
            String channel = channelopt == null ? null : channelopt.getAsString();
            OptionMapping textopt = event.getOption("text");
            String text = textopt == null ? null : textopt.getAsString();
            embed.addField("Say", "Sent the text \"" + text + "\" inside the channel \"<#" + channel + ">\".", false);
            event.getGuild().getTextChannelById(channel).sendMessage(text).queue();
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                event.replyEmbeds(embed.build()).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }
    }
}
