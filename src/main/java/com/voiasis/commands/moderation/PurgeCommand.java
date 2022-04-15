package com.voiasis.commands.moderation;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PurgeCommand extends CommandBuilder {
    public PurgeCommand() {
        setCommandData(Commands.slash("purge", "Deletes given amount of messages in the channel.").addOption(OptionType.INTEGER, "amount", "Enter an amount of messages to delete.", true));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
        if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
            List<Message> messages = event.getChannel().getHistory().retrievePast(event.getOption("amount").getAsInt()).complete();
            try {
                event.getTextChannel().deleteMessages(messages).queue();
                embed.addField("Messages Deleted", event.getOption("amount").getAsInt() + " messages have been deleted.", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } catch (IllegalArgumentException ex) {
                embed.addField("Error!", "Must provide at least 2 or at most 100 messages to be deleted!", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        } else {
            embed.addField("Error!", "You do not have manage messages permission!", false);
            event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}
