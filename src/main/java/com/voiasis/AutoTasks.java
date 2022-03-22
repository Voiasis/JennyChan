package com.voiasis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoTasks extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) { 
        if (event.getChannel().getId().equals("927712748488507433")) {
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
        }

        if (event.getChannel().getId().equals("876521430555168768")) {
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
        }

        String msgc = event.getMessage().getContentRaw();
        String msgl = msgc.toLowerCase();
        String msg = " " + msgl + " ";
        if (msg.contains(" ur ")) {
            if (event.getAuthor().getId().equals("472899069136601099")) {
                //
            } else {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " L").queueAfter(1, TimeUnit.SECONDS);
            }
        }

        if (msg.contains(" u ")) {
            if (event.getAuthor().getId().equals("472899069136601099")) {
                //
            } else {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " L").queueAfter(1, TimeUnit.SECONDS);
            }
        }

        if (msg.contains(" r ")) {
            if (event.getAuthor().getId().equals("472899069136601099")) {
                //
            } else {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " L").queueAfter(1, TimeUnit.SECONDS);
            }
        }

        if (msgc.contains("nigg")) {
            event.getMessage().delete().queue();
        }

        if (msgc.contains("retard")) {
            event.getMessage().delete().queue();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        final List<TextChannel> dontDoThis = event.getGuild().getTextChannelsByName("welcome", true);

        if (dontDoThis.isEmpty()) {
            return;
        }

        final TextChannel pleaseDontDoThisAtAll = dontDoThis.get(0);

        final String useGuildSpecificSettingsInstead = String.format("Welcome %s to %s",
                event.getMember().getUser().getAsTag(), event.getGuild().getName());

        pleaseDontDoThisAtAll.sendMessage(useGuildSpecificSettingsInstead).queue();
    }
}