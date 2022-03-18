package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UserinfoCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        

        if (args[0].equalsIgnoreCase(prefix + "userinfo")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);
            
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
    }
} //TODO add created date and join date if in server. add option to use IDs