package BotCommands.Moderation;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class KickCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(prefix + "kick")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

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
    }
} //TODO check for kick permission of command user. check if in server