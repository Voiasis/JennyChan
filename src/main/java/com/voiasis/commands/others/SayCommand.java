package com.voiasis.commands.others;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class SayCommand extends CommandBuilder {
    public SayCommand() {
        setCommandData(Commands.slash("say", "Sends text.").addOption(OptionType.CHANNEL, "channel", "Choose the channel you'd like to send the message in.", true).addOption(OptionType.STRING, "text", "Type out what you want the bot to say.", true));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
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
            event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}
