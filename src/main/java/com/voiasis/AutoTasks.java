package com.voiasis;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoTasks extends ListenerAdapter {

    public String prefix = "!"; //bot prefix

    @Override
    public void onMessageReceived(MessageReceivedEvent event) { 
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getChannel().getId().equals("927712748488507433")) {
        
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
        }


        if (event.getChannel().getId().equals("876521430555168768")) {
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
        }

        String msgc = event.getMessage().getContentRaw();
        String msgl = msgc.toLowerCase();
        String msg = " " + msgl + " ";

        //custom chat filters
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
            }
        }

        if (msg.contains(" r ")) {
            if (event.getAuthor().getId().equals("472899069136601099")) {
                //
            } else {
                event.getMessage().delete().queue();
            }
        }

        if (msgl.contains("nigg")) {
            event.getMessage().delete().queue();
            event.getMessage().getMember().timeoutFor(6942, TimeUnit.MINUTES).queue();
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " has been timed out for saying the nword.").queue();
        }

        if (msgl.contains("retard")) {
            event.getMessage().delete().queue();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        final List<TextChannel> dontDoThis = event.getGuild().getTextChannelsByName("joins-leaves", true);

        final TextChannel pleaseDontDoThisAtAll = dontDoThis.get(0);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);

        embed.addField("Welcome!", event.getMember().getUser().getAsTag() + " has joined the server.", false);
        
        pleaseDontDoThisAtAll.sendMessageEmbeds(embed.build()).queue();
        embed.clear();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        final List<TextChannel> dontDoThis = event.getGuild().getTextChannelsByName("joins-leaves", true);

        final TextChannel pleaseDontDoThisAtAll = dontDoThis.get(0);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);

        embed.addField("Goodbye!", event.getMember().getUser().getAsTag() + " has left the server.", false);
        
        pleaseDontDoThisAtAll.sendMessageEmbeds(embed.build()).queue();
        embed.clear();
    }

    
}