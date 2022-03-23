package com.voiasis;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class BotCommands extends ListenerAdapter {
    
    public String prefix = "!"; //bot prefix
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setFooter("Command executed by " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());

        final Member member = event.getMember();
        final User author = event.getAuthor();
        final MessageChannel channel = event.getChannel();
        final Member self = event.getGuild().getSelfMember();

        LocalDateTime now = LocalDateTime.now();;

        if (author.isBot()) {
            //
        } else {
      
//Utilities:

        //reaction page system test
        if (args[0].equalsIgnoreCase(prefix + "page")) {
            event.getChannel().sendMessage("test").setActionRow(Button.primary("page:2", Emoji.fromMarkdown("▶️"))).queue();
            
        }
        //about TODO
        if (args[0].equalsIgnoreCase(prefix + "about")) {
            //about the bot. why have i still not added anything. pure laziness maybe?
        }
        //avatar
        if (args[0].equalsIgnoreCase(prefix + "avatar")) {
            if(event.getMessage().getMentionedUsers().toArray().length == 1) {
                Member mentioned = event.getMessage().getMentionedMembers().get(0);
                embed.setTitle("Image Link", mentioned.getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + mentioned.getUser().getAsMention() + ".");
                embed.setImage(mentioned.getUser().getAvatarUrl() + "?size=1024");
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();    
            } else {
                embed.setTitle("Image Link", author.getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + author.getAsMention() + ".");
                embed.setImage(author.getAvatarUrl() + "?size=1024");
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //message TODO
        if (args[0].equalsIgnoreCase(prefix + "message")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                if (event.getMessage().getContentRaw().toCharArray().length >= 32) {
                    String msg = event.getMessage().getContentRaw().substring(32);
                    member.getUser().openPrivateChannel().queue((ch) -> {
                        ch.sendMessage(msg).queue();
                    });
                } else {
                    embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //ping
        if (args[0].equalsIgnoreCase(prefix + "ping")) {
            long gw = event.getJDA().getGatewayPing();
            String gwp = Long.toString(gw);
            embed.setTitle("Pong!", null);
            embed.setDescription("Ping: ... \nWebsocket: ...");
            channel.sendMessageEmbeds(embed.build()).queue(message -> {
                long ping = event.getMessage().getTimeCreated().until(message.getTimeCreated(), ChronoUnit.MILLIS);
                embed.setTitle("Pong!", null);
                embed.setDescription("Ping: " + ping + " ms\nWebsocket: " + gwp + " ms");
                embed.setColor(Color.MAGENTA);
                embed.setFooter("Command executed by " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());
                message.editMessageEmbeds(embed.build()).queue();
            });
            embed.clear();
        }
        //react
        if (args[0].equalsIgnoreCase(prefix + "react")) {
            try {
                if (event.getMessage().getContentRaw().toCharArray().length >= 7) {
                    String content = event.getMessage().getContentRaw().substring(7);
                    if (content.startsWith("<")) {
                        String emoji1 = event.getMessage().getContentRaw().substring(9);
                        String emoji2 = emoji1.replaceFirst(">","");
                        Message msg = event.getMessage().getMessageReference().getMessage();
                        msg.addReaction(emoji2).queue();
                        event.getMessage().delete().queue();
                    } else {
                        embed.addField("Error!", "That is not an emoji!", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "react <emoji>", "Adds reaction to replied message.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } catch(Exception ex) {
                embed.addField("Error!", "You must reply to a message!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
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
            embed.setImage(event.getGuild().getIconUrl() + "?size=1024");
            //embed.addField("Description", event.getGuild().getDescription(), false);
            embed.addField("Owner", owner, false);
            embed.addField("Created", creation, false);
            embed.addField("Default Channel", "<#" + event.getGuild().getDefaultChannel().getId() + ">", false);
            //embed.addField("Online Members", event.getGuild().getMemberCount(), false);
            embed.addField("Total Members", members, false);
            //embed.addField("Channels (text)", event.getGuild(), false);
            //embed.addField("Channels (voice)", event.getGuild(), false);
            //embed.addField("Roles", event.getGuild(), false);
            //embed.addField("Emojis", , false);
            //embed.addField("Voice Region", , false);
            //embed.addField("Ban Count", event., false);
            embed.addField("Boosts", boosts, false);
            embed.addField("Server ID", event.getGuild().getId(), false);

            embed.addField("Banner", "", false);
            embed.setImage(event.getGuild().getBannerUrl());

            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //shutdown
        if (args[0].equalsIgnoreCase(prefix + "shutdown")) {
            if (member.hasPermission(Permission.ADMINISTRATOR)) {
                System.exit(1);
            } else {
                embed.addField("Error", "You do not have permission to do that!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        } 
        //uptime
        if (args[0].equalsIgnoreCase(prefix + "uptime")) {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long ut = rb.getUptime();
            long second = (ut / 1000) % 60;
            long minute = (ut / (1000 * 60)) % 60;
            long hour = (ut / (1000 * 60 * 60)) % 24;
            long day = (ut / (1000 * 60 * 60 * 60)) % 24;
            String uptime = day + " Days, " + hour + " Hours, " + minute + " Minutes, and " + second + " Seconds. ";
            embed.addField("JennyChan's Uptime", uptime, false);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //userinfo
        if (args[0].equalsIgnoreCase(prefix + "userinfo")) {

            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                Member mentioned = event.getMessage().getMentionedMembers().get(0);

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
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            } else {
                String jsMonth = Integer.toString(member.getTimeJoined().getMonth().getValue());
                String jsDay = Integer.toString(member.getTimeJoined().getDayOfMonth());
                String jsYear = Integer.toString(member.getTimeJoined().getYear());
                String joinedServer = jsMonth + "/" + jsDay + "/" + jsYear;

                LocalDateTime jsTime = member.getTimeJoined().toLocalDateTime();
                long jsDiffL = ChronoUnit.DAYS.between(jsTime, now);
                String jsDiffS = Long.toString(jsDiffL);

                String jdMonth = Integer.toString(author.getTimeCreated().getMonth().getValue());
                String jdDay = Integer.toString(author.getTimeCreated().getDayOfMonth());
                String jdYear = Integer.toString(author.getTimeCreated().getYear());
                String joinedDiscord = jdMonth + "/" + jdDay + "/" + jdYear;

                LocalDateTime jdTime = author.getTimeCreated().toLocalDateTime();
                long jdDiffL = ChronoUnit.DAYS.between(jdTime, now);
                String jdDiffS = Long.toString(jdDiffL);

                embed.addField(author.getAsTag(), author.getAsMention(), false);
                embed.addField("User ID", author.getId(), false);
                embed.addField("Joined Discord", joinedDiscord + " (" + jdDiffS + " days ago)", false);
                embed.addField("Joined Server", joinedServer + " (" + jsDiffS + " days ago)", false);
                embed.setThumbnail(author.getAvatarUrl());
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
//Moderation:

        //lock


        //unlock


        //steal TODO
        if (args[0].equalsIgnoreCase(prefix + "steal")) {
            if (member.hasPermission(Permission.MANAGE_EMOTES_AND_STICKERS)) {
                //
            }
        } else {
            //
        }

        //ban TODO
        if (args[0].equalsIgnoreCase(prefix + "ban")) {
            if (member.hasPermission(Permission.BAN_MEMBERS)) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    if (event.getMessage().getContentRaw().toCharArray().length >= 28) {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        String reason = event.getMessage().getContentRaw().substring(28);
                        event.getGuild().ban(member, 0, reason).queue();
                        embed.addField("User Banned", mentioned.getAsMention() + " has been banned with reason \"" + reason +"\".", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    } else {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        
                        embed.addField("User Banned", mentioned.getAsMention() + " has been banned.", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        event.getGuild().ban(mentioned, 0).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "ban <@user> [<reason>]", "Bans a user.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have ban permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //delete
        if (args[0].equalsIgnoreCase(prefix + "delete")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                try {
                    if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 5){
                        channel.deleteMessageById(event.getMessage().getMessageReference().getMessageId()).queue();
                        event.getMessage().delete().queue();
                    }
                } catch(Exception ex) {
                    embed.addField(prefix + "delete", "Deletes the message you reply to.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //edit
        if (args[0].equalsIgnoreCase(prefix + "edit")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                try {
                    if (event.getMessage().getContentRaw().substring(6) == null) {
                    }
                    try {
                        if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 1) {
                            
                            if (event.getMessage().getReferencedMessage().getAuthor().getId().equalsIgnoreCase("952761165577060453")) { 
                                String msgid = event.getMessage().getMessageReference().getMessageId();
                                channel.editMessageById(msgid, event.getMessage().getContentRaw().substring(6)).queue();
                                event.getMessage().delete().queue();
                            } else {
                                embed.addField("Error!", "I can only edit my own messages!", false);
                                channel.sendMessageEmbeds(embed.build()).queue();
                                embed.clear();
                            }
                        }
                    } catch(Exception ex) {
                        embed.addField("Error!", "You must reply to one of my messages!", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } catch(Exception ex) {
                    embed.addField(prefix + "edit <some text>", "Edits one of my messages.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //giverole
        if (args[0].equalsIgnoreCase(prefix + "giverole")) {
            if (member.hasPermission(Permission.MANAGE_ROLES)) {
                if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                    if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        Role roleToGive = event.getMessage().getMentionedRoles().get(0);
                        try {
                            event.getGuild().addRoleToMember(mentioned, roleToGive).queue();
                            embed.addField("Role given.", "Gave the role of " + roleToGive.getAsMention() + " to " + mentioned.getAsMention() + ".", false);
                            channel.sendMessageEmbeds(embed.build()).queue();
                            embed.clear();
                        } catch(Exception ex) {
                            embed.addField("Error!", "That role is higher then mine!", false);
                            channel.sendMessageEmbeds(embed.build()).queue();
                            embed.clear();
                        }
                    } else {
                        embed.addField("Error!", "You must specify a user!", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "giverole <@role> <@user>", "Gives a user a role.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have manage roles permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //kick
        if (args[0].equalsIgnoreCase(prefix + "kick")) {
            if (member.hasPermission(Permission.KICK_MEMBERS)) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    if (event.getMessage().getContentRaw().toCharArray().length >= 29) {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        String reason = event.getMessage().getContentRaw().substring(29);
                        event.getGuild().kick(mentioned, reason).queue();
                        embed.addField("User kicked", mentioned.getAsMention() + " has been kicked with reason \"" + reason +"\".", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    } else {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        event.getGuild().kick(mentioned, null).queue();
                        embed.addField("User kicked", mentioned.getAsMention() + " has been kicked.", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "kick <@user> [<reason>]", "Kicks a user.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have kick permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //mute
        if (args[0].equalsIgnoreCase(prefix + "mute")) {
            if (member.hasPermission(Permission.MODERATE_MEMBERS)) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    if (event.getMessage().getContentRaw().toCharArray().length >= 29) {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        String msga = args[2];
                        if (Character.isDigit(msga.charAt(0))) {
                            String msgc = event.getMessage().getContentRaw().substring(6);
                            try {
                                if (args[3] != null) {
                                    if (msga.endsWith("s")) {
                                        String reason = msgc.substring(msgc.indexOf("s") + 2);
                                        String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                        long time = Long.parseLong(msgt);
                                        mentioned.timeoutFor(time, TimeUnit.SECONDS).reason(reason).queue();
                                        embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " seconds with reason \"" + reason + "\".", false);
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                        embed.clear();
                                    } else if (msga.endsWith("m")) {
                                        String reason = msgc.substring(msgc.indexOf("m") + 2);
                                        String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                        long time = Long.parseLong(msgt);
                                        mentioned.timeoutFor(time, TimeUnit.MINUTES).reason(reason).queue();
                                        embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " minutes with reason \"" + reason + "\".", false);
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                        embed.clear();
                                    } else if (msga.endsWith("h")) {
                                        String reason = msgc.substring(msgc.indexOf("h") + 2);
                                        String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                        long time = Long.parseLong(msgt);
                                        mentioned.timeoutFor(time, TimeUnit.HOURS).reason(reason).queue();
                                        embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " hours with reason \"" + reason + "\".", false);
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                        embed.clear();
                                    } else if (msga.endsWith("d")) {
                                        String reason = msgc.substring(msgc.indexOf("d") + 2);
                                        String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                        long time = Long.parseLong(msgt);
                                        mentioned.timeoutFor(time, TimeUnit.DAYS).reason(reason).queue();
                                        embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " days with reason \"" + reason + "\".", false);
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                        embed.clear();
                                    } else if (msga.endsWith("w")) {
                                        String reason = msgc.substring(msgc.indexOf("d") + 2);
                                        String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                        long time = Long.parseLong(msgt);
                                        mentioned.timeoutFor(time * 7, TimeUnit.DAYS).reason(reason).queue();
                                        embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " weeks with reason \"" + reason + "\".", false);
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                        embed.clear();
                                    } else {
                                        embed.addField("Error!", "You must specify a time unit for \"" + msga + "\"!", false);
                                        embed.addField("Example", msga + "w = " + msga + " weeks, " + msga + "d = " + msga + " days, " + msga + "h = " + msga + " hours, " + msga + "m = " + msga + " minutes, " + msga + "s = " + msga + " seconds", false);
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                        embed.clear();
                                    }
                                }
                            } catch(Exception ex) {
                                if (msga.endsWith("s")) {
                                    String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                    long time = Long.parseLong(msgt);
                                    mentioned.timeoutFor(time, TimeUnit.SECONDS).queue();
                                    embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " seconds.", false);
                                    channel.sendMessageEmbeds(embed.build()).queue();
                                    embed.clear();
                                } else if (msga.endsWith("m")) {
                                    String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                    long time = Long.parseLong(msgt);
                                    mentioned.timeoutFor(time, TimeUnit.MINUTES).queue();
                                    embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " minutes.", false);
                                    channel.sendMessageEmbeds(embed.build()).queue();
                                    embed.clear();
                                } else if (msga.endsWith("h")) {
                                    String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                    long time = Long.parseLong(msgt);
                                    mentioned.timeoutFor(time, TimeUnit.HOURS).queue();
                                    embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " hours.", false);
                                    channel.sendMessageEmbeds(embed.build()).queue();
                                    embed.clear();
                                } else if (msga.endsWith("d")) {
                                    String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                    long time = Long.parseLong(msgt);
                                    mentioned.timeoutFor(time, TimeUnit.DAYS).queue();
                                    embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " days.", false);
                                    channel.sendMessageEmbeds(embed.build()).queue();
                                    embed.clear();
                                } else if (msga.endsWith("w")) {
                                    String msgt = msga.replace(msga.substring(msga.length()-1), "");
                                    long time = Long.parseLong(msgt);
                                    mentioned.timeoutFor(time * 7, TimeUnit.DAYS).queue();
                                    embed.addField("User Muted", mentioned.getAsMention() + " has been muted for " + time + " weeks.", false);
                                    channel.sendMessageEmbeds(embed.build()).queue();
                                    embed.clear();
                                } else {
                                    embed.addField("Error!", "You must specify a time unit for \"" + msga + "\"!", false);
                                    embed.addField("Example", msga + "w = " + msga + " weeks, " + msga + "d = " + msga + " days, " + msga + "h = " + msga + " hours, " + msga + "m = " + msga + " minutes, " + msga + "s = " + msga + " seconds", false);
                                    channel.sendMessageEmbeds(embed.build()).queue();
                                    embed.clear();
                                }
                            }
                        } else {
                            embed.addField("Error!", "You must specify a time!", false);
                            channel.sendMessageEmbeds(embed.build()).queue();
                            embed.clear();
                        }
                    } else {
                        embed.addField("Error!", "You must specify a time!", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "mute <@user> <time> [<reason>]", "Mutes a user.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have mute members permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //removerole
        if (args[0].equalsIgnoreCase(prefix + "removerole")) {
            if (member.hasPermission(Permission.MANAGE_ROLES)) {
                if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                    if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                        Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                        Role roleToRemove = event.getMessage().getMentionedRoles().get(0);
                        try {
                            event.getGuild().removeRoleFromMember(mentioned, roleToRemove).queue();
                            embed.addField("Role removed.", "Removed the role of " + roleToRemove.getAsMention() + " from " + mentioned.getAsMention() + ".", false);
                            channel.sendMessageEmbeds(embed.build()).queue();
                            embed.clear();
                        } catch(Exception ex) {
                            embed.addField("Error!", "That role is higher then mine!", false);
                            channel.sendMessageEmbeds(embed.build()).queue();
                            embed.clear();
                        }  
                    } else {
                        embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have manage roles permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //reply
        if (args[0].equalsIgnoreCase(prefix + "reply")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                try {
                    if (event.getMessage().getContentRaw().toCharArray().length >= 7) {
                        Message msg = event.getMessage().getMessageReference().getMessage();
                        msg.reply(event.getMessage().getContentRaw().substring(7)).queue();
                        event.getMessage().delete().queue();
                    } else {
                        embed.addField(prefix + "reply <some text>", "Replies to replied message.", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } catch(Exception ex) {
                    embed.addField("Error!", "You must reply to a message!", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //say
        if (args[0].equalsIgnoreCase(prefix + "say")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                if (event.getMessage().getContentRaw().toCharArray().length >= 5) {
                    String content = event.getMessage().getContentRaw().substring(7, 7);
                    if (content.contains("#")) {

                    }
                    channel.sendMessage(event.getMessage().getContentRaw().substring(5)).queue();
                    event.getMessage().delete().queue();
                } else {
                    embed.addField(prefix + "say <some text>", "Sends inputted text.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //softban TODO
        if (args[0].equalsIgnoreCase(prefix + "softban")) {
            if (member.hasPermission(Permission.BAN_MEMBERS)) {
                //
            }
            //
        }
        //unban TODO
        if (args[0].equalsIgnoreCase(prefix + "unban")) {
            if (member.hasPermission(Permission.BAN_MEMBERS)) {

            }
            //
        }
        //unmute
        if (args[0].equalsIgnoreCase(prefix + "unmute")) {
            if (member.hasPermission(Permission.MODERATE_MEMBERS)) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member mentioned = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    if (mentioned.isTimedOut()) {
                        mentioned.removeTimeout().queue();
                        embed.addField("User Unmuted", mentioned.getAsMention() + " has been unmuted.", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    } else {
                        embed.addField("Error!", mentioned.getAsMention() + " is not muted!", false);
                        channel.sendMessageEmbeds(embed.build()).queue();
                        embed.clear();
                    }
                } else {
                    embed.addField(prefix + "unmute <@user>", "Unmutes a user.", false);
                    channel.sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.addField("Error!", "You do not have mute members permission!", false);
                channel.sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }


        //add commands above this
        if (args[0].equalsIgnoreCase(prefix + "commands")) {
            embed.setTitle("Commands List (Utilities)", null);
            embed.setDescription("Page 1 of 3");

            embed.addField(prefix + "avatar [<@user>]", "Shows avatar of user.", false);
            embed.addField(prefix + "userinfo", "Shows users info.", false);
            embed.addField(prefix + "serverinfo", "Shows server info.", false);
            embed.addField(prefix + "ping", "Shows message response time.", false);
            embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
            embed.addField(prefix + "react <emoji>", "Adds reaction to replied message.", false);

            channel.sendMessageEmbeds(embed.build()).setActionRow(Button.primary("page:2", "Page 2"), Button.primary("page:3", "Page 3")).queue();
            embed.clear();
        }
        } 
    }

//commands lists
    public void onButtonInteraction(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setFooter("Command executed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        if (event.getComponentId().equals("page:1")) {
            embed.setTitle("Commands List (Utilities)", null);
            embed.setDescription("Page 1 of 3");

            embed.addField(prefix + "avatar [<@user>]", "Shows avatar of user.", false);
            embed.addField(prefix + "userinfo", "Shows users info.", false);
            embed.addField(prefix + "serverinfo", "Shows server info.", false);
            embed.addField(prefix + "ping", "Shows message response time.", false);
            embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
            embed.addField(prefix + "react <emoji>", "Adds reaction to replied message.", false);

            event.editMessageEmbeds(embed.build()).setActionRow(Button.primary("page:2", "Page 2"), Button.primary("page:3", "Page 3")).queue();
        }

        if (event.getComponentId().equals("page:2")) {
            embed.setTitle("Commands List (Music)", null);
            embed.setDescription("Page 2 of 3");

            embed.addField(prefix + "play <music url>", "Plays music.", false);
            embed.addField(prefix + "skip", "Skips currently playing track.", false);

            event.editMessageEmbeds(embed.build()).setActionRow(Button.primary("page:3", "Page 3"), Button.primary("page:1", "Page 1")).queue();
        }

        if (event.getComponentId().equals("page:3")) {
            embed.setTitle("Commands List (Moderation)", null);
            embed.setDescription("Page 3 of 3");

            embed.addField(prefix + "kick <@user> [<reason>]", "Kicks a user.", false);
            embed.addField(prefix + "giverole <@role> <@user>", "Gives a user a role.", false);
            embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
            embed.addField(prefix + "say <some text>", "Sends text.", false);
            embed.addField(prefix + "reply <some text>", "Replys to replied message.", false);
            embed.addField(prefix + "edit <some text>", "Edits bot message you reply to.", false);
            embed.addField(prefix + "delete", "Deletes message you replied to.", false);

            event.editMessageEmbeds(embed.build()).setActionRow(Button.primary("page:1", "Page 1"), Button.primary("page:2", "Page 2")).queue();
        }
    }
}