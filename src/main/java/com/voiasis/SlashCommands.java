package com.voiasis;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommands extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        Member member = event.getMember();
        //ping
        if (event.getName().equals("ping")) {
            LocalDateTime time = LocalDateTime.now();
            String gwp = Long.toString(event.getJDA().getGatewayPing());
            embed.setTitle("Pong!", null);
            embed.setDescription("Ping: ... \nWebsocket: ...");
            event.replyEmbeds(embed.build()).queue(message -> {
                embed.clear();
                long ping = time.until(LocalDateTime.now(), ChronoUnit.MILLIS);
                embed.setTitle("Pong!", null);
                embed.setDescription("Ping: " + ping + " ms\nWebsocket: " + gwp + " ms");
                embed.setColor(Color.MAGENTA);
                message.editOriginalEmbeds(embed.build()).queue();
                embed.clear();
            });
        }
        //afk
        if (event.getName().equals("afk")) {
            try {
                String afkread = Files.readString(Path.of("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt"));
                File afktxt = new File("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt");
                afktxt.delete();
                embed.addField("AFK Disabled", "Removed afk status.", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } catch(Exception ex) {
                try {
                    File afktxt = new File("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt");
                    afktxt.createNewFile();
                    OptionMapping msgopt = event.getOption("message");
                    String msg = msgopt == null ? null : msgopt.getAsString();
                    if (!msg.isBlank()) {
                        FileWriter afkfile = new FileWriter("E:\\Dev\\JennyChan\\settings\\afk\\" + event.getMember().getId() + ".txt");
                        afkfile.write(msg);
                        afkfile.close();
                        embed.addField("AFK Enabled", "Added afk status of \"" + msg + "\".", false);
                        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                    } else {
                        embed.addField("AFK enabled", "Added afk status.", false);
                        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                    }
                } catch(Exception e) {
                }
            }
        }
        //avatar
        if (event.getName().equals("avatar")) {
            Member memberopt = event.getOption("user").getAsMember();
            OptionMapping useropt = event.getOption("user");
            String user = useropt == null ? null : useropt.getAsString();
            if (event.getOption("hidden").getAsBoolean()) {
                if(!user.isBlank()) {
                    embed.setTitle("Image Link", memberopt.getUser().getAvatarUrl() + "?size=1024");
                    embed.setDescription("Avatar of " + memberopt.getUser().getAsMention() + ".");
                    embed.setImage(memberopt.getUser().getAvatarUrl() + "?size=1024");
                    event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                } else {
                    embed.setTitle("Image Link", event.getMember().getUser().getAvatarUrl() + "?size=1024");
                    embed.setDescription("Avatar of " + event.getMember().getUser().getAsTag() + ".");
                    embed.setImage(event.getMember().getUser().getAvatarUrl() + "?size=1024");
                    event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                }
            } else {
                if(!user.isBlank()) {
                    embed.setTitle("Image Link", memberopt.getUser().getAvatarUrl() + "?size=1024");
                    embed.setDescription("Avatar of " + memberopt.getUser().getAsMention() + ".");
                    embed.setImage(memberopt.getUser().getAvatarUrl() + "?size=1024");
                    event.replyEmbeds(embed.build()).setEphemeral(false).queue();
                } else {
                    embed.setTitle("Image Link", event.getMember().getUser().getAvatarUrl() + "?size=1024");
                    embed.setDescription("Avatar of " + event.getMember().getUser().getAsTag() + ".");
                    embed.setImage(event.getMember().getUser().getAvatarUrl() + "?size=1024");
                    event.replyEmbeds(embed.build()).setEphemeral(false).queue();
                }
            }
            
        }
        //uptime
        if (event.getName().equals("uptime")) {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long ut = rb.getUptime();
            long second = (ut / 1000) % 60;
            long minute = (ut / (1000 * 60)) % 60;
            long hour = (ut / (1000 * 60 * 60)) % 24;
            long day = (ut / (1000 * 60 * 60 * 60)) % 24;
            String uptime = day + " Days, " + hour + " Hours, " + minute + " Minutes, and " + second + " Seconds. ";
            embed.addField("JennyChan's Uptime", uptime, false);
            event.replyEmbeds(embed.build()).setEphemeral(false).queue();
        }
        if (event.getName().equals("userinfo")) {
            LocalDateTime now = LocalDateTime.now();;
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
        //lock
        if (event.getName().equals("lock")) {
            if (member.hasPermission(Permission.MANAGE_CHANNEL)) {
                event.getTextChannel().getManager().getChannel().putPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.MESSAGE_SEND).queue();
                embed.addField("Lock Updated", "Channel has been locked.", false);
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            } else {
                embed.addField("Error!", "You do not have Manage Channel permission!", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }
        //unlock
        if (event.getName().equals("unlock")) {
            if (member.hasPermission(Permission.MANAGE_CHANNEL)) {
                event.getTextChannel().getManager().getChannel().putPermissionOverride(event.getGuild().getPublicRole()).clear(Permission.MESSAGE_SEND).queue();
                embed.addField("Lock Updated", "Channel has been unlocked.", false);
                event.replyEmbeds(embed.build()).setEphemeral(false).queue();
            } else {
                embed.addField("Error!", "You do not have Manage Channel permission!", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }
        //NSFW
        if (event.getName().equals("nsfw")) {
            if (member.hasPermission(Permission.MANAGE_CHANNEL)) {
                if (event.getTextChannel().isNSFW()) {
                    event.getTextChannel().getManager().setNSFW(false).queue();
                    embed.addField("NSFW Updated", "NSFW has been disabled.", false);
                    event.replyEmbeds(embed.build()).setEphemeral(false).queue();
                } else {
                    event.getTextChannel().getManager().setNSFW(true).queue();
                    embed.addField("NSFW Updated", "NSFW has been enabled.", false);
                    event.replyEmbeds(embed.build()).setEphemeral(false).queue();
                }
            } else {
                embed.addField("Error!", "You do not have Manage Channel permission!", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }
        //purge
        if (event.getName().equals("purge")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                    List<Message> messages = event.getChannel().getHistory().retrievePast(event.getOption("amount").getAsInt()).complete();
                    try {
                        event.getTextChannel().deleteMessages(messages).queue();
                        embed.addField("Messages Deleted", event.getOption("amount").getAsInt() + " messages have been deleted.", false);
                        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                    } catch (IllegalArgumentException ex) {
                        embed.addField("Error!", "Must provide at least 2 or at most 100 messages to be deleted!", false);
                        event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                            event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                        });
                    }
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }

        





        //say
        if (event.getName().equals("say")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                OptionMapping channelopt = event.getOption("channel");
                String channel = channelopt == null ? null : channelopt.getAsString();
                OptionMapping textopt = event.getOption("text");
                String text = textopt == null ? null : textopt.getAsString();
                embed.addField("Say", "Sent the text \"" + text + "\" inside the channel \"<#" + channel + ">\".", false);
                event.getGuild().getTextChannelById(channel).sendMessage(text).queue();
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } else {
                embed.addField("Error!", "You do not have manage messages permission!", false);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue(message -> {
                    event.getHook().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
                });
            }
        }
    }
}
