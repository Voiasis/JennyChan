package com.voiasis.handler;


import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class CommandBuilder {
    public SlashCommandData commandData;

    public void setCommandData(SlashCommandData commandData) {
        this.commandData = commandData;
    }

    public String getName() {
        return commandData.getName();
    }

    public String getDescription() {
        return commandData.getDescription();
    }

    public SlashCommandData getCommandData() {
        return commandData;
    }

    public abstract void executeCommand(SlashCommandInteractionEvent event);
}