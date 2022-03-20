package main.java;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);

        //commands list page 1
        if (args[0].equalsIgnoreCase(prefix + "commands")) {
            embed.setTitle("Commands. Page 1 of 2.", null);
            embed.setDescription("");
            
            embed.addField(prefix + "avatar [<@user>]", "Shows avatar of user.", false);
            embed.addField(prefix + "react <emoji>", "Adds reaction to replied message.", false);
            embed.addField(prefix + "say <some text>", "Sends text.", false);
            embed.addField(prefix + "reply <some text>", "Replys to replied message.", false);
            embed.addField(prefix + "edit <some text>", "Edits bot message you reply to.", false);
            embed.addField(prefix + "delete", "Deletes message you replied to.", false);
            embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
            embed.addField(prefix + "giverole <@role> <@user>", "Gives a user a role.", false);
            embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
            embed.addField(prefix + "kick <@user> [<reason>]", "Kicks a user. Reason is optional.", false);

            embed.addField("","Use \"" + prefix + "commands2\" to go to page 2.", false);
            embed.setFooter(ftr, avURL);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        } 

        //commands list page 2
        if (args[0].equalsIgnoreCase(prefix + "commands2")) {
            embed.setTitle("Commands. Page 2 of 2", null);
            embed.setDescription("");

            embed.addField(prefix + "userinfo", "Shows users info.", false);
            embed.addField(prefix + "serverinfo", "Shows server info.", false);
            embed.addField(prefix + "uptime", "Shows the bots uptime.", false);
            embed.addField(prefix + "ping", "Shows message response time.", false);
            embed.addField(prefix + "shutdown", "Shuts down bot.", false);
            embed.addField(prefix + "commands", "Lists page 1 of commands.", false);
            embed.addField(prefix + "commands2", "Lists page 2 of commands.", false);

            embed.addField("","Use \"" + prefix + "commands\" to go to page 1.", false);
            embed.setFooter(ftr, avURL);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
//Utilities:

        //about TODO
        if (args[0].equalsIgnoreCase(prefix + "about")) {
            //about the bot
        }
        //avatar
        if (args[0].equalsIgnoreCase(prefix + "avatar")) {
            if(event.getMessage().getMentionedUsers().toArray().length == 1) {
                Member member = event.getMessage().getMentionedMembers().get(0);
                embed.setTitle("Link", member.getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + member.getUser().getAsMention() + ".");
                embed.setImage(member.getUser().getAvatarUrl() + "?size=1024");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();    
            } else {
                embed.setTitle("Link", event.getAuthor().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + event.getAuthor().getAsMention() + ".");
                embed.setImage(event.getAuthor().getAvatarUrl() + "?size=1024");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //message
        if (args[0].equalsIgnoreCase(prefix + "message")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                if (event.getMessage().getContentRaw().toCharArray().length >= 32) {
                    String msg = event.getMessage().getContentRaw().substring(32);
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(msg)).queue();
                } else {
                    embed.setTitle(prefix + "message <@user> <some text>", null);
                    embed.setDescription("Sends user a message.");
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.setTitle(prefix + "message <@user> <some text>", null);
                embed.setDescription("Sends user a message.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //ping TODO
        if (args[0].equalsIgnoreCase(prefix + "ping")) {
            long gw = event.getJDA().getGatewayPing();
            String gwp = Long.toString(gw);
            embed.setTitle("Pong!", null);
            embed.setDescription("");
            embed.addField("Ping:", "...." + "ms", false);
            embed.addField("Websocket:", gwp + "ms", false);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();

            
            

            //event.getMessage().reply("Ping: ... | Websocket: " + event.getJDA().getGatewayPing() + "ms").queue();

            //Temporal m = event.getMessage().getTimeCreated();
           // long ping = event.getMessage().getTimeCreated().until(m, ChronoUnit.MILLIS);
            
            //event.getMessage().editMessage(arg0)
        }
        //react
        if (args[0].equalsIgnoreCase(prefix + "react")) {
            if (event.getMessage().getContentRaw().toCharArray().length >= 7) {
                String emoji1 = event.getMessage().getContentRaw().substring(9);
                String emoji2 = emoji1.replaceFirst(">","");
                Message msg = event.getMessage().getMessageReference().getMessage();
                msg.addReaction(emoji2).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "react <emoji>", null);
                embed.setDescription("Adds reaction to replied message.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //serverinfo TODO
        if (args[0].equalsIgnoreCase(prefix + "serverinfo")) {
            String members = Integer.toString(event.getGuild().getMemberCount());
            String month = Integer.toString(event.getGuild().getTimeCreated().getMonthValue());
            String day = Integer.toString(event.getGuild().getTimeCreated().getDayOfMonth());
            String year = Integer.toString(event.getGuild().getTimeCreated().getYear());
            String creation = month + "/" + day + "/" + year;
            String owner = event.getGuild().getOwner().getAsMention();
            String boosts = Integer.toString(event.getGuild().getBoostCount());

            embed.setTitle(event.getGuild().getName(), null);
            //embed.addField("Description", event.getGuild().getDescription(), false);
            embed.addField("Owner", owner, false);
            embed.addField("Created", creation, false);
            embed.addField("Default Channel", "<#" + event.getGuild().getDefaultChannel().getId() + ">", false);
            //embed.addField("Online Members", event.getGuild().getMemberCount(), false);
            embed.addField("Total Members", members, false);
            //embed.addField("Channels (text)", event.getGuild(), false);
            //embed.addField("Channels (voice)", event.getGuild(), false);
            //embed.addField("Roles", event.getGuild(), false);
            //embed.addField("Emojis", event.getGuild(), false);
            //embed.addField("Voice Region", event.getGuild(), false);
            //embed.addField("Ban Count", event.getGuild(), false);
            embed.addField("Boosts", boosts, false);
            embed.addField("Server ID", event.getGuild().getId(), false);

            embed.addField("Banner", "", false);
            embed.setImage(event.getGuild().getBannerUrl());

            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //shutdown
        if (args[0].equalsIgnoreCase(prefix + "shutdown")) {
            String authorID = event.getAuthor().getId();
            if (authorID != "472899069136601099") {
                System.exit(1);
            } else {
                embed.setTitle("Error", null);
                embed.setDescription("You are not the owner of the bot!");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        } 
        //uptime
        if (args[0].equalsIgnoreCase(prefix + "uptime")) {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long ut = rb.getUptime();
            long millis = ut % 1000;
            long second = (ut / 1000) % 60;
            long minute = (ut / (1000 * 60)) % 60;
            long hour = (ut / (1000 * 60 * 60)) % 24;
            String uptime = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
            embed.setTitle("JennyChan's Uptime", null);
            embed.setDescription("" + uptime);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //userinfo
        if (args[0].equalsIgnoreCase(prefix + "userinfo")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                Member member = event.getMessage().getMentionedMembers().get(0);

                embed.setAuthor(member.getUser().getAsTag(), null);
                embed.setDescription(member.getAsMention());
                embed.addField("User ID", member.getId(), false);
                embed.setThumbnail(member.getUser().getAvatarUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            } else {
                embed.setAuthor(event.getAuthor().getAsTag(), null);
                embed.setDescription(event.getAuthor().getAsMention());
                embed.addField("User ID", event.getAuthor().getId(), false);
                embed.setThumbnail(event.getAuthor().getAvatarUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
//Moderation:

        //ban TODO
        if (args[0].equalsIgnoreCase(prefix + "ban")) {
            //
        }
        //delete
        if (args[0].equalsIgnoreCase(prefix + "delete")) {
            if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 5){
                event.getChannel().deleteMessageById(event.getMessage().getMessageReference().getMessageId()).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "delete", null);
                embed.setDescription("Deletes message you reply to.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //edit
        if (args[0].equalsIgnoreCase(prefix + "edit")) {
            if (event.getMessage().getContentRaw().toCharArray().length >= 5) {
                if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 1){
                    String msgid = event.getMessage().getMessageReference().getMessageId();
                    event.getChannel().editMessageById(msgid, event.getMessage().getContentRaw().substring(6)).queue();
                    event.getMessage().delete().queue();
                }
            }
        }
        //giverole
        if (args[0].equalsIgnoreCase(prefix + "giverole")) {
            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToGive = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().addRoleToMember(member, roleToGive).queue();

                    event.getMessage().reply("Gave the role " + roleToGive.getAsMention() + " to " + member.getAsMention() + ".").queue();
                } else {
                    embed.setTitle(prefix + "giverole <@role> <@user>", null);
                    embed.setDescription("Gives a user a role.");
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.setTitle(prefix + "giverole <@role> <@user>", null);
                embed.setDescription("Gives a user a role.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //kick
        if (args[0].equalsIgnoreCase(prefix + "kick")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                if (event.getMessage().getContentRaw().toCharArray().length >= 29) {
                    //kick with reason
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    String reason = event.getMessage().getContentRaw().substring(29);
                    event.getGuild().kick(member, reason).queue();
                    embed.setTitle("User kicked", null);
                    embed.setDescription(member.getAsMention() + " has been kicked with reason \"" + reason +"\".");
                    embed.setFooter(ftr, avURL);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                } else {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    event.getGuild().kick(member, null).queue();
                    embed.setTitle("User kicked", null);
                    embed.setDescription(member.getAsMention() + " has been kicked.");
                    embed.setFooter(ftr, avURL);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.setTitle(prefix + "kick <@member>", null);
                embed.setDescription("Kicks a member.");
                embed.setFooter(ftr, avURL);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //mute TODO
        if (args[0].equalsIgnoreCase(prefix + "mute")) {
            //
        }
        //removerole
        if (args[0].equalsIgnoreCase(prefix + "removerole")) {
            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToRemove = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().removeRoleFromMember(member, roleToRemove).queue();
                    event.getMessage().reply("Removed the role " + roleToRemove.getAsMention() + " from " + member.getAsMention() + '.').queue();
                } else {
                    embed.setTitle(prefix + "removerole <@role> <@user>", null);
                    embed.setDescription("Removes a role from a user.");
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.setTitle(prefix + "removerole <@role> <@user>", null);
                embed.setDescription("Removes a role from a user.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //reply
        if (args[0].equalsIgnoreCase(prefix + "reply")) {
            if (event.getMessage().getContentRaw().toCharArray().length >= 7) {
                Message msg = event.getMessage().getMessageReference().getMessage();
                msg.reply(event.getMessage().getContentRaw().substring(7)).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "reply <some text>", null);
                embed.setDescription("Replies to replied message.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //say
        if (args[0].equalsIgnoreCase(prefix + "say")) {
            if (event.getMessage().getContentRaw().toCharArray().length >= 5) {
                event.getChannel().sendMessage(event.getMessage().getContentRaw().substring(5)).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "say <some text>", null);
                embed.setDescription("Sends inputted text.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //softban TODO
        if (args[0].equalsIgnoreCase(prefix + "softban")) {
            //
        }
        //unban TODO
        if (args[0].equalsIgnoreCase(prefix + "unban")) {
            //
        }
    } //add commands above this
}