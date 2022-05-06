package com.voiasis.commands.moderation;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class NsfwCommand extends CommandBuilder {
    public NsfwCommand() {
        setCommandData(Commands.slash("nsfw", "Toggles channel NSFW."));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
        if (member.hasPermission(Permission.MANAGE_CHANNEL)) {
            if (event.getTextChannel().isNSFW()) {
                event.getTextChannel().getManager().setNSFW(false).queue();
                embed.addField("NSFW Updated", "NSFW has been disabled.", false);
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            } else {
                event.getTextChannel().getManager().setNSFW(true).queue();
                embed.addField("NSFW Updated", "NSFW has been enabled.", false);
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            }
        } else {
            embed.addField("Error!", "You do not have Manage Channel permission!", false);
            event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}
