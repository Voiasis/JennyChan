package com.voiasis.commands.moderation;

import com.voiasis.handler.CommandBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StealCommand extends CommandBuilder {
    public StealCommand() {
        setCommandData(Commands.slash("steal", "Creates a new emoji from emoji or image URL.").addOption(OptionType.STRING, "name", "Name of the emoji.", true).addOption(OptionType.STRING, "link", "Emoji image link.", true));
    }
    @Override
    public void executeCommand(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
    }
}
