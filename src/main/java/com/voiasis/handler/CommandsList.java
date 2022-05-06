package com.voiasis.handler;

import com.voiasis.commands.moderation.*;
import com.voiasis.commands.others.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.HashMap;

public class CommandsList {
    private static final HashMap<String, CommandBuilder> commands = new HashMap<>();

    public static void add(String name, CommandBuilder command) {
        commands.put(name, command);
    }

    public static HashMap<String, CommandBuilder> getCommands() {

        return commands;
    }

    public static void addCommandsToList() {
        CommandsList.add(new PingCommand().getName(), new PingCommand());
        CommandsList.add(new SayCommand().getName(), new SayCommand());
        CommandsList.add(new AfkCommand().getName(), new AfkCommand());
        CommandsList.add(new AvatarCommand().getName(), new AvatarCommand());
        CommandsList.add(new UptimeCommand().getName(), new UptimeCommand());
        CommandsList.add(new UserinfoCommand().getName(), new UserinfoCommand());
        CommandsList.add(new LockCommand().getName(), new LockCommand());
        CommandsList.add(new UnlockCommand().getName(), new UnlockCommand());
        CommandsList.add(new PurgeCommand().getName(), new PurgeCommand());
        CommandsList.add(new StealCommand().getName(), new StealCommand());
        CommandsList.add(new NsfwCommand().getName(), new NsfwCommand());
    }
    public static void registerCommands(JDA api, String GUILD_ID) {
        Guild mainGuild = api.getGuildById(GUILD_ID);
        assert mainGuild != null;
        addCommandsToList();
        CommandListUpdateAction ListOfCommands = mainGuild.updateCommands();
        for (CommandBuilder command : getCommands().values()) {
            ListOfCommands.addCommands(command.commandData);
        }
        ListOfCommands.queue();
    }
}
