package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(prefix + "message")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

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
    }
}