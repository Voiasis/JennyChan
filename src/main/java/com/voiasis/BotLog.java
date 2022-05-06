package com.voiasis;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotLog extends ListenerAdapter {
    public static void nowOnline(JDA jda) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.GREEN);
        embed.addField("Status", "Online!", false);
        try {
            //AV OUT
            jda.awaitReady().getGuildById("902397621015040020").getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            //The Dark Knights
            jda.awaitReady().getGuildById("531540842301882379").getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            //VosQuz's Grapevine Cafè
            //jda.awaitReady().getGuildById("965338284987408394").getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
        } catch (InterruptedException e) {
            //
        }
    }


    public static void sayLog(Member member, String message, Channel channel, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.addField("\"" + message + "\"", member.getAsMention() + " used Say in channel " + channel.getAsMention() + ".", false);
        if (guild.getId().equals("902397621015040020")) { //AV OUT
            guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
            guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
            guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
        } else {
            
        }
    }
    public static void purgeLog(Member member, String amount, Channel channel, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.ORANGE);
        embed.addField(amount + " Messages Deleted.", member.getAsMention() + " used Purge in " + channel.getAsMention(), false);
        if (guild.getId().equals("902397621015040020")) { //AV OUT
            guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
            guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
            guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
        } else {
            
        }
    }
    public static void kickLog(Member member, String reason, Member kicked, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        if (reason != null) {
            embed.addField("User Kicked", member.getAsMention() + " used Kick on " + kicked.getAsMention() + " with reason \"" + reason + "\"", false);
            if (guild.getId().equals("902397621015040020")) { //AV OUT
                guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
                guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
            
            }
        } else {
            embed.addField("User Kicked", member.getAsMention() + " used Kick on " + kicked.getAsMention() + " without a reason", false);
            if (guild.getId().equals("902397621015040020")) { //AV OUT
                guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
                guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
            
            }
        }
    }
    public static void banLog(Member member, String reason, Member banned, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        if (reason != null) {
            embed.addField("User Banned", member.getAsMention() + " used Ban on " + banned.getAsMention() + " with reason \"" + reason + "\"", false);
            if (guild.getId().equals("902397621015040020")) { //AV OUT
                guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
                guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
            
            }
        } else {
            embed.addField("User Banned", member.getAsMention() + " used Ban on " + banned.getAsMention() + " without a reason", false);
            if (guild.getId().equals("902397621015040020")) { //AV OUT
                guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
                guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
            
            }
        }
    }
    public static void muteLog(Member member, String time, String timeUnit, String reason, Member muted, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        if (reason != null) {
            embed.addField("User Muted", member.getAsMention() + " used Mute on " + muted.getAsMention() + " with reason \"" + reason + "\" for " + time + " " + timeUnit, false);
            if (guild.getId().equals("902397621015040020")) { //AV OUT
                guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
                guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
            
            }
        } else {
            embed.addField("User Muted", member.getAsMention() + " used Mute on " + muted.getAsMention() + " without a reason for " + time + " " + timeUnit, false);
            if (guild.getId().equals("902397621015040020")) { //AV OUT
                guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
                guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
            
            }
        }
    }
    public static void deleteLog(Member member, String content, Channel channel, Member msgOwner, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.ORANGE);
        embed.addField("\"" + content + "\"", member.getAsMention() + " used Delete in " + channel.getAsMention() + " to delete " + msgOwner.getAsMention() + "'s message", false);
        if (guild.getId().equals("902397621015040020")) { //AV OUT
            guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
            guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
            guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
        } else {
            
        }
    }
    public static void editLog(Member member, String before, String after, Channel channel, Guild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.ORANGE);
        embed.addField("Before: " + before + " | After: " + after, member.getAsMention() + " used Edit in " + channel.getAsMention(), false);
        if (guild.getId().equals("902397621015040020")) { //AV OUT
            guild.getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("531540842301882379")) { //The Dark Knights
            guild.getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
        } else if (guild.getId().equals("965338284987408394")) { //VosQuz's Grapevine Cafè
            guild.getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
        } else {
            
        }
    }


    /*@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String guildId = event.getGuild().getId();
        String channelId = event.getChannel().getId();
        String messageId = event.getMessageId();
        //String memberId = event.getMember().getId();
        new File("E:\\Dev\\JennyChan\\settings\\cache\\guild\\" + guildId + "\\" + "channel" + "\\" + channelId + "\\" + "message").mkdirs();
        File afktxt = new File("E:\\Dev\\JennyChan\\settings\\cache\\guild\\" + guildId + "\\" + "channel" + "\\" + channelId + "\\" + "message" + "\\" + messageId + ".txt");
        try {
            afktxt.createNewFile();
            FileWriter afkfile = new FileWriter("E:\\Dev\\JennyChan\\settings\\cache\\guild\\" + guildId + "\\" + "channel" + "\\" + channelId + "\\" + "message" + "\\" + messageId + ".txt");
            //afkfile.write(memberId);
            afkfile.close();
        } catch (IOException e) {
        }
    }

    public void onMessageDeleteEvent(MessageDeleteEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.ORANGE);
        
        String guildId = event.getGuild().getId();
        String channelId = event.getChannel().getId();
        String messageId = event.getMessageId();
        String message = event.toString();

        Channel channel = event.getChannel();

        try {
            String memberId = Files.readString(Path.of("E:\\Dev\\JennyChan\\settings\\cache\\guild\\" + guildId + "\\" + "channel" + "\\" + channelId + "\\" + "message" + "\\" + messageId + ".txt"));

            Member member = event.getJDA().getGuildById(guildId).getMemberById(memberId);
            
            embed.addField("Message sent by " + member + " deleted in " + channel, message, false);
            embed.setFooter("Author ID: " + memberId + " | Message ID: " + messageId);
            //event.getChannel().sendMessageEmbeds(embed.build()).queue();
            
            if (guildId.equals("902397621015040020")) { //AV OUT
                event.getGuild().getTextChannelById("971837260049834024").sendMessageEmbeds(embed.build()).queue();
            } else if (guildId.equals("531540842301882379")) { //The Dark Knights
                event.getGuild().getTextChannelById("971856851224371230").sendMessageEmbeds(embed.build()).queue();
            } else if (guildId.equals("965338284987408394")) { //VosQuz's Grapevine Cafè
                event.getGuild().getTextChannelById("971857135040360458").sendMessageEmbeds(embed.build()).queue();
            } else {
                
            }

        } catch (IOException e) {
        }
        
        


        
    }*/
}
