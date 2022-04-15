package com.voiasis.commands.others;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AvatarCommand extends CommandBuilder {
    public AvatarCommand() {
        setCommandData(Commands.slash("avatar", "Shows avatar of user.").addOption(OptionType.BOOLEAN, "hidden", "Have the command show as hidden?", true).addOption(OptionType.USER, "user", "Get avatar from user.", false));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member memberopt = event.getOption("user").getAsMember();
        OptionMapping useropt = event.getOption("user");
        String user = useropt == null ? null : useropt.getAsString();
        if (event.getOption("hidden").getAsBoolean()) {
            if(!user.isBlank()) {
                embed.setTitle("Image Link", memberopt.getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + memberopt.getUser().getAsMention() + ".");
                embed.setImage(memberopt.getUser().getAvatarUrl() + "?size=1024");
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } else {
                embed.setTitle("Image Link", event.getMember().getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + event.getMember().getUser().getAsTag() + ".");
                embed.setImage(event.getMember().getUser().getAvatarUrl() + "?size=1024");
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            }
        } else {
            if(!user.isBlank()) {
                embed.setTitle("Image Link", memberopt.getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + memberopt.getUser().getAsMention() + ".");
                embed.setImage(memberopt.getUser().getAvatarUrl() + "?size=1024");
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            } else {
                embed.setTitle("Image Link", event.getMember().getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + event.getMember().getUser().getAsTag() + ".");
                embed.setImage(event.getMember().getUser().getAvatarUrl() + "?size=1024");
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            }
        }
    }
}
