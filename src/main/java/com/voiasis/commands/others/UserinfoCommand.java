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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UserinfoCommand extends CommandBuilder {
    public UserinfoCommand() {
        setCommandData(Commands.slash("userinfo", "Shows users info.").addOption(OptionType.BOOLEAN, "hidden", "Have the command show as hidden?", true).addOption(OptionType.USER, "user", "Set a user you want info on.", false));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
        LocalDateTime now = LocalDateTime.now();
        OptionMapping useropt = event.getOption("user");
        String user = useropt == null ? null : useropt.getAsString();
        if (!user.isBlank()) {
            Member mentioned = event.getOption("user").getAsMember();
            String jsMonth = Integer.toString(mentioned.getTimeJoined().getMonth().getValue());
            String jsDay = Integer.toString(mentioned.getTimeJoined().getDayOfMonth());
            String jsYear = Integer.toString(mentioned.getTimeJoined().getYear());
            String joinedServer = jsMonth + "/" + jsDay + "/" + jsYear;
            LocalDateTime jsTime = mentioned.getTimeJoined().toLocalDateTime();
            long jsDiffL = ChronoUnit.DAYS.between(jsTime, now);
            String jsDiffS = Long.toString(jsDiffL);
            String jdMonth = Integer.toString(mentioned.getTimeCreated().getMonth().getValue());
            String jdDay = Integer.toString(mentioned.getTimeCreated().getDayOfMonth());
            String jdYear = Integer.toString(mentioned.getTimeCreated().getYear());
            String joinedDiscord = jdMonth + "/" + jdDay + "/" + jdYear;
            LocalDateTime jdTime = mentioned.getTimeCreated().toLocalDateTime();
            long jdDiffL = ChronoUnit.DAYS.between(jdTime, now);
            String jdDiffS = Long.toString(jdDiffL);
            embed.addField(mentioned.getUser().getAsTag(), mentioned.getAsMention(), false);
            embed.addField("User ID", mentioned.getId(), false);
            embed.addField("Joined Discord", joinedDiscord + " (" + jdDiffS + " days ago)", false);
            embed.addField("Joined Server", joinedServer + " (" + jsDiffS + " days ago)", false);
            embed.setThumbnail(mentioned.getUser().getAvatarUrl());
            if (event.getOption("hidden").getAsBoolean()) {
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } else {
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            }
        } else {
            String jsMonth = Integer.toString(member.getTimeJoined().getMonth().getValue());
            String jsDay = Integer.toString(member.getTimeJoined().getDayOfMonth());
            String jsYear = Integer.toString(member.getTimeJoined().getYear());
            String joinedServer = jsMonth + "/" + jsDay + "/" + jsYear;
            LocalDateTime jsTime = member.getTimeJoined().toLocalDateTime();
            long jsDiffL = ChronoUnit.DAYS.between(jsTime, now);
            String jsDiffS = Long.toString(jsDiffL);
            String jdMonth = Integer.toString(event.getMember().getUser().getTimeCreated().getMonth().getValue());
            String jdDay = Integer.toString(event.getMember().getUser().getTimeCreated().getDayOfMonth());
            String jdYear = Integer.toString(event.getMember().getUser().getTimeCreated().getYear());
            String joinedDiscord = jdMonth + "/" + jdDay + "/" + jdYear;
            LocalDateTime jdTime = event.getMember().getUser().getTimeCreated().toLocalDateTime();
            long jdDiffL = ChronoUnit.DAYS.between(jdTime, now);
            String jdDiffS = Long.toString(jdDiffL);
            embed.addField(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAsMention(), false);
            embed.addField("User ID", event.getMember().getUser().getId(), false);
            embed.addField("Joined Discord", joinedDiscord + " (" + jdDiffS + " days ago)", false);
            embed.addField("Joined Server", joinedServer + " (" + jsDiffS + " days ago)", false);
            embed.setThumbnail(event.getMember().getUser().getAvatarUrl());
            if (event.getOption("hidden").getAsBoolean()) {
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } else {
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            }
        }
    }
}
