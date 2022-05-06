package com.voiasis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;

public class AutoTasks extends ListenerAdapter {
    String prefix = Config.get("PREFIX");
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getChannel().equals(event.getGuild().getTextChannelById("836666384590438412"))) {
            if (event.getReactionEmote().getEmote().toString().equals("E:Lewd(836684794804568164)")) {
                event.getGuild().addRoleToMember(event.getUserId(), event.getGuild().getRoleById("870138445908029460")).queue();
            }
        }
    }
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getChannel().equals(event.getGuild().getTextChannelById("836666384590438412"))) {
            if (event.getReactionEmote().getEmote().toString().equals("E:Lewd(836684794804568164)")) {
                event.getGuild().removeRoleFromMember(event.getUserId(), event.getGuild().getRoleById("870138445908029460")).queue();
            }
        }
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) { 
        //spam filter
        List<Message> history = event.getTextChannel().getIterableHistory().complete().stream().limit(10).filter(msg -> !msg.equals(event.getMessage())).collect(Collectors.toList());
        int spam = history.stream().filter(message -> message.getAuthor().equals(event.getAuthor()) && !message.getAuthor().isBot()).filter(msg -> (
            event.getMessage().getTimeCreated().toEpochSecond() - msg.getTimeCreated().toEpochSecond()) < 10).collect(Collectors.toList()).size();
        if (spam > 2 && !event.getGuild().getOwner().equals(event.getMember()) && !PermissionUtil.checkPermission(event.getMember(), Permission.ADMINISTRATOR)) {
            event.getTextChannel().deleteMessages(history.stream().filter(message -> message.getAuthor().equals(event.getAuthor()) && !message.getAuthor().isBot()).filter(msg ->(
                event.getMessage().getTimeCreated().toEpochSecond() - msg.getTimeCreated().toEpochSecond()) < 10).collect(Collectors.toList())).queue();
            event.getMember().timeoutFor(10, TimeUnit.SECONDS).queue();
            event.getChannel().sendMessage("Please slow down, " + event.getAuthor().getAsMention()).queue(message -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
                event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }

        String[] args = event.getMessage().getContentRaw().split(" ");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        if (event.getChannel().getId().equals("927712748488507433")) { //AV OUT
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
            event.getMessage().addReaction("approved:956621420560011354").queue();
            event.getMessage().addReaction("rejected:956621588084695120").queue();
        }
        if (event.getChannel().getId().equals("876521430555168768")) { //The Dark Knights
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
            event.getMessage().addReaction("approved:956621420560011354").queue();
            event.getMessage().addReaction("rejected:956621588084695120").queue();
        }
        if (event.getChannel().getId().equals("971643137137184768")) { //VosQuz's Grapevine Cafè
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
            event.getMessage().addReaction("approved:956621420560011354").queue();
            event.getMessage().addReaction("rejected:956621588084695120").queue();
        }


        /*try {
            String userID = event.getMessage().getMentionedMembers().get(0).getId();
            File afkfile = new File("E:\\Dev\\JennyChan\\settings\\afk\\" + userID + ".txt");
            if (afkfile.exists() && !afkfile.isDirectory()) {
                String afkread = Files.readString(Path.of("E:\\Dev\\JennyChan\\settings\\afk\\" + userID + ".txt"));
                String afk1 = afkread.replaceFirst("!", "");
                String afk2 = afk1.replaceFirst("a", "");
                String afk3 = afk2.replaceFirst("f", "");
                String afkmsg = afk3.replaceFirst("k", "");
                if (afkmsg.isEmpty()) {
                    embed.addField("User AFK", "That user is currently AFK.", false);
                    event.getMessage().replyEmbeds(embed.build()).queue();
                } else {
                    embed.addField("User AFK", "That user is currently AFK with message \"" + afkmsg + "\".", false);
                    event.getMessage().replyEmbeds(embed.build()).queue();
                }
            }
        } catch(Exception ex) {
        }
        try {
            File afktxt = new File("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getAuthor().getId() + ".txt");
            if (afktxt.exists() && !afktxt.isDirectory()) {
                afktxt.delete();
                embed.addField("AFK Disabled", "Removed afk status.", false);
                event.getMessage().replyEmbeds(embed.build()).queue();
            }
        } catch(Exception ex) {
        }*/

        try {
            if (event.getMessage().getMentionedUsers().get(0).getId().equals("472899069136601099")) {
                if (event.getAuthor().isBot()) {//
                } else if (event.getAuthor().getId().equals("472899069136601099")) {//
                } else {
                    if (event.getMessage().getContentRaw().contains("472899069136601099")) {
                        event.getMessage().reply("https://c.tenor.com/3Xzu0bKWsG0AAAAd/ping-discord.gif").queue();
                    } else {
                        event.getMessage().reply("https://c.tenor.com/kJhT6VC2tzEAAAAS/pings-off-reply-pings-off.gif").queue();
                    }
                }
            }
        } catch(Exception ex) {
        }
        String msgc = event.getMessage().getContentRaw();
        String msgl = msgc.toLowerCase();
        String msg = " " + msgl + " ";
        if (msgc.contains("<@&971630877438279710>")) {
            event.getMessage().reply("https://c.tenor.com/3Xzu0bKWsG0AAAAd/ping-discord.gif").queue();
        }
        if (msgc.contains("<@952761165577060453>")) {
            embed.addField("Bot Prefix", "My current prefix is set to " + prefix + ".\r\nOnly Voiasis can change it for now.", false);
            event.getChannel().sendMessageEmbeds(embed.build()).queue(message -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
        //custom chat filters
        if (msg.contains(" ur ")) {
            if (event.getAuthor().getId().equals("472899069136601099")) {
                //
            } else {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " L").queueAfter(1, TimeUnit.SECONDS);
            }
        }
        if (msg.contains(" urs ")) {
            if (event.getAuthor().getId().equals("472899069136601099")) {
                //
            } else {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " L").queueAfter(1, TimeUnit.SECONDS);
            }
        }
        if (msg.contains(" nft ")) {
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
    private void coolDownUser(TextChannel textChannel, User author) {
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