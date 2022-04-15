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

public class LockCommand extends CommandBuilder {
    public LockCommand() {
        setCommandData(Commands.slash("lock", "Locks a channel."));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
        if (member.hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getTextChannel().getManager().getChannel().putPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.MESSAGE_SEND).queue();
            embed.addField("Lock Updated", "Channel has been locked.", false);
            event.replyEmbeds(embed.build()).setEphemeral(false).queue();
        } else {
            embed.addField("Error!", "You do not have Manage Channel permission!", false);
            event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}
